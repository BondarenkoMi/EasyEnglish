package ru.itis.easyenglish.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.itis.easyenglish.R

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
        val icon : ImageView = itemView.findViewById(R.id.level_logo)

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
        when(position) {
            0 -> holder.icon.setImageResource(R.drawable.ic_a1)
            1 -> holder.icon.setImageResource(R.drawable.ic_a2)
            2 -> holder.icon.setImageResource(R.drawable.ic_b1)
            3 -> holder.icon.setImageResource(R.drawable.ic_b2)
        }
    }
}
