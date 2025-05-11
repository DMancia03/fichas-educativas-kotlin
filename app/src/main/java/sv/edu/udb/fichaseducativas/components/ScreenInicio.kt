package sv.edu.udb.fichaseducativas.components
//Version buena
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sv.edu.udb.fichaseducativas.models.Ficha
import sv.edu.udb.fichaseducativas.navigation.NavigationStrings
import sv.edu.udb.fichaseducativas.service.FichaService
import sv.edu.udb.fichaseducativas.service.TematicaService

@Composable
fun ScreenInicio(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val fichaService = remember { FichaService(context) }
    val tematicaService = remember { TematicaService(context) }

    var tematicas by remember { mutableStateOf(tematicaService.getAllTematicas()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Temáticas",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tematicas) { tematica ->
                val fichasDeEstaTematica = fichaService.contarFichasPorTematica(tematica.Id)

                var expanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE5CBB4))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Fila con el titulo y el menu desplegable
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = tematica.Nombre,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                ),
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.Top)
                            )

                            Box(
                                contentAlignment = Alignment.TopEnd,
                                //modifier = Modifier.wrapContentSize() // ERROR
                            ) {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "Opciones",
                                        tint = Color.White
                                    )
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    offset = DpOffset(x = (-24).dp, y = (0).dp),// ajuste de posición del menu desplegable
                                    modifier = Modifier.background(Color(0xFF56413E))
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Agregar ficha", color = Color.White) },
                                        onClick = {
                                            expanded = false

                                            // Acción para agregar ficha->Navegar a la pantalla de agregar ficha
                                            navHostController.navigate("${NavigationStrings.ItemMenuRouteFichasForm}?action=${NavigationStrings.ActionCreate}&idTematica=${tematica.Id}")

                                        }
                                    )
                                    Divider(modifier = Modifier.padding(horizontal = 8.dp))//linea divisora entre opciones
                                    DropdownMenuItem(
                                        text = { Text("Administrar", color = Color.White) },
                                        onClick = {
                                            expanded = false


                                            // Acción para administrar tematica->Navegar a la pantalla de fichas
                                            navHostController.navigate("${NavigationStrings.ItemMenuRouteFichas}?idTematica=${tematica.Id}")

                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Fichas totales: ${fichasDeEstaTematica}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {

                                // Acción del botón "Ver fichas" -> Navegar a la pantalla de fichas
                                navHostController.navigate("${NavigationStrings.ItemMenuRouteFichasRevisar}?idTematica=${tematica.Id}")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3D6ED9)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                "Ver fichas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


