package com.inhaproject.karaoke3.ui.mypage

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemMypageBinding
import com.inhaproject.karaoke3.ui.mypage.admin.AdminActivity
import com.inhaproject.karaoke3.ui.mypage.mynote.MyNoteActivity
import com.inhaproject.karaoke3.ui.mypage.mysearch.MySearchActivity
import com.inhaproject.karaoke3.ui.mypage.record.RecordActivity

class MyPageAdapter (fragment: MyPageFragment) : RecyclerView.Adapter<MyPageAdapter.ViewHolder>() {

    var data = mutableListOf<MyPageData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMypageBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(private val binding: ItemMypageBinding) : RecyclerView.ViewHolder(binding.root) {

        private val txtName: TextView = itemView.findViewById(R.id.rv_myPage_title)
        private val imgMyPage: ImageView = itemView.findViewById(R.id.rv_myPage_img)

        private val context = binding.root.context

        fun bind(item: MyPageData) {
            txtName.text = item.name
            Glide.with(itemView).load(item.img).into(imgMyPage)
            itemView.setOnClickListener {
                var intent = Intent(context, RecordActivity::class.java)
                when(item.name) {
                    "목소리 녹음 / 분석" -> intent = Intent(context, RecordActivity::class.java)
                    "나의 음역대" -> intent = Intent(context,MyNoteActivity::class.java)
                    "맞춤 노래 추천" -> intent = Intent(context, MySearchActivity::class.java)
                    "내 투표 현황" -> intent = Intent(context, MyProposalActivity::class.java)
                    "관리자 메뉴" -> intent = Intent(context, AdminActivity::class.java)
                }
                intent.run { context.startActivity(this) }
            }

        }
    }


}