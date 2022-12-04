package com.inhaproject.karaoke3.ui.mypage.coin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemDepositBinding
import java.text.SimpleDateFormat

class DepositAdapter(private val depositList: ArrayList<DepositData>, val context: Context)
    : RecyclerView.Adapter<DepositAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDepositBinding) : RecyclerView.ViewHolder(binding.root) {

        private val dDate : TextView = itemView.findViewById(R.id.depositDateTextView)
        private val eDate : TextView = itemView.findViewById(R.id.expireDateTextView)
        private val content : TextView = itemView.findViewById(R.id.depositContentTextView)
        private val coin : TextView = itemView.findViewById(R.id.depositCoinTextView)

        private val context = binding.root.context


        private fun convertTimestampToDate(timestamp: Long) : String {
            val sdf = SimpleDateFormat("yyyy-MM-dd-hh:mm")
            val date = sdf.format(timestamp)
            Log.d("TTT UNix Date -> ", sdf.format((System.currentTimeMillis())).toString())
            Log.d("TTTT date -> ", date.toString())

            return date.toString()
        }


        fun bind(item: DepositData) {
            dDate.text = convertTimestampToDate(item.timestamp)
            eDate.text = convertTimestampToDate(item.completeTimestamp)
            coin.text = item.amount.toString()
            if(item.amount.toInt() == 10){
                content.text = "게시글 작성"
            }
            else
                content.text = "게시글 투표"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDepositBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(depositList[position])
    }

    override fun getItemCount(): Int {
        return depositList.size
    }


}