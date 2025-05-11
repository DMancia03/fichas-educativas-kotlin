package sv.edu.udb.fichaseducativas.components


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sv.edu.udb.fichaseducativas.models.Tematica
import sv.edu.udb.fichaseducativas.service.FichaService
import sv.edu.udb.fichaseducativas.service.TematicaService
@Composable
fun ScreenTematica(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val service = remember { TematicaService(context) }
    val fichaService = remember { FichaService(context) } // Usamos el servicio de fichas


    var nombreTematica by remember { mutableStateOf("") }
    var listaTematicas by remember { mutableStateOf(service.getAllTematicas()) }

    // Estados para edición
    var tematicaSeleccionada by remember { mutableStateOf<Tematica?>(null) }
    val estaEditando = tematicaSeleccionada != null

    // Estado para el diálogo de confirmación
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var tematicaAEliminar by remember { mutableStateOf<Tematica?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (estaEditando) "Editar temática" else "Agregar nueva temática",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombreTematica,
            onValueChange = { nombreTematica = it },
            label = { Text("Nombre de temática") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (nombreTematica.isNotBlank()) {
                    if (estaEditando) {
                        tematicaSeleccionada?.let {
                            it.Nombre = nombreTematica
                            service.updateTematica(it)
                        }
                    } else {
                        val nueva = Tematica(nombreTematica)
                        service.insertTematica(nueva)
                    }

                    // Resetear valores
                    nombreTematica = ""
                    tematicaSeleccionada = null
                    listaTematicas = service.getAllTematicas()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA590))
        ) {
            Text(
                text = if (estaEditando) "Actualizar" else "Agregar",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Temáticas:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(listaTematicas) { tematica ->
                val totalFichas = fichaService.contarFichasPorTematica(tematica.Id)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0xFFE5CBB4), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE5CBB4))
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = tematica.Nombre,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                ),
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Fichas totales: $totalFichas",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Color.White
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier
                                .padding(end = 0.dp)
                                .weight(0.5f)
                        ) {
                            Button(
                                onClick = {
                                    tematicaSeleccionada = tematica
                                    nombreTematica = tematica.Nombre
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D6ED9)),
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text("Modificar", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Button(
                                onClick = {
                                    // Mostrar el diálogo de confirmación antes de eliminar
                                    tematicaAEliminar = tematica
                                    showConfirmationDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA590)),
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.width(150.dp)
                            ) {
                                Text("Eliminar", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    // Mostrar el diálogo de confirmación
    if (showConfirmationDialog && tematicaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta temática?") },
            confirmButton = {
                Button(
                    onClick = {
                        tematicaAEliminar?.let { tematica ->
                            service.deleteTematica(tematica.Id)
                            listaTematicas = service.getAllTematicas()
                        }
                        showConfirmationDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmationDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
