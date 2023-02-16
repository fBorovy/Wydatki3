package pl.fboro.wydatki3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spending_table") // obiekty wstawiane do tabeli spending_table
class Spending(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val day: Byte,
    val month: Byte,
    val year: Short,
    val title: String,
    val sum: Double,
    val from: Char
)