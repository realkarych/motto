package github.karchx.motto.models.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.models.db.saved_motto.SavedMottoDao
import github.karchx.motto.models.db.user_notes.UserNote
import github.karchx.motto.models.db.user_notes.UserNoteDao

@Database(entities = [SavedMotto::class, UserNote::class], version = 2, exportSchema = false)
abstract class MottoDatabase : RoomDatabase() {

    abstract fun savedMottoDao(): SavedMottoDao
    abstract fun userNoteDao(): UserNoteDao

    companion object {
        @Volatile
        private var INSTANCE: MottoDatabase? = null

        fun getDatabase(
            context: Context
        ): MottoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MottoDatabase::class.java,
                    "motto_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
