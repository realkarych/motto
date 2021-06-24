package github.karchx.motto.models

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import github.karchx.motto.models.db.Motto
import github.karchx.motto.models.db.MottoDao

class MottoRepository(private val mottoDao: MottoDao) {

    val allMottos: LiveData<List<Motto>> = mottoDao.getAllMottos()

    @WorkerThread
    suspend fun insertMotto(motto: Motto) {
        mottoDao.insertMotto(motto)
    }

    @WorkerThread
    suspend fun removeMotto(mottoQuote: String, mottoSource: String) {
        mottoDao.deleteMotto(mottoQuote, mottoSource)
    }
}
