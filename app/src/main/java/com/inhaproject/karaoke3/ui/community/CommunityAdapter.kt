package com.inhaproject.karaoke3.ui.community

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemCommunityBinding
import com.inhaproject.karaoke3.ui.community.board.BoardActivity
import com.inhaproject.karaoke3.ui.community.freeboard.FreeBoardActivity
import com.inhaproject.karaoke3.ui.community.rangeboard.RangeBoardActivity

class CommunityAdapter (fragment: CommunityFragment) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    var datas = mutableListOf<CommunityData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ItemCommunityBinding) : RecyclerView.ViewHolder(binding.root) {

        private val txtName: TextView = itemView.findViewById(R.id.rv_community_title)
        private val txtSub: TextView = itemView.findViewById(R.id.rv_community_sub)
        private val imgCommunity: ImageView = itemView.findViewById(R.id.rv_community_img)

        private val context = binding.root.context

        fun bind(item: CommunityData) {
            txtName.text = item.name
            txtSub.text = item.sub.toString()
            Glide.with(itemView).load(item.img).into(imgCommunity)
            itemView.setOnClickListener {
                var intent = Intent(context, BoardActivity::class.java)
                when(item.name) {
                    "코인노래방 패키지 추천 게시판" -> intent = Intent(context, BoardActivity::class.java)
                    "음역대 정보 게시판" -> intent = Intent(context, RangeBoardActivity::class.java)
                    "자유 게시판" -> intent = Intent(context, FreeBoardActivity::class.java)
                }
                intent.run { context.startActivity(this) }
            }

        }
    }


}