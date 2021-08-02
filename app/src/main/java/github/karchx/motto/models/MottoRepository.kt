package github.karchx.motto.models

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import github.karchx.motto.models.db.SavedMotto
import github.karchx.motto.models.db.SavedMottoDao

class MottoRepository(private val mottoDao: SavedMottoDao) {

    val allMottos: LiveData<List<SavedMotto>> = mottoDao.getAllMottos()

    @WorkerThread
    suspend fun insertMotto(motto: SavedMotto) {
        mottoDao.insertMotto(motto)
    }

    @WorkerThread
    suspend fun removeMotto(mottoQuote: String, mottoSource: String) {
        mottoDao.deleteMotto(mottoQuote, mottoSource)
    }
}
