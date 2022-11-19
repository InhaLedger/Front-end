package com.inhaproject.karaoke3.ui.community.noteboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemArticleBinding
import com.inhaproject.karaoke3.databinding.ItemNoteArticleBinding

class NoteArticleAdapter(
    private val articleList : ArrayList<NoteArticleData>,
    val context: Context)
    : RecyclerView.Adapter<NoteArticleAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding: ItemNoteArticleBinding):
        RecyclerView.ViewHolder(binding.root){

        val title: TextView = itemView.findViewById(R.id.note_SongTitleTextView)
        val singer: TextView = itemView.findViewById(R.id.note_SingerTextView)
        private val like: TextView = itemView.findViewById(R.id.note_likeTextView)
        private val writer: TextView = itemView.findViewById(R.id.note_WriterTextView)
        private val highNote: TextView = itemView.findViewById(R.id.highNote_TextView)
        private val lowNote: TextView = itemView.findViewById(R.id.lowNote_TextView)

        fun bind(articleModel: NoteArticleData, context: Context?) {
            val con = binding.root.context

            title.text = articleModel.title
            singer.text = articleModel.singer
            writer.text = "작성자: " + articleModel.note_writer.toString()
            highNote.text = "최고 음: "+articleModel.highNote
            lowNote.text = "최저 음: "+articleModel.lowNote
            like.text = articleModel.vote.toString()

            itemView.setOnClickListener {
                val intent = Intent(con, ReadNoteActivity::class.java)
                intent.putExtra("음역대 게시물 번호",articleModel.noteidx.toString())
                intent.run { con.startActivity(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteArticleBinding.inflate(
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