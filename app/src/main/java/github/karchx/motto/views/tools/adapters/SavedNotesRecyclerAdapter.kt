package github.karchx.motto.views.tools.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.karchx.motto.R
import github.karchx.motto.models.db.user_notes.UserNote
import github.karchx.motto.models.storages.Constants

class SavedNotesRecyclerAdapter(
    private val notes: List<UserNote>
) : RecyclerView.Adapter<SavedNotesRecyclerAdapter.SavedNotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return SavedNotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNotesViewHolder, position: Int) {
        val noteTitle = getNoteTitle(notes[position].quote)
        val noteSource = notes[position].source
        val noteDateSaved = notes[position].dateSaved

        holder.noteTitle.text = noteTitle
        holder.noteSource.text = noteSource
        holder.noteDateAdded.text = noteDateSaved

        holder.openMenuBtn.setOnClickListener {
            Log.d("кнопка", "нажата")
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    private fun getNoteTitle(quote: String): String {
        val noteTitleWordsArray = quote.split(" ")

        if (noteTitleWordsArray.size > Constants.QUANTITY_WORDS_IN_MOTTO_TITLE) {
            val cutNoteTitleWordsArray =
                noteTitleWordsArray.take(Constants.QUANTITY_WORDS_IN_MOTTO_TITLE)
            var noteTitle = cutNoteTitleWordsArray.joinToString(separator = " ")
            noteTitle = "$noteTitle <...>"
            return noteTitle
        } else {
            return quote
        }
    }

    class SavedNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.textview_note_title)
        val noteSource: TextView = itemView.findViewById(R.id.textview_note_source)
        val noteDateAdded: TextView = itemView.findViewById(R.id.textview_note_time_added)
        val openMenuBtn: ImageView = itemView.findViewById(R.id.imageview_note_menu)
    }
}