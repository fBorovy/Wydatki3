package pl.fboro.wydatki3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Spending::class], version = 1)
abstract  class SpendingDatabase : RoomDatabase() {
    abstract fun spendingDao(): SpendingDao
}

object SpendingDb{
    private var db: SpendingDatabase ?= null

    fun getInstance(context: Context): SpendingDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context,
                SpendingDatabase::class.java,
                "spending-database"
            ).build()
        }
        return db!!
    }
}