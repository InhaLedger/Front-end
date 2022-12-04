package com.inhaproject.karaoke3.ui.mypage.admin

import com.inhaproject.karaoke3.ui.mypage.ProposalData
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemProposalBinding
import java.text.SimpleDateFormat

class ProposalAdapter (private val List : ArrayList<ProposalData>, val context : Context)
    : RecyclerView.Adapter<ProposalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProposalBinding) : RecyclerView.ViewHolder(binding.root) {

        private val date : TextView = itemView.findViewById(R.id.proposalStartTextView)
        private val writer : TextView = itemView.findViewById(R.id.proposalWriterTextView)
        private val content : TextView = itemView.findViewById(R.id.proposalContentTextView)
        private val status : TextView = itemView.findViewById(R.id.proposalStatusTextView)

        private val context = binding.root.context

        private fun convertTimestampToDate(timestamp: Long) : String {
            val sdf = SimpleDateFormat("yyyy-MM-dd-hh:mm")
            val date = sdf.format(timestamp)
            Log.d("TTT UNix Date -> ", sdf.format((System.currentTimeMillis())).toString())
            Log.d("TTTT date -> ", date.toString())

            return date.toString()
        }

        fun bind(item: ProposalData) {
            date.text = convertTimestampToDate(item.proposal_timeStamp.toLong())
            writer.text = item.proposal_userid.toString()
            content.text = item.proposal_type +" "+ item.proposal_boardidx +"번 게시물"
            if(item.proposal_status == "PROGRESS")
            status.text = "진행중"
            else
                status.text = "완료"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProposalBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }
}