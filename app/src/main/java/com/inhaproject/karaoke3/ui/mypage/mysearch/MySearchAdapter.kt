package com.inhaproject.karaoke3.ui.mypage.mysearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.SongDetailActivity
import com.inhaproject.karaoke3.databinding.ItemMysearchBinding
import com.inhaproject.karaoke3.databinding.ItemSearchBinding

class MySearchAdapter (private val MySearchList : ArrayList<SearchData>, val context : Context)
    : RecyclerView.Adapter<MySearchAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMysearchBinding) : RecyclerView.ViewHolder(binding.root) {

        private val txtName: TextView = itemView.findViewById(R.id.msv_title)
        private val txtSinger: TextView = itemView.findViewById(R.id.msv_singer)
        private val image: ImageView = itemView.findViewById(R.id.msv_album_img)

        private val context = binding.root.context

        fun bind(item: SearchData) {
            txtName.text = item.title
            txtSinger.text = item.singer
            Glide.with(itemView).load(item.imageurl).into(image)
            binding.msvHighNote.text = "최고 음 : "+item.highNote
            binding.msvLowNote.text = "최저 음 : "+item.lowNote
            binding.msvStar.text = "("+item.star.toString()+")"

            itemView.setOnClickListener {
                val intent = Intent(context, SongDetailActivity::class.java)

                intent.putExtra("노래 제목",item.title)
                intent.putExtra("가수",item.singer)
                intent.putExtra("번호",item.no.toString())
                intent.putExtra("발매일",item.releasedate.substring(0 until 10))
                intent.putExtra("작곡자",item.composer)
                intent.putExtra("작사자",item.lyricist)
                intent.putExtra("앨범 제목",item.album)
                intent.putExtra("앨범 이미지",item.imageurl)

                intent.run {
                    context?.startActivity(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMysearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(MySearchList[position])
    }

    override fun getItemCount(): Int {
        return MySearchList.size
    }

}