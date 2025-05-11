package sv.edu.udb.fichaseducativas.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.navigation.NavigationStrings
import sv.edu.udb.fichaseducativas.service.FichaService

@Composable
fun ScreenFichas(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column {
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

        Text(
            text = "Fichas educativas"
        )

        Button({
            navHostController.navigate("${NavigationStrings.ItemMenuRouteFichasForm}?action=${NavigationStrings.ActionCreate}&idTematica=${idTematica}")
        }) {
            Text("Agregar")
        }

        Button({
            navHostController.navigateUp()
        }) {
            Text("Regresar")
        }

        if(listaFichas.count() > 0){
            LazyColumn {
                items(listaFichas){
                        ficha ->
                    Column {
                        Text(
                            text = ficha.Titulo
                        )

                        Row {
                            Button({
                                navHostController.navigate("${NavigationStrings.ItemMenuRouteFichasForm}?action=${NavigationStrings.ActionUpdate}&idTematica=${idTematica}&id=${ficha.Id}")
                            }) {
                                Text("Editar")
                            }

                            Button({
                                fichaService.delete(ficha.Id)

                                listaFichas = fichaService.getByTematicaId(1)

                                Toast.makeText(
                                    context,
                                    "Ficha eliminada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Text("Eliminar")
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