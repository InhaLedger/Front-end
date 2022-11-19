package com.inhaproject.karaoke3.ui.mypage.record

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.TarsosDSPAudioFormat
import be.tarsos.dsp.io.UniversalAudioInputStream
import be.tarsos.dsp.io.android.AndroidAudioPlayer
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.writer.WriterProcessor

import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityRecordBinding

import kotlinx.android.synthetic.main.activity_record.*
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteOrder
import java.io.FileInputStream


class RecordActivity : AppCompatActivity() {

    //private var file: File? =  File(sdCard,"recorded_sound.wav")

    private var dispatcher: AudioDispatcher? = null
    private var tarsosDSPAudioFormat: TarsosDSPAudioFormat? = null

    private var maxPitch = 0
    private var minPitch = 1000
    private var maxNote = "1옥타브 도"
    private var minNote = "1옥타브 도"

    private val soundVisualizer: SoundVisualizer by lazy {
        findViewById(R.id.soundVisualizerView)
    }

    private val recordTimeTextView: CountTextView by lazy {
        findViewById(R.id.recordTimeTextView)
    }
    lateinit var binding: ActivityRecordBinding
    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)

    private val recordingPath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    //private var recorder: MediaRecorder? = null
    //private var player : MediaPlayer? = null

    private var state = State.BEFORE_RECORDING
    set(value) {
        field = value
        resetButton.isEnabled = (value == State.AFTER_RECORDING ||
                value == State.ON_PLAYING)
        submitButton.isEnabled = (value == State.AFTER_RECORDING ||
                value == State.ON_PLAYING)
        recordButton.updateIconWithState(value)
    }

    private val submitButton : Button by lazy {
        findViewById(R.id.submit_Record)
    }

    private val resetButton: Button by lazy {
        findViewById(R.id.resetButton)
    }

    private val recordButton: RecordButton by lazy{
        findViewById(R.id.recordButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tarsosDSPAudioFormat = TarsosDSPAudioFormat(
            TarsosDSPAudioFormat.Encoding.PCM_SIGNED,
            22050F,
            2 * 8,
            1,
            2 * 1,
            22050F,
            ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder())
        )


        requestAudioPermission()
        initView()
        bindView()
        initVariables()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION
                && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if(!audioRecordPermissionGranted) {
            Toast.makeText(this,"권한이 필요합니다. 녹음 권한을 허용해주세요." , Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun initView() {
        recordButton.updateIconWithState(state)
    }

    private fun bindView(){
        /*soundVisualizer.onRequestCurrentAmplitude = {
            recorder?.maxAmplitude ?: 0
        }*/
        resetButton.setOnClickListener {
            stopPlaying()

            pitchTextView.text = "0 hz"
            octaveText.text = "1옥타브"
            noteText.text = "도"

            state = State.BEFORE_RECORDING
        }

        recordButton.setOnClickListener {
            when(state) {
                State.BEFORE_RECORDING -> {
                    recordSub.text = "3초간 높은 음을 내주세요"
                    startRecording()
                    Handler(Looper.getMainLooper()).postDelayed({
                        recordSub.text = "3초간 낮은 음을 내주세요"
                    },3000)
                    Handler(Looper.getMainLooper()).postDelayed({
                        recordSub.text = "wndwl"
                    },6000)
                }
                State.ON_RECORDING -> {
                    stopRecording()
                }
                State.AFTER_RECORDING -> {
                    startPlaying()
                }
                State.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }

        submitButton.setOnClickListener{
            binding.let {
                pitchToNote(maxPitch.toFloat())
                pitchToMin(minPitch.toFloat())

                val intent = Intent(this, RecordResultActivity::class.java)

                intent.putExtra("최고 음",maxNote)
                intent.putExtra("최저 음",minNote)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initVariables() {
        state = State.BEFORE_RECORDING
    }

    private fun startRecording() {

        //releaseDispatcher()
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)

        try {

            val randomAccessFile = RandomAccessFile(recordingPath, "rw")
            val recordProcessor: AudioProcessor =
                WriterProcessor(tarsosDSPAudioFormat, randomAccessFile)
            dispatcher?.addAudioProcessor(recordProcessor)
            val pitchDetectionHandler =
                PitchDetectionHandler { res, _ ->
                    val pitchInHz = res.pitch
                    runOnUiThread { pitchToNote(pitchInHz) }
                    soundVisualizer.onRequestCurrentAmplitude = {
                        pitchInHz.toInt()
                    }
                    if (pitchInHz.toInt() > maxPitch)
                        maxPitch = pitchInHz.toInt()

                    if (pitchInHz.toInt() in 81 until minPitch)
                        minPitch = pitchInHz.toInt()
                }
            val pitchProcessor: AudioProcessor = PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
                22050F,
                1024,
                pitchDetectionHandler
            )
            dispatcher?.addAudioProcessor(pitchProcessor)
            val audioThread = Thread(dispatcher, "Audio Thread")
            audioThread.start()

            soundVisualizer.startVisualizing(false)
            recordTimeTextView.startCount()
            state = State.ON_RECORDING



        } catch (e: IOException) {
            e.printStackTrace()
        }

    }



    private fun startPlaying() {
        try {
            //releaseDispatcher()

            val fileInputStream = FileInputStream(recordingPath)
            dispatcher = AudioDispatcher(
                UniversalAudioInputStream(fileInputStream, tarsosDSPAudioFormat),
                1024,
                0
            )
            val playerProcessor: AudioProcessor = AndroidAudioPlayer(tarsosDSPAudioFormat, 2048, 0)
            dispatcher?.addAudioProcessor(playerProcessor)
            val pitchDetectionHandler =
                PitchDetectionHandler { res, _ ->
                    val pitchInHz = res.pitch
                    runOnUiThread { pitchToNote(pitchInHz) }
                }
            val pitchProcessor: AudioProcessor = PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
                22050F,
                1024,
                pitchDetectionHandler
            )
            dispatcher?.addAudioProcessor(pitchProcessor)
            val audioThread = Thread(dispatcher, "Audio Thread")
            audioThread.start()

            soundVisualizer.startVisualizing(true)
            recordTimeTextView.startCount()
            state = State.ON_PLAYING


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun stopRecording() {
        super.onStop()
        releaseDispatcher()
        soundVisualizer.stopVisualizing()
        recordTimeTextView.stopCount()
        state = State.AFTER_RECORDING
    }

    private fun stopPlaying(){
        super.onStop()
        releaseDispatcher()
        soundVisualizer.stopVisualizing()
        recordTimeTextView.stopCount()
        state = State.AFTER_RECORDING
    }


    private fun releaseDispatcher() {
        //if (dispatcher?.isStopped() == false) {
        dispatcher?.stop()
        dispatcher = null
        //}
    }

    private fun pitchToNote(pitchInHz : Float){
        pitchTextView.text = pitchInHz.toInt().toString() +" hz"

        when (pitchInHz) {
            in 130.80..146.82 -> {
                //1옥 도
                octaveText.text = "1옥타브"
                noteText.text = "도"
                maxNote = "1옥타브 도"
            }
            in 146.83..164.80 -> {
                //1옥 레
                octaveText.text = "1옥타브"
                noteText.text = "레"
                maxNote = "1옥타브 레"
            }
            in 164.81..174.60 -> {
                //1옥 미
                octaveText.text = "1옥타브"
                noteText.text = "미"
                maxNote = "1옥타브 미"
            }
            in 174.61..195.99 -> {
                //1옥 파
                octaveText.text = "1옥타브"
                noteText.text = "파"
                maxNote = "1옥타브 파"
            }
            in 196.00..219.99 -> {
                //1옥 솔
                octaveText.text = "1옥타브"
                noteText.text = "솔"
                maxNote = "1옥타브 솔"
            }
            in 220.00..246.93 -> {
                //1옥 라
                octaveText.text = "1옥타브"
                noteText.text = "라"
                maxNote = "1옥타브 라"
            }
            in 246.94..261.62 -> {
                //1옥 시
                octaveText.text = "1옥타브"
                noteText.text = "시"
                maxNote = "1옥타브 시"
            }
            in 261.63..293.65 -> {
                //2옥 도
                octaveText.text = "2옥타브"
                noteText.text = "도"
                maxNote = "2옥타브 도"

            }
            in 293.66..329.62 -> {
                //2옥 레
                octaveText.text = "2옥타브"
                noteText.text = "레"
                maxNote = "2옥타브 레"
            }
            in 329.63..349.22 -> {
                //2옥 미
                octaveText.text = "2옥타브"
                noteText.text = "미"
                maxNote = "2옥타브 미"
            }
            in 349.23..391.99 -> {
                //2옥 파
                octaveText.text = "2옥타브"
                noteText.text = "파"
                maxNote = "2옥타브 파"
            }
            in 392.00..439.99 -> {
                //2옥 솔
                octaveText.text = "2옥타브"
                noteText.text = "솔"
                maxNote = "2옥타브 솔"
            }
            in 440.00..493.87 -> {
                //2옥 라
                octaveText.text = "2옥타브"
                noteText.text = "라"
                maxNote = "2옥타브 라"
            }
            in 493.88..523.24 -> {
                //2옥 시
                octaveText.text = "2옥타브"
                noteText.text = "시"
                maxNote = "2옥타브 시"
            }
            in 523.25..587.32 -> {
                //3옥 도
                octaveText.text = "3옥타브"
                noteText.text = "도"
                maxNote = "3옥타브 도"
            }
            in 587.33..659.25 -> {
                //3옥 레
                octaveText.text = "3옥타브"
                noteText.text = "레"
                maxNote = "3옥타브 레"
            }
            in 659.26..698.45 -> {
                //3옥 미
                octaveText.text = "3옥타브"
                noteText.text = "미"
                maxNote = "3옥타브 미"
            }
            in 698.46..783.98 -> {
                //3옥 파
                octaveText.text = "3옥타브"
                noteText.text = "파"
                maxNote = "3옥타브 파"
            }
            in 783.99..879.99 -> {
                //3옥 솔
                octaveText.text = "3옥타브"
                noteText.text = "솔"
                maxNote = "3옥타브 솔"
            }
            in 880.00..987.76 -> {
                //3옥 라
                octaveText.text = "3옥타브"
                noteText.text = "라"
                maxNote = "3옥타브 라"
            }
            in 987.77..1046.49 -> {
                //3옥 시
                octaveText.text = "3옥타브"
                noteText.text = "시"
                maxNote = "3옥타브 시"
            }
            in 1046.50..1108.72 -> {
                //4옥 도
                octaveText.text = "4옥타브"
                noteText.text = "도"
                maxNote = "4옥타브 도"
            }
            else -> {
                octaveText.text = "--"
                noteText.text = "--"
            }
        }
    }

    private fun pitchToMin(pitchInHz: Float){
        when (pitchInHz) {
            in 65.40..73.40 -> {
                minNote = "0옥타브 도"
            }
            in 73.41..82.39 -> {
                minNote = "0옥타브 레"
            }
            in 82.40..87.29 -> {
                minNote = "0옥타브 미"
            }
            in 87.30..97.99 -> {
                minNote = "0옥타브 파"
            }
            in 98.00..109.99 -> {
                minNote = "0옥타브 솔"
            }
            in 110.00..123.46 -> {
                minNote = "0옥타브 라"
            }
            in 123.47..130.80 -> {
                minNote = "0옥타브 시"
            }
            in 130.80..146.82 -> {
                //1옥 도
                minNote = "1옥타브 도"
            }
            in 146.83..164.80 -> {
                //1옥 레
                minNote = "1옥타브 레"
            }
            in 164.81..174.60 -> {
                //1옥 미
                minNote = "1옥타브 미"
            }
            in 174.61..195.99 -> {
                //1옥 파
                minNote = "1옥타브 파"
            }
            in 196.00..219.99 -> {
                //1옥 솔
                minNote = "1옥타브 솔"
            }
            in 220.00..246.93 -> {
                //1옥 라
                minNote = "1옥타브 라"
            }
            in 246.94..261.62 -> {
                //1옥 시
                minNote = "1옥타브 시"
            }
            in 261.63..293.65 -> {
                //2옥 도
                minNote = "2옥타브 도"

            }
        }
    }
    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }

}