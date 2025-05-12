package sv.edu.udb.fichaseducativas.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.fichaseducativas.models.Ficha

@Composable
fun CardFicha(
    ficha: Ficha,
    esCara : Boolean,
    setEsCara : (esCara : Boolean) -> Unit
) {
    // Variables de estado
    val (iconCard, setIconCard) = remember { mutableStateOf(Icons.Outlined.Autorenew) }
    val rotar by animateFloatAsState(
        targetValue = if(!esCara) 180f else 0f,
        animationSpec = tween(500)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .graphicsLayer {
                rotationY = rotar
                //cameraDistance = 8 * density
            },
        colors = CardDefaults.cardColors(
            containerColor = if(esCara) Color(0xFF94e7ff) else Color(0xFFa4d495)
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 15.dp)
                .graphicsLayer {
                    rotationY = rotar
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(all = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ficha.Titulo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    //color = if(esCara) Color.Black else Color.White
                )
            }

            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if(esCara){
                    Text(
                        text = ficha.DescripcionCara,
                        fontSize = 20.sp,
                        //color = if(esCara) Color.Black else Color.White
                    )
                }else{
                    Text(
                        text = ficha.DescripcionAtras,
                        fontSize = 20.sp,
                        //color = if(esCara) Color.Black else Color.White
                    )
                }

                IconButton(
                    onClick = {
                        setEsCara(!esCara)
                    },
                    modifier = Modifier.padding(all = 20.dp)
                ) {
                    Icon(
                        imageVector = iconCard,
                        contentDescription = "Voltear carta",
                        modifier = Modifier.size(100.dp),
                    )
                }
            }
        }
    }
}