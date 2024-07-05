package ru.itis.easyenglish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelsAdapter() : RecyclerView.Adapter<LevelsAdapter.LevelViewHolder>() {
    private val levels: List<String> = listOf("A1", "A2", "B1", "B2")
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val level: TextView = itemView.findViewById(R.id.levelDif)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_level, parent, false)
        return LevelViewHolder(itemView)
    }

    override fun getItemCount(): Int = levels.size

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val currentItem = levels[position]
        holder.level.text = currentItem
    }
}
