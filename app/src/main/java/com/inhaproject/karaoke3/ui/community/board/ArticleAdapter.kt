package com.inhaproject.karaoke3.ui.community.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemArticleBinding
import java.util.*

class ArticleAdapter(
    private val articleList : ArrayList<PackArticleData>,
    val context: Context)
    :RecyclerView.Adapter<ArticleAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){

        val title: TextView = itemView.findViewById(R.id.titleTextView)
        private val writer: TextView = itemView.findViewById(R.id.packWriterTextView)
        private val price: TextView = itemView.findViewById(R.id.priceTextView)
        private val vote: TextView = itemView.findViewById(R.id.likeTextView)

        fun bind(articleModel: PackArticleData , context: Context?) {
            val con = binding.root.context

            if (articleModel.packprice == "")
                price.text = "1000원"
            else
                price.text = articleModel.packprice +"원"

            title.text = articleModel.packtitle
            writer.text = "작성자: " + articleModel.packwriter.toString()

            vote.text = articleModel.vote.toString()

            itemView.setOnClickListener {
                val intent = Intent(con, ReadPackActivity::class.java)
                intent.putExtra("게시물 번호",articleModel.packidx.toString())
                intent.run { con.startActivity(this) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context)
            , parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleList[position],context)
    }

    /*companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }
        }
    }*/

    override fun getItemCount(): Int {
        return articleList.count()
    }
}