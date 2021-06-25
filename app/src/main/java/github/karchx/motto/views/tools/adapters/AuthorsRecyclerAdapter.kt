package github.karchx.motto.views.tools.adapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.data.Author

class AuthorsRecyclerAdapter(
    private val fragment: Fragment,
    private val authors: ArrayList<Author>
) : RecyclerView.Adapter<AuthorsRecyclerAdapter.AuthorsViewHolder>() {

    private val context = fragment.requireContext()
    private val activity = fragment.requireActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.author_item, parent, false)
        return AuthorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        val authorName = authors[position].name
        val authorSurname = authors[position].surname
        val authorUniqueID = authors[position].uniqueID

        setAuthorName(holder, authorName, authorSurname)
        setAuthorImage(holder, authorUniqueID)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    private fun setAuthorName(
        holder: AuthorsViewHolder,
        authorName: String,
        authorSurname: String
    ) {
        holder.authorName.text = getAuthorNameSpan(authorName)
        holder.authorName.append(getAuthorSurnameSpan(authorSurname))
    }

    private fun setAuthorImage(holder: AuthorsViewHolder, authorUniqueID: String) {
        try {
            val imageId = getAuthorImageId(authorUniqueID)
            val image = ContextCompat.getDrawable(activity, imageId)
            holder.authorImage.setImageDrawable(image)
        } catch (ex: java.lang.Exception) {
        }
    }

    private fun getAuthorImageId(authorUniqueID: String): Int {
        val defType = "drawable"
        val packageName = context.packageName
        return context.resources.getIdentifier(authorUniqueID, defType, packageName)
    }

    private fun getAuthorNameSpan(name: String): Spannable {
        val authorNameSpan: Spannable = SpannableString(name + "\n")

        authorNameSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.soft_white)),
            0,
            authorNameSpan.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return authorNameSpan
    }

    private fun getAuthorSurnameSpan(surname: String): Spannable {
        val authorSurnameSpan: Spannable = SpannableString(surname)

        authorSurnameSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.pink)),
            0,
            authorSurnameSpan.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return authorSurnameSpan
    }

    class AuthorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var authorName: TextView = itemView.findViewById(R.id.textview_author_name)
        val authorImage: ImageView = itemView.findViewById(R.id.imageview_author_image)
    }
}
