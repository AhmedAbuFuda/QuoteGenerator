package com.abufoda.quotegenerator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abufoda.quotegenerator.R
import com.abufoda.quotegenerator.model.entity.MyDataItem

class FavoriteQuoteRecyclerView : RecyclerView.Adapter<FavoriteQuoteRecyclerView.FavoriteViewHolder>() {

    var onClick: OnClick? = null
    var quoteList : List<MyDataItem> = emptyList()

    fun setList(quoteList : List<MyDataItem>){
        this.quoteList = quoteList
        notifyDataSetChanged()
    }

    fun setFilteredList(quoteList: List<MyDataItem>){
        this.quoteList = quoteList
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var content: TextView = itemView.findViewById(R.id.content)
        var author: TextView = itemView.findViewById(R.id.author)
        var favoriteBtn: Button = itemView.findViewById(R.id.favoriteBtn)

      fun bind(quote: MyDataItem){
          content.text = quote.content
          author.text = quote.author
          favoriteBtn.setOnClickListener{
              onClick?.onClick(quote)
          }
      }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        var quote: MyDataItem = quoteList[position]
        holder.bind(quote)
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }
}