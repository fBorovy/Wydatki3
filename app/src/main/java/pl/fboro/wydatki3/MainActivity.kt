package pl.fboro.wydatki3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.fboro.wydatki3.data.Spending
import pl.fboro.wydatki3.ui.theme.Gray100
import pl.fboro.wydatki3.ui.theme.Wydatki3Theme

class MainActivity : ComponentActivity() {
    private val mainVm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wydatki3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val spendings = mainVm.getSpendings().collectAsState(initial = emptyList())
                    SpendingList(
                        spendings = spendings.value,
                        onDelete = { spending -> mainVm.deleteSpending(spending) }
                    )
                }
            }
        }
    }
}

@Composable
fun SpendingList(spendings: List<Spending>, onDelete: ((Spending) -> Unit)? = null) {
    Column( modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,) {
        SpendingLazyColumn(spendings, onDelete)
    }
}

@Composable
fun SpendingLazyColumn(spendings: List<Spending>, onDelete: ((Spending) -> Unit)? = null){
    LazyColumn {
        items(items = spendings, key = { it.uid }) { spending ->
            SpendingRow(spending, onDelete)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpendingRow(spending: Spending, onDelete: ((Spending) -> Unit)? = null) {
    val dismissState = rememberDismissState(confirmStateChange = {
        if (it == DismissValue.DismissedToStart)
            onDelete?.invoke(spending)
        true
    })

    SwipeToDismiss(state = dismissState,
        background = {
                     Row(modifier = Modifier
                         .fillMaxWidth()
                         .padding(start = 100.dp, end = 10.dp, top = 2.dp, bottom = 2.dp)
                         .background(Gray100),
                         horizontalArrangement = Arrangement.End
                     ) {
                         val imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete)
                         Icon(imageVector = imageVector, contentDescription = null)
                     }
        },
        dismissThresholds = {FractionalThreshold(0.5f)},
        directions = setOf(DismissDirection.EndToStart)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "${spending.day}.${spending.month}: ${spending.sum.format(2)} ${spending.title} ")
                if (spending.from == 'B')
                    Text(text = "${spending.from}", color = Color.Green)
                if (spending.from == 'R')
                    Text(text = "${spending.from}", color = Color.Blue)
                if (spending.from == 'G')
                    Text(text = "${spending.from}", color = Color.Red)
            }
        }
    }
}

@Composable
fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Preview(showBackground = true)
@Composable
fun SpendingRowPreview() {
    val spending1 = Spending(day = 3, month = 12, year = 2022 ,title = "test", sum = 100.00, from = 'B')
    SpendingRow(spending = spending1)
}