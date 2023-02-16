package pl.fboro.wydatki3

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pl.fboro.wydatki3.data.Spending
import pl.fboro.wydatki3.data.SpendingDao
import pl.fboro.wydatki3.data.SpendingDb
import kotlin.random.Random

class Repository(context: Context): SpendingDao {

    private val dao = SpendingDb.getInstance(context).spendingDao()

    override suspend fun insertAll(spendings: List<Spending>) = withContext(Dispatchers.IO){
        dao.insertAll(spendings)
    }

    override suspend fun delete(spendings: List<Spending>) = withContext(Dispatchers.IO){
        dao.delete(spendings)
    }

    override suspend fun update(spending: Spending) = withContext(Dispatchers.IO){
        dao.update(spending)
    }

    override fun getAll(): Flow<List<Spending>> {
        return dao.getAll()
    }

    override suspend fun dropDatabase() = withContext(Dispatchers.IO){
        dao.dropDatabase()
    }


}