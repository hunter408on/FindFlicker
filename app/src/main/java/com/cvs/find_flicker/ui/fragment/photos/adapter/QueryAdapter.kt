package com.cvs.find_flicker.ui.fragment.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cvs.find_flicker.databinding.*

class QueryAdapter(private val onItemSelected: (query: String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val HEADER_TYPE = 1
        private val NO_ITEM_TYPE = 2
        private val QUERY_ITEM = 3
    }
    private val queryList = mutableListOf<String>()

    fun updateQueryList(newQueryList: List<String>) {
        queryList.clear()
        queryList.addAll(newQueryList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER_TYPE) {
            return HeaderViewHolder(ItemQueryHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else if (viewType == NO_ITEM_TYPE) {
            return NoItemViewHolder(ItemNoQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            return QueryViewHolder(ItemSearchQueryBinding.inflate(LayoutInflater.from(parent.context),parent, false),
                onItemSelected
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QueryViewHolder) {
            holder.bind(queryList[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemCount == 1) {
            return NO_ITEM_TYPE
        } else {
            if (position == 0) {
                return HEADER_TYPE
            } else {
                return QUERY_ITEM
            }
        }
    }

    override fun getItemCount(): Int {
        val querySize = queryList.size
        if (querySize == 0) {
            return 1
        } else {
            return querySize + 1
        }
    }

    class QueryViewHolder(
        private val binding: ItemSearchQueryBinding,
        private val onItemSelected: (query: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var query: String
        init {
            binding.root.setOnClickListener {
                onItemSelected(query)
            }
        }

        fun bind(item: String) {
            query = item
            binding.query.text = item
        }
    }

    class HeaderViewHolder(
        private val binding: ItemQueryHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class NoItemViewHolder(
        private val binding: ItemNoQueryBinding
    ) : RecyclerView.ViewHolder(binding.root)
}