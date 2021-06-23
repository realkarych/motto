package github.karchx.motto.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Motto::class], version = 1, exportSchema = false)
abstract class MottoDatabase : RoomDatabase() {

    abstract fun mottoDao(): MottoDao

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
