package github.karchx.motto.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.data.Motto

class RandomMottosRecyclerAdapter(
    private val mottos: ArrayList<Motto>
) : RecyclerView.Adapter<RandomMottosRecyclerAdapter.RandomMottosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomMottosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.motto_item, parent, false)
        return RandomMottosViewHolder(view)
    }

    override fun onBindViewHolder(holder: RandomMottosViewHolder, position: Int) {
        val mottoTitle = mottos[position].quote.take(30) + "..."
        val mottoSource = mottos[position].source

        holder.mottoTitle.text = mottoTitle
        holder.mottoSource.text = mottoSource
    }

    override fun getItemCount(): Int {
        return mottos.size
    }

    class RandomMottosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mottoTitle: TextView = itemView.findViewById(R.id.textview_motto_title)
        val mottoSource: TextView = itemView.findViewById(R.id.textview_motto_source)
    }
}
