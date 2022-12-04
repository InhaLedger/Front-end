package com.inhaproject.karaoke3.ui.community.newboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemNewArticleBinding


class NewArticleAdapter(
    private val articleList : ArrayList<NewArticleData>,
    val context: Context)
    : RecyclerView.Adapter<NewArticleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemNewArticleBinding):
        RecyclerView.ViewHolder(binding.root){
        val title: TextView = itemView.findViewById(R.id.new_SongTitleTextView)
        val singer: TextView = itemView.findViewById(R.id.new_SingerTextView)
        private val like: TextView = itemView.findViewById(R.id.new_likeTextView)
        private val writer: TextView = itemView.findViewById(R.id.new_WriterTextView)
        private val release: TextView = itemView.findViewById(R.id.new_releaseDate_TextView)

        fun bind(articleModel: NewArticleData, context: Context?) {
            val con = binding.root.context

            title.text = articleModel.new_title
            singer.text = articleModel.new_singer
            like.text = articleModel.upvote.toString()
            writer.text = "작성자 : " + articleModel.userid
            release.text = "발매일 : "+ articleModel.new_releasedate.substring(0 until 10)

            itemView.setOnClickListener {
                val intent = Intent(con, ReadNewActivity::class.java)
                intent.putExtra("게시물 번호", articleModel.newidx.toString())
                intent.putExtra("추천 수",articleModel.upvote.toString())
                intent.putExtra("비추천 수",articleModel.downvote.toString())
                intent.run { con.startActivity(this) }
            }
        }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewArticleBinding.inflate(
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