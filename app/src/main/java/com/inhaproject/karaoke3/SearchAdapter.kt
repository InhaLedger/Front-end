package com.inhaproject.karaoke3

import com.inhaproject.karaoke3.ui.home.RankData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.databinding.ItemSearchBinding

class SearchAdapter(private val searchList : ArrayList<SearchData>, val context : Context)
    : RecyclerView.Adapter<SearchAdapter.ViewHolder>()
{
        inner class ViewHolder(private val binding: ItemSearchBinding)
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
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context),
        parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchList[position],context)
    }

    override fun getItemCount(): Int {
        return searchList.count()
    }

}


