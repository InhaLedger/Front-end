package com.inhaproject.karaoke3.ui.community.fixboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ItemSearchBinding
import com.inhaproject.karaoke3.ui.community.noteboard.NoteAddArticleActivity
import com.inhaproject.karaoke3.ui.community.noteboard.NoteSearchAdapter

class FixSearchAdapter (private val searchList : ArrayList<SearchData>, val context : Context)
    : RecyclerView.Adapter<FixSearchAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: ItemSearchBinding)
        : RecyclerView.ViewHolder(binding.root){

        val title: TextView = itemView.findViewById(R.id.sv_title)
        private val singer: TextView = itemView.findViewById(R.id.sv_singer)
        private val star: TextView = itemView.findViewById(R.id.sv_star)
        private val image: ImageView = itemView.findViewById(R.id.sv_album_img)


        fun bind(searchModel: SearchData, context: Context?) {
            title.text = searchModel.title
            singer.text = searchModel.singer
            star.text = "("+searchModel.star.toString()+")"
            Glide.with(itemView).load(searchModel.imageurl).into(image)

            itemView.setOnClickListener {
                val intent = Intent(context, FixAddArticleActivity::class.java)

                intent.putExtra("노래 제목",searchModel.title)
                intent.putExtra("가수",searchModel.singer)
                intent.putExtra("번호",searchModel.no.toString())
                intent.putExtra("발매일",searchModel.releasedate.substring(0 until 10))
                intent.putExtra("작곡자",searchModel.composer)
                intent.putExtra("작사자",searchModel.lyricist)
                intent.putExtra("앨범 제목",searchModel.album)
                intent.putExtra("앨범 이미지",searchModel.imageurl)

                intent.run {
                    context?.startActivity(this)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchList[position],context)
    }

    override fun getItemCount(): Int {
        return searchList.count()
    }

}