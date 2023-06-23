package com.dicoding.core.utils.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.core.R
import com.dicoding.core.databinding.ItemListPostsBinding
import com.dicoding.core.domain.model.axa_test.AxaTestModel

class PostAdapter : RecyclerView.Adapter<PostAdapter.ListViewHolder>() {

    private var listData = ArrayList<AxaTestModel>()
    var onItemClick: ((AxaTestModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<AxaTestModel>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_posts, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPostsBinding.bind(itemView)
        fun bind(data: AxaTestModel) {
            with(binding) {
                tvTitle.text = data.title
                tvBody.text = data.body
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }

}