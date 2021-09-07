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
import github.karchx.motto.search_engine.citaty_info_website.items.TVChannel

class ChannelsRecyclerAdapter(
    fragment: Fragment,
    private val channels: ArrayList<TVChannel>
) : RecyclerView.Adapter<ChannelsRecyclerAdapter.ChannelsViewHolder>() {

    private val context = fragment.requireContext()
    private val activity = fragment.requireActivity()
    private var lastPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.motto_source_item, parent, false)
        return ChannelsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        val channelTitle = channels[position].title
        val channelUniqueID = channels[position].uniqueID

        holder.channelTitle.text = channelTitle
        setChannelImage(holder, channelUniqueID)

        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
            holder.channelImage.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int {
        return channels.size
    }

    private fun setChannelImage(holder: ChannelsViewHolder, channelUniqueID: String) {
        try {
            val imageId = getChannelImageId(channelUniqueID)
            val image = ContextCompat.getDrawable(activity, imageId)
            holder.channelImage.setImageDrawable(image)
        } catch (ex: java.lang.Exception) {
        }
    }

    private fun getChannelImageId(channelUniqueID: String): Int {
        val defType = "drawable"
        val packageName = context.packageName
        return context.resources.getIdentifier(channelUniqueID, defType, packageName)
    }

    class ChannelsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val channelTitle: TextView = itemView.findViewById(R.id.textview_motto_tab_title)
        val channelImage: ImageView = itemView.findViewById(R.id.imageview_motto_tab_image)
    }
}
