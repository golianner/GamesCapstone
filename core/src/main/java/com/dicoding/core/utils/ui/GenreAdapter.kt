package com.dicoding.core.utils.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.core.R
import com.dicoding.core.databinding.ItemListCategoriesBinding
import com.dicoding.core.domain.model.genre.GenresModel
import com.dicoding.core.utils.DataHelper
import java.util.ArrayList

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ListViewHolder>() {

    private var listData = ArrayList<GenresModel>()
    var onFavoriteClick: ((GenresModel) -> Unit)? = null
    var onFindClick: ((GenresModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<GenresModel>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_categories, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListCategoriesBinding.bind(itemView)
        fun bind(data: GenresModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.backgroundImage)
                    .into(ivItemImage)
                tvItemTitle.text = data.name
                tvItemSubtitle.text = DataHelper.gamesCount(data.gamesCount)
                if (data.isFavorite){
                    ibFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_fill))
                } else {
                    ibFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_border))
                }
                ibFavorite.setOnClickListener {
                    onFavoriteClick?.invoke(data)
                }
                ibFind.setOnClickListener {
                    onFindClick?.invoke(data)
                }
            }
        }
    }
}