package sv.edu.udb.fichaseducativas.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.navigation.NavigationStrings
import sv.edu.udb.fichaseducativas.service.FichaService

@Composable
fun ScreenFichasForm(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Route
        var action : String? = NavigationStrings.ActionCreate
        var idTematica : Int? = 0
        var idToUpdate : Int? = 0

        if(navHostController.currentBackStackEntry != null && navHostController.currentBackStackEntry?.arguments != null){
            action = navHostController.currentBackStackEntry!!.arguments!!.getString("action")
            idTematica = navHostController.currentBackStackEntry!!.arguments!!.getInt("idTematica")
            idToUpdate = navHostController.currentBackStackEntry!!.arguments!!.getInt("id")
        }

        // Contexto
        val context = LocalContext.current

        // Services
        val fichaService = FichaService(context)

        // Ficha default
        var fichaDefault = Ficha(0, 0, "", "", "", "")

        // Cuando se update
        if(action == NavigationStrings.ActionUpdate && idToUpdate != null){
            fichaDefault = fichaService.getById(idToUpdate)
        }

        // Variables de formulario
        val (titulo, setTitulo) = remember { mutableStateOf(fichaDefault.Titulo) }
        val (descripcionCara, setDescripcionCara) = remember { mutableStateOf(fichaDefault.DescripcionCara) }
        val (descripcionAtras, setDescripcionAtras) = remember { mutableStateOf(fichaDefault.DescripcionAtras) }
        val (imagen, setImagen) = remember { mutableStateOf(fichaDefault.Imagen) }

        Text(
            text = if(action == NavigationStrings.ActionCreate) "Agregar ficha" else "Editar ficha",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = { setTitulo(it) },
            label = {
                Text("Título:")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcionCara,
            onValueChange = { setDescripcionCara(it) },
            label = {
                Text("Descripción (Cara):")
            },
            singleLine = false,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcionAtras,
            onValueChange = { setDescripcionAtras(it) },
            label = {
                Text("Descripción (Atrás):")
            },
            singleLine = false,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        /*OutlinedTextField(
            value = imagen,
            onValueChange = { setImagen(it) },
            label = {
                Text("Titulo:")
            },
            singleLine = true
        )*/

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    if(idTematica != null){
                        if(titulo.isNullOrBlank() || titulo.isNullOrEmpty()){
                            Toast.makeText(
                                context,
                                "Debe ingresar un título...",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        if(descripcionCara.isNullOrBlank() || descripcionCara.isNullOrEmpty()){
                            Toast.makeText(
                                context,
                                "Debe ingresar una descripción (cara)...",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        if(descripcionAtras.isNullOrBlank() || descripcionAtras.isNullOrEmpty()){
                            Toast.makeText(
                                context,
                                "Debe ingresar una descripción (atrás)...",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        if(action == NavigationStrings.ActionCreate){
                            fichaService.add(Ficha(0, idTematica, titulo, descripcionCara, descripcionAtras, imagen))

                            Toast.makeText(
                                context,
                                "Ficha creada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            fichaService.update(Ficha(idToUpdate ?: 0, idTematica, titulo, descripcionCara, descripcionAtras, imagen))

                            Toast.makeText(
                                context,
                                "Ficha actualizada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        navHostController.navigateUp()
                    }else{
                        Toast.makeText(
                            context,
                            "Algo salió mal. No se pudo guardar la ficha.",
                            Toast.LENGTH_SHORT
                        ).show()

                        navHostController.navigateUp()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D6ED9)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Guardar")
            }

            Button(
                onClick = {
                    navHostController.navigateUp()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA590)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Cancelar")
            }
        }
    }
}