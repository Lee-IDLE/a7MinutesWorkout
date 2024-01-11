package com.example.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.ItemHistoryRowBinding

// HistoryEntity에 속성이 1개 밖에 없어 그냥 String으로 했다
class HistoryAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root){
        val llHistoryItemMain = binding.llHistoryItemMain
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // RecyclerView에 보여줄 내용들을 적어라
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var date: String = items.get(position)
        holder.tvPosition.text = (position+1).toString()
        holder.tvItem.text = date

        if(position % 2 == 0){
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else{
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }


}