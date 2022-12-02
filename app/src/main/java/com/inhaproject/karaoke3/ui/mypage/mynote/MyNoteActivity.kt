package com.inhaproject.karaoke3.ui.mypage.mynote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityMynoteBinding
import com.inhaproject.karaoke3.databinding.ActivityRecordResultBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.min

class MyNoteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMynoteBinding

    private val api = RetroInterface.create()

    var note = ""
    var max= ""
    var min= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMynoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val okBtn : Button = findViewById(R.id.myNoteOkButton)
        val modifyBtn : Button = findViewById(R.id.myNoteModifyButton)

        val highNote : TextView = findViewById(R.id.myNote_max)
        val minNote : TextView = findViewById(R.id.myNote_min)

        api.showMyNote().enqueue(object : Callback<ArrayList<MyNoteData>>{
            override fun onResponse(
                call: Call<ArrayList<MyNoteData>>,
                response: Response<ArrayList<MyNoteData>>
            ) {
                if(response.code() == 200){
                    CodeToString(response.body()?.get(0)?.highNote)
                    highNote.text = note
                    CodeToString(response.body()?.get(0)?.lowNote)
                    minNote.text = note
                }
            }

            override fun onFailure(call: Call<ArrayList<MyNoteData>>, t: Throwable) {
                Toast.makeText(this@MyNoteActivity,"음역대 서버 연결 실패 " , Toast.LENGTH_SHORT).show()
                Log.d("나의 음역대 오류", t.message.toString())
            }

        })

        okBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        modifyBtn.setOnClickListener {
            val intent = Intent(this, MyNoteModifyActivity::class.java)
            intent.putExtra("최고 음",highNote.text.toString())
            intent.putExtra("최저 음",minNote.text.toString())
            startActivity(intent)
            finish()
        }
    }
    private fun CodeToString(string: String?){
        when (string) {
            "C0" -> {
                note = "-2옥타브 도"
            }
            "C2" -> {
                note = "0옥타브 도"
            }
            "D2" -> {
                note = "0옥타브 레"
            }
            "E2" -> {
                note = "0옥타브 미"
            }
            "F2" -> {
                note = "0옥타브 파"
            }
            "G2" -> {
                note = "0옥타브 솔"
            }
            "A3" -> {
                note = "0옥타브 라"
            }
            "B3" -> {
                note = "0옥타브 시"
            }
            "C3" -> {
                note = "1옥타브 도"
            }
            "D3" -> {
                note = "1옥타브 레"
            }
            "E3" -> {
                note = "1옥타브 미"
            }
            "F3" -> {
                note = "1옥타브 파"
            }
            "G3" -> {
                note = "1옥타브 솔"
            }
            "A4" -> {
                note = "1옥타브 라"
            }
            "B4" -> {
                note = "1옥타브 시"
            }
            "C4" -> {
                note = "2옥타브 도"
            }
            "D4" -> {
                note = "2옥타브 레"
            }
            "E4" -> {
                note = "2옥타브 미"
            }
            "F4" -> {
                note = "2옥타브 파"
            }
            "G4" -> {
                note = "2옥타브 솔"
            }
            "A5" -> {
                note = "2옥타브 라"
            }
            "B5" -> {
                note = "2옥타브 시"
            }
            "C5" -> {
                note = "3옥타브 도"
            }
            "D5" -> {
                note = "3옥타브 레"
            }
            "E5" -> {
                note = "3옥타브 미"
            }
            "F5" -> {
                note = "3옥타브 파"
            }
            "G5" -> {
                note = "3옥타브 솔"
            }
            "A6" -> {
                note = "3옥타브 라"
            }
            "B6" -> {
                note = "3옥타브 시"
            }
            "C6" -> {
                note = "4옥타브 도"
            }
            "B8" -> {
                note = "5옥타브 시"
            }
        }
    }
}