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
import github.karchx.motto.search_engine.citaty_info_website.items.Topic

class TopicsRecyclerAdapter(
    fragment: Fragment,
    private val topics: ArrayList<Topic>
) : RecyclerView.Adapter<TopicsRecyclerAdapter.TopicsViewHolder>() {

    private val context = fragment.requireContext()
    private val activity = fragment.requireActivity()
    private var lastPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.motto_source_item, parent, false)
        return TopicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        val topicTitle = topics[position].title
        val topicUniqueID = topics[position].uniqueID

        holder.topicTitle.text = topicTitle
        setTopicImage(holder, topicUniqueID)

        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
            holder.topicImage.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    private fun setTopicImage(holder: TopicsViewHolder, topicUniqueID: String) {
        try {
            val imageId = getTopicImageId(topicUniqueID)
            val image = ContextCompat.getDrawable(activity, imageId)
            holder.topicImage.setImageDrawable(image)
        } catch (ex: java.lang.Exception) {
        }
    }

    private fun getTopicImageId(topicUniqueID: String): Int {
        val defType = "drawable"
        val packageName = context.packageName
        return context.resources.getIdentifier(topicUniqueID, defType, packageName)
    }

    class TopicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topicTitle: TextView = itemView.findViewById(R.id.textview_motto_tab_title)
        val topicImage: ImageView = itemView.findViewById(R.id.imageview_motto_tab_image)
    }
}
