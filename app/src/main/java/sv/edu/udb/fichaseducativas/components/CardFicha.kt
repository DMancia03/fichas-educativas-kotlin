package sv.edu.udb.fichaseducativas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.fichaseducativas.models.Ficha

@Composable
fun CardFicha(
    ficha: Ficha
) {
    // Variables de estado
    val (iconCard, setIconCard) = remember { mutableStateOf(Icons.Default.ArrowForward) }
    val (esCara, setEsCara) = remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth().height(500.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE5CBB4)
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(all = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        if(iconCard == Icons.Default.ArrowForward){
                            setIconCard(Icons.Default.ArrowBack)
                            setEsCara(false)
                        }else{
                            setIconCard(Icons.Default.ArrowForward)
                            setEsCara(true)
                        }
                    }
                ) {
                    Icon(
                        imageVector = iconCard,
                        contentDescription = "Voltear carta",
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(all = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ficha.Titulo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(esCara){
                    Text(
                        text = ficha.DescripcionCara,
                        fontSize = 20.sp,
                    )
                }else{
                    Text(
                        text = ficha.DescripcionAtras,
                        fontSize = 20.sp
                    )
                }
            }

        }
    }
}