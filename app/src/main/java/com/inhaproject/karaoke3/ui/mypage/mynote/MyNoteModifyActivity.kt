package com.inhaproject.karaoke3.ui.mypage.mynote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityMynoteModifyBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.record.RecordResultActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyNoteModifyActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMynoteModifyBinding

    private val api = RetroInterface.create()

    var checkedIndex = 0
    var flag = 0
    var code = ""
    var maxCode = ""
    var minCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMynoteModifyBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_mynote_modify)

        val modifyMax : Button = findViewById(R.id.ModifyMaxButton)
        val modifyMin : Button = findViewById(R.id.ModifyMinButton)
        val saveBtn : Button = findViewById(R.id.myNoteModifySaveButton)
        val cancelBtn : Button = findViewById(R.id.myNoteModifyCancelButton)

        modifyMax.text = intent.getStringExtra("최고 음")
        modifyMin.text = intent.getStringExtra("최저 음")

        modifyMax.setOnClickListener {
            flag = 0
            radioMenu()
        }

        modifyMin.setOnClickListener {
            flag = 1
            radioMenu()
        }
        cancelBtn.setOnClickListener {
            val intent = Intent(this, MyNoteActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveBtn.setOnClickListener {
            stringToCode(modifyMax.text as String?)
            maxCode = code
            stringToCode(modifyMin.text as String?)
            minCode = code

            api.myNoteSubmit(maxCode,minCode).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body().toString().isNotEmpty()){
                        if(response.code() == 200){
                            response.body().let {
                                Toast.makeText(
                                    this@MyNoteModifyActivity, "저장되었습니다.", Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@MyNoteModifyActivity, "저장에 실패했습니다.", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("음역대 저장 오류",t.message.toString())
                }

            })
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun radioMenu() {

        val modifyMax : Button = findViewById(R.id.ModifyMaxButton)
        val modifyMin : Button = findViewById(R.id.ModifyMinButton)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("최고 음")
        val itemList = arrayOf("0옥타브 도","0옥타브 레","0옥타브 미","0옥타브 파"
        ,"0옥타브 솔","0옥타브 라","0옥타브 시","1옥타브 도","1옥타브 레","1옥타브 미","1옥타브 파",
            "1옥타브 솔","1옥타브 라","1옥타브 시","2옥타브 도","2옥타브 레","2옥타브 미","2옥타브 파",
            "2옥타브 솔","2옥타브 라","2옥타브 시","3옥타브 도","3옥타브 레","3옥타브 미","3옥타브 파",
            "3옥타브 솔","3옥타브 라","3옥타브 시","4옥타브 도",)

        builder.setSingleChoiceItems(itemList,checkedIndex) {
            dialog, which -> checkedIndex = which
        }
        builder.setPositiveButton("확인") {dialog, which ->
            if (flag == 0){
                modifyMax.text = itemList[checkedIndex]
            }
            else {
                modifyMin.text = itemList[checkedIndex]
            }
        }
        builder.show()
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