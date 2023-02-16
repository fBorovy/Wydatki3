package pl.fboro.wydatki3.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendingDao {

    @Insert
    suspend fun insertAll(spendings: List<Spending>)

    @Delete
    suspend fun delete(spendings: List<Spending>)

    @Update
    suspend fun update(spending: Spending)

    @Query("SELECT * FROM spending_table")
    fun getAll(): Flow<List<Spending>>

    @Query("DELETE FROM spending_table")
    suspend fun dropDatabase()
}