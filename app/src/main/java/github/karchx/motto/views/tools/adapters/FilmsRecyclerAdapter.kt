package github.karchx.motto.views.tools.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.items.Film

class FilmsRecyclerAdapter(
    fragment: Fragment,
    private val films: ArrayList<Film>
) : RecyclerView.Adapter<FilmsRecyclerAdapter.FilmsViewHolder>() {

    private val context = fragment.requireContext()
    private val activity = fragment.requireActivity()
    private var lastPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.motto_source_item, parent, false)
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val filmTitle = films[position].title
        val filmUniqueID = films[position].uniqueID

        holder.filmTitle.text = filmTitle
        setFilmImage(holder, filmUniqueID)

        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
            holder.filmImage.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

    private fun setFilmImage(holder: FilmsViewHolder, filmUniqueID: String) {
        try {
            val imageId = getFilmImageId(filmUniqueID)
            val image = ContextCompat.getDrawable(activity, imageId)
            holder.filmImage.setImageDrawable(image)
        } catch (ex: java.lang.Exception) {
        }
    }

    private fun getFilmImageId(filmUniqueID: String): Int {
        val defType = "drawable"
        val packageName = context.packageName
        return context.resources.getIdentifier(filmUniqueID, defType, packageName)
    }

    class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filmTitle: TextView = itemView.findViewById(R.id.textview_motto_tab_title)
        val filmImage: ImageView = itemView.findViewById(R.id.imageview_motto_tab_image)
    }
}
