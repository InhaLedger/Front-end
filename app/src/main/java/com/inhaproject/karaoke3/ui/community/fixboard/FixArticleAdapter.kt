package com.inhaproject.karaoke3.ui.community.fixboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemFixArticleBinding
import com.inhaproject.karaoke3.databinding.ItemNewArticleBinding
import com.inhaproject.karaoke3.ui.community.newboard.NewArticleData
import com.inhaproject.karaoke3.ui.community.newboard.ReadNewActivity

class FixArticleAdapter(
    private val articleList : ArrayList<FixArticleData>,
    val context: Context) : RecyclerView.Adapter<FixArticleAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemFixArticleBinding):
    RecyclerView.ViewHolder(binding.root){
        val title: TextView = itemView.findViewById(R.id.fix_SongTitleTextView)
        val singer: TextView = itemView.findViewById(R.id.fix_SingerTextView)
        private val like: TextView = itemView.findViewById(R.id.fix_likeTextView)
        private val writer: TextView = itemView.findViewById(R.id.fix_WriterTextView)
        private val release: TextView = itemView.findViewById(R.id.fix_releaseDate_TextView)

        fun bind(articleModel: FixArticleData, context: Context?) {
            val con = binding.root.context

            title.text = articleModel.fix_title
            singer.text = articleModel.fix_singer
            like.text = articleModel.vote.toString()
            writer.text = "작성자 : " + articleModel.userid
            release.text = "발매일 : "+ articleModel.fix_releasedate.substring(0 until 10)

            itemView.setOnClickListener {
                val intent = Intent(con, ReadFixActivity::class.java)
                intent.putExtra("게시물 번호", articleModel.fixidx.toString())
                intent.run { con.startActivity(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFixArticleBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleList[position],context)
    }

    override fun getItemCount(): Int {
        return articleList.count()
    }
}