package sv.edu.udb.fichaseducativas.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.navigation.NavigationStrings
import sv.edu.udb.fichaseducativas.service.FichaService

@Composable
fun ScreenFichas(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Route
        var idTematica : Int? = 0

        if(navHostController.currentBackStackEntry != null && navHostController.currentBackStackEntry?.arguments != null){
            idTematica = navHostController.currentBackStackEntry!!.arguments!!.getInt("idTematica")
        }

        // Contexto
        val context = LocalContext.current

        // Service
        val fichaService = remember { FichaService(context) }

        // Fichas
        var listaFichas by remember { mutableStateOf(fichaService.getByTematicaId(idTematica ?: 0)) }

        Button(
            onClick = {
                navHostController.navigate("${NavigationStrings.ItemMenuRouteFichasForm}?action=${NavigationStrings.ActionCreate}&idTematica=${idTematica}")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D6ED9)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Agregar")
        }

        Button(
            onClick = {
                navHostController.navigateUp()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA590)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Regresar")
        }

        if(listaFichas.count() > 0){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(listaFichas){
                        ficha ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE5CBB4)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(all = 15.dp)
                        ) {
                            Text(
                                text = ficha.Titulo,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                Button(
                                    onClick = {
                                        navHostController.navigate("${NavigationStrings.ItemMenuRouteFichasForm}?action=${NavigationStrings.ActionUpdate}&idTematica=${idTematica}&id=${ficha.Id}")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D6ED9)),
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        fichaService.delete(ficha.Id)

                                        listaFichas = fichaService.getByTematicaId(1)

                                        Toast.makeText(
                                            context,
                                            "Ficha eliminada",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA590)),
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }else{
            Text("No hay fichas creadas para esta temática...")
        }
    }
}