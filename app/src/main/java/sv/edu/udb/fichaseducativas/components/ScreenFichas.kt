package sv.edu.udb.fichaseducativas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ScreenFichas(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column {
        Text(
            text = "Fichas educativas"
        )
    }
}