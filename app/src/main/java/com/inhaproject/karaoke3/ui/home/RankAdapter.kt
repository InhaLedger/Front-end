package com.inhaproject.karaoke3.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SongDetailActivity
import com.inhaproject.karaoke3.databinding.ItemRankBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankAdapter(private val RankList : ArrayList<RankData>, val context: Context)
    : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemRankBinding): RecyclerView.ViewHolder(binding.root){

        private val api = RetroInterface.create()

        val title: TextView = itemView.findViewById(R.id.rv_tv_title)
        private val singer: TextView = itemView.findViewById(R.id.rv_tv_singer)
        private val star: TextView = itemView.findViewById(R.id.rv_star)
        private val image: ImageView = itemView.findViewById(R.id.rv_album_img)
        private val rank : TextView = itemView.findViewById(R.id.rv_num)
        private val starBox : CheckBox = itemView.findViewById(R.id.checkbox_basket)


        fun bind(rankModel: RankData, context: Context?) {
            rank.text = rankModel.rankidx.toString()
            title.text = rankModel.title
            singer.text = rankModel.singer
            star.text = "("+rankModel.star.toString()+")"
            Glide.with(itemView).load(rankModel.imageurl).into(image)

            val no = rankModel.no.toString()

            if(rankModel.alreadystar == 1){
                starBox.isChecked = true
            }

            itemView.setOnClickListener {
                val intent = Intent(context, SongDetailActivity::class.java)

                intent.putExtra("노래 제목",rankModel.title)
                intent.putExtra("가수",rankModel.singer)
                intent.putExtra("번호",rankModel.no.toString())
                intent.putExtra("발매일",rankModel.releasedate.substring(0 until 10))
                intent.putExtra("작곡자",rankModel.composer)
                intent.putExtra("작사자",rankModel.lyricist)
                intent.putExtra("앨범 제목",rankModel.album)
                intent.putExtra("앨범 이미지",rankModel.imageurl)

                intent.run {
                    context?.startActivity(this)
                }
            }

            starBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    api.insertMySong(no).enqueue(object :Callback<String>{
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if(response.code() == 200){
                                //tempStar = rankModel.star + 1
                                star.text = "("+rankModel.star.toString()+")"
                                Toast.makeText(context,"즐겨찾기에 추가되었습니다. "
                                    , Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(context,"즐겨찾기 서버 연결 실패 "
                                , Toast.LENGTH_SHORT).show()
                            Log.d("즐겨찾기 추가 실패",t.message.toString())
                        }

                    })
                }
                else {
                    api.deleteMySong(no).enqueue(object :Callback<String>{
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if(response.code() == 200){
                                star.text = "("+rankModel.star.toString()+")"
                                Toast.makeText(context,"즐겨찾기에서 삭제되었습니다. "
                                    , Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(context,"즐겨찾기 서버 연결 실패 "
                                , Toast.LENGTH_SHORT).show()
                            Log.d("즐겨찾기 삭제 실패", t.message.toString())
                        }

                    })
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRankBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)).apply {
                setIsRecyclable(false)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(RankList[position], context)
    }

    override fun getItemCount(): Int {
        return RankList.count()
    }



}