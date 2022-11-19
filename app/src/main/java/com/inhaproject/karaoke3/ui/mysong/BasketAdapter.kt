package com.inhaproject.karaoke3.ui.mysong

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ItemBasketBinding
import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketAdapter (private val MySongList : ArrayList<BasketData>, val context: Context)
    : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    var no = ""

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
        private val menuBtn : ImageButton = itemView.findViewById(R.id.basketMenuButton)

        private val context = binding.root.context

        fun bind(item: BasketData) {
            txtName.text = item.title
            txtSinger.text = item.singer
            Glide.with(itemView).load(item.imageurl).into(imgBasket)

            menuBtn.setOnClickListener {
                no = item.no
                val popup = PopupMenu(context, binding.basketMenuButton)
                popup.menuInflater.inflate(R.menu.basket_menu, popup.menu)

                val listener = PopupMenuListener()
                popup.setOnMenuItemClickListener(listener)
                popup.show()
            }

        }
    }

    inner class PopupMenuListener : PopupMenu.OnMenuItemClickListener{

        private val api = RetroInterface.create()

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.deleteItem -> {
                    val builder = AlertDialog.Builder(context)
                        .setTitle("삭제")
                        .setMessage("정말 삭제 하시겠습니까?")

                    builder.setPositiveButton("확인") {
                        dialog, which ->
                        run {
                            api.deleteMySong(no).enqueue(object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    if (response.code() == 200) {
                                        Toast.makeText(
                                            context, "즐겨찾기에서 삭제되었습니다. ", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Toast.makeText(
                                        context, "즐겨찾기 서버 연결 실패 ", Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("즐겨찾기 삭제 실패", t.message.toString())
                                }

                            })
                        }
                    }
                    builder.show()

                }
            }
            return false
        }
    }

    override fun getItemCount(): Int {
        return MySongList.count()
    }


}