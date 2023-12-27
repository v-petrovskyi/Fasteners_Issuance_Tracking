package com.v_petr.qrandbarcodescanner.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.databinding.ItemMaterialIssueRecordBinding

class MaterialItemAdapter :
    RecyclerView.Adapter<MaterialItemAdapter.MaterialItemViewHolder>() {

    private var list: MutableList<MaterialIssueRecord> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialItemViewHolder {
        val itemView = ItemMaterialIssueRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MaterialItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MaterialItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun getItem(position: Int): MaterialIssueRecord {
        return list[position]
    }

    fun deleteItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateList(list: MutableList<MaterialIssueRecord>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MaterialItemViewHolder(val binding: ItemMaterialIssueRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MaterialIssueRecord) {
            binding.item = item
        }
    }
}