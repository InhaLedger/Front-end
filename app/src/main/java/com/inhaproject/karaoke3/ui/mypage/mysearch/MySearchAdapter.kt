package com.inhaproject.karaoke3.ui.mypage.mysearch

import android.content.Context
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