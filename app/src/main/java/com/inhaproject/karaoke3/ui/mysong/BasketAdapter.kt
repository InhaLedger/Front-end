package com.inhaproject.karaoke3.ui.mysong

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemBasketBinding
import android.content.Context

class BasketAdapter (private val MySongList : ArrayList<BasketData>, val context: Context)
    : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(MySongList[position])
    }

    inner class ViewHolder(private val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {

        private val txtName: TextView = itemView.findViewById(R.id.rv_basket_title)
        private val txtSinger: TextView = itemView.findViewById(R.id.rv_basket_sub)
        private val imgBasket: ImageView = itemView.findViewById(R.id.rv_basket_img)


        fun bind(item: BasketData) {
            txtName.text = item.title
            txtSinger.text = item.singer
            Glide.with(itemView).load(item.imageurl).into(imgBasket)



        }
    }

    override fun getItemCount(): Int {
        return MySongList.count()
    }


}