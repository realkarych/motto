package github.karchx.motto.views.tools.adapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.items.Book

class BooksRecyclerAdapter(
    fragment: Fragment,
    private val books: ArrayList<Book>
) : RecyclerView.Adapter<BooksRecyclerAdapter.BooksViewHolder>() {

    private val context = fragment.requireContext()
    private val activity = fragment.requireActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.motto_tab_item, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val bookTitle = books[position].title
        val authorSurname = books[position].authorSurname
        val bookUniqueID = books[position].uniqueID

        setBookInfo(holder, bookTitle, authorSurname)
        setBookImage(holder, bookUniqueID)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    private fun setBookInfo(
        holder: BooksViewHolder,
        bookTitle: String,
        authorSurname: String
    ) {
        holder.bookTitle.text = getBookTitleSpan(bookTitle)
        holder.bookTitle.append(getAuthorSurnameSpan(authorSurname))
    }

    private fun setBookImage(holder: BooksViewHolder, bookUniqueID: String) {
        try {
            val imageId = getBookImageId(bookUniqueID)
            val image = ContextCompat.getDrawable(activity, imageId)
            holder.bookImage.setImageDrawable(image)
        } catch (ex: java.lang.Exception) {
        }
    }

    private fun getBookImageId(bookUniqueID: String): Int {
        val defType = "drawable"
        val packageName = context.packageName
        return context.resources.getIdentifier(bookUniqueID, defType, packageName)
    }

    private fun getBookTitleSpan(title: String): Spannable {
        val bookTitleSpan: Spannable = SpannableString(title + "\n")

        bookTitleSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.soft_white)),
            0,
            bookTitleSpan.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return bookTitleSpan
    }

    private fun getAuthorSurnameSpan(surname: String): Spannable {
        val authorSurnameSpan: Spannable = SpannableString(surname)

        authorSurnameSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.pink)),
            0,
            authorSurnameSpan.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        authorSurnameSpan.setSpan(
            RelativeSizeSpan(0.8f),
            0,
            authorSurnameSpan.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return authorSurnameSpan
    }

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.textview_motto_tab_title)
        val bookImage: ImageView = itemView.findViewById(R.id.imageview_motto_tab_image)
    }
}
