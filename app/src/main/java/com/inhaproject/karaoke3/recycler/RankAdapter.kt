package com.inhaproject.karaoke3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3
import com.inhaproject.karaoke3.R

class RankAdapter(private val context: Context?) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {
        var data = mutableListOf<MusicData>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType:
        Int): ViewHolder {
            val binding =

        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(data[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtTitle: TextView = itemView.findViewById(R.id.rv_tv_title)
        private val txtSinger: TextView = itemView.findViewById(R.id.rv_tv_singer)
        private val imgAlbum: ImageView = itemView.findViewById(R.id.rv_album_img)

        fun bind(item: MusicData) {
            txtTitle.text = item.title
            txtSinger.text = item.singer
            Glide.with(itemView).load(item.img).into(imgAlbum)

        }
    }
    }
