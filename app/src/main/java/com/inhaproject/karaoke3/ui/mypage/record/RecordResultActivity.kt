package com.inhaproject.karaoke3.ui.mypage.record

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityRecordResultBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.MyPageFragment
import kotlinx.android.synthetic.main.activity_record_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecordResultActivity : AppCompatActivity() {

    init {
        instance = this
    }

    companion object{
        private var instance:RecordResultActivity? = null
        fun getInstance():RecordResultActivity? {
            return instance
        }
    }
    var code = ""
    var maxCode = ""
    var minCode = ""

    private lateinit var binding: ActivityRecordResultBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cancelBtn : Button = findViewById(R.id.result_Cancel_Button)
        val saveBtn : Button = findViewById(R.id.result_Save_Button)

        val note = intent.getStringExtra("최고 음")
        val minNote = intent.getStringExtra("최저 음")

        record_result.text = note
        min_result.text = minNote

        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveBtn.setOnClickListener {
            stringToCode(note)
            maxCode = code
            stringToCode(minNote)
            minCode = code
            api.myNoteSubmit(maxCode,minCode).enqueue(object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body().toString().isNotEmpty()){
                        if(response.code() == 200){
                        response.body().let {
                            Toast.makeText(
                                this@RecordResultActivity, "저장되었습니다.", Toast.LENGTH_SHORT
                            ).show()

                        }
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@RecordResultActivity, "저장에 실패했습니다.", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("음역대 저장 오류",t.message.toString())
                }

            })
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun stringToCode(string: String?) {
        when (string) {
            "0옥타브 도" -> {
                code = "C2"
            }
            "0옥타브 레" -> {
                code = "D2"
            }
            "0옥타브 미" -> {
                code = "E2"
            }
            "0옥타브 파" -> {
                code = "F2"
            }
            "0옥타브 솔" -> {
                code = "G2"
            }
            "0옥타브 라" -> {
                code = "A3"
            }
            "0옥타브 시" -> {
                code = "B3"
            }
            "1옥타브 도" -> {
                code = "C3"
            }
            "1옥타브 레" -> {
                code = "D3"
            }
            "1옥타브 미" -> {
                code = "E3"
            }
            "1옥타브 파" -> {
                code = "F3"
            }
            "1옥타브 솔" -> {
                code = "G3"
            }
            "1옥타브 라" -> {
                code = "A4"
            }
            "1옥타브 시" -> {
                code = "B4"
            }
            "2옥타브 도" -> {
                code = "C4"
            }
            "2옥타브 레" -> {
                code = "D4"
            }
            "2옥타브 미" -> {
                code = "E4"
            }
            "2옥타브 파" -> {
                code = "F4"
            }
            "2옥타브 솔" -> {
                code = "G4"
            }
            "2옥타브 라" -> {
                code = "A5"
            }
            "2옥타브 시" -> {
                code = "B5"
            }
            "3옥타브 도" -> {
                code = "C5"
            }
            "3옥타브 레" -> {
                code = "D5"
            }
            "3옥타브 미" -> {
                code = "E5"
            }
            "3옥타브 파" -> {
                code = "F5"
            }
            "3옥타브 솔" -> {
                code = "G5"
            }
            "3옥타브 라" -> {
                code = "A6"
            }
            "3옥타브 시" -> {
                code = "B6"
            }
            "4옥타브 도" -> {
                code = "C6"
            }
        }
    }

}