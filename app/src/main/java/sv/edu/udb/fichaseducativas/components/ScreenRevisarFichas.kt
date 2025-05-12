package sv.edu.udb.fichaseducativas.components

import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.navigation.NavigationStrings
import sv.edu.udb.fichaseducativas.service.FichaService
import sv.edu.udb.fichaseducativas.service.TematicaService

@Composable
fun ScreenRevisarFichas(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Contexto
        val context = LocalContext.current

        // Obtener id tematica desde route
        var idTematica : Int = 0

        if(navHostController.currentBackStackEntry != null && navHostController.currentBackStackEntry?.arguments != null){
            idTematica = navHostController.currentBackStackEntry!!.arguments!!.getInt("idTematica")
        }

        // Validar id tematica
        if(idTematica <= 0){
            navHostController.navigateUp()

            Toast.makeText(
                context,
                "No ha seleccionado una temática",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        //Log.i("LOG_ERRORES", "ID TEMATICA NO ENCONTRADA")

        // Service
        val tematicaService = remember { TematicaService(context) }
        val fichaService = remember { FichaService(context) }

        // Tematica
        val tematica = tematicaService.getById(idTematica)

        // Fichas
        val listaFichas = fichaService.getByTematicaId(idTematica)

        if (listaFichas.count() <= 0){
            navHostController.navigateUp()

            Toast.makeText(
                context,
                "No hay fichas creadas para esta temática",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        //Log.i("LOG_ERRORES", "FICHAS NO EXISTEN")

        val indexStart = 0
        var (indexCurrent, setIndexCurrent) = remember { mutableStateOf(0) }
        val indexEnd = listaFichas.count() - 1

        val (ficha, setFicha) = remember { mutableStateOf(listaFichas.get(indexCurrent)) }

        // TITULO ----------------------------------------------------------------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF56413E)
            ),
        ) {
            Row(
                modifier = Modifier.padding(all = 15.dp).align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Temática: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = tematica.Nombre,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }

        // CONTENIDO -------------------------------------------------------------------------------
        val (btn01, setBtn01) = remember { mutableStateOf(NavigationStrings.BtnRegresar) }
        val (btn02, setBtn02) = remember { mutableStateOf(NavigationStrings.BtnSiguiente) }

        CardFicha(
            ficha = ficha
        )

        // ACCIONES --------------------------------------------------------------------------------
        if(listaFichas.count() == 1){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if(btn01 == NavigationStrings.BtnRegresar){
                            navHostController.navigateUp()

                            return@Button
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D6ED9)
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = btn01,
                        fontSize = 15.sp
                    )
                }
            }
        }else{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        val index = indexCurrent - 1

                        if(btn01 == NavigationStrings.BtnRegresar){
                            navHostController.navigateUp()

                            return@Button
                        }else if(btn01 == NavigationStrings.BtnAnterior){
                            setFicha(listaFichas.get(index))

                            if(index == indexStart){
                                setBtn01(NavigationStrings.BtnRegresar)
                            }

                            setBtn02(NavigationStrings.BtnSiguiente)

                            Log.i("LOG_ERRORES", ficha.Titulo)
                        }

                        setIndexCurrent(index)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D6ED9)
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.width(160.dp)
                ) {
                    Text(
                        text = btn01,
                        fontSize = 15.sp
                    )
                }

                Button(
                    onClick = {
                        val index = indexCurrent + 1

                        if(btn02 == NavigationStrings.BtnRegresar){
                            navHostController.navigateUp()

                            return@Button
                        }else if (btn02 == NavigationStrings.BtnSiguiente){


                            setFicha(listaFichas.get(index))

                            if(index == indexEnd){
                                setBtn02(NavigationStrings.BtnRegresar)
                            }

                            setBtn01(NavigationStrings.BtnAnterior)

                            Log.i("LOG_ERRORES", ficha.Titulo)

                        }

                        setIndexCurrent(index)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D6ED9)
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.width(160.dp)
                ) {
                    Text(
                        text = btn02,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}