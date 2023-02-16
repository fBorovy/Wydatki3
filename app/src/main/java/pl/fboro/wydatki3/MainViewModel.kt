package pl.fboro.wydatki3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.fboro.wydatki3.data.Spending
import pl.fboro.wydatki3.data.SpendingDb
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository(app.applicationContext)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repo.dropDatabase()
            populateDb()
        }
    }

    private fun populateDb() {
        val source: List<Char> = listOf('R','B','G')
        var i = 0
        repeat(3){
            val time = System.currentTimeMillis()
            val ran = Random.nextDouble()
            val spending = Spending(day = 3, month = 12, year = 2022 ,title = "${time % 100}", sum = ran, from = source[i])
            CoroutineScope(viewModelScope.coroutineContext).launch {
                repo.insertAll(listOf(spending))
            }
            i++
        }
    }
    fun getSpendings(): Flow<List<Spending>> {
        return repo.getAll()
    }

    fun deleteSpending(spending: Spending) = CoroutineScope(viewModelScope.coroutineContext).launch {
        repo.delete(listOf(spending))
    }



}