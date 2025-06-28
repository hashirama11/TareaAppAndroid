package com.example.tareaapp.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.example.tareaapp.R
import com.example.tareaapp.Service.Rutas
import com.example.tareaapp.Service.cambiarEstadoPorContenido
import com.example.tareaapp.Service.deleteTareaPorContenido
import com.example.tareaapp.Service.leerInfoCompletaDeTodasLasTareas

// Compose para la vista de lectura

@Composable
fun ReadCheckCRUDUI(
    modifier: Modifier = Modifier,
    onNavegar: (String) -> Unit = {}
) {
    // Ahora obtenemos una lista de Pares (contenido de la tarea, estado)
    val tareasExistencias: List<Pair<String, Boolean>> = leerInfoCompletaDeTodasLasTareas()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (tareasExistencias.isEmpty()) {
                    EmptyStateCard()
                } else {
                    ExpandableTaskList(
                        // Pasamos la lista de Pares
                        listaDeTareasConEstado = tareasExistencias,
                        expanded = true,
                        onAccionDeTareaConfirmada = { accion, tareaAfectadaContenido ->
                            // 'tareaAfectadaContenido' aquí será el String de la tarea
                            println("Acción confirmada: $accion para la tarea: $tareaAfectadaContenido")
                            when (accion) {
                                TareaAction.Borrar -> {
                                    // Usas el contenido para llamar a deleteTareaPorContenido
                                    deleteTareaPorContenido(tareaAfectadaContenido)
                                    // TODO: Necesitas una forma de que la UI se actualice después de esto.
                                    // Idealmente, 'tareasExistencias' sería un estado que se recompone.
                                }
                                TareaAction.Modificar -> {
                                    // Navegar o mostrar diálogo para modificar tareaAfectadaContenido
                                }
                                TareaAction.Listo -> {
                                    // Usas el contenido para llamar a cambiarEstadoPorContenido
                                    // Suponiendo que 'Listo' significa marcar como true
                                    cambiarEstadoPorContenido(tareaAfectadaContenido, true)
                                    // TODO: Actualizar UI
                                }
                                TareaAction.Ninguna -> { /* No debería pasar */ }
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                Button(onClick = { onNavegar(Rutas.CreateCheck) }) {
                    Text("Agregar otra tarea")
                }
                Button(onClick = { onNavegar(Rutas.Home) }) {
                    Text("Regresar al inicio")
                }
            }
        }
    }
}
// Enum para los tipos de acciones

enum class TareaAction {
    Borrar, Modificar, Listo, Ninguna
}

// Compose para mostrar una tarea
@Composable
fun TareaItem(
    // Acepta el contenido y el estado por separado
    contenidoTarea: String,
    estaCompletada: Boolean,
    // La lambda de confirmación solo devuelve la acción.
    // El 'contenidoTarea' ya lo conoce ExpandableTaskList para propagarlo.
    onAccionConfirmada: (accion: TareaAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }
    var accionParaConfirmar by remember { mutableStateOf(TareaAction.Ninguna) }

    val onAccionSeleccionadaDelMenu: (TareaAction) -> Unit = { action ->
        accionParaConfirmar = action
        mostrarDialogoConfirmacion = true
        menuExpanded = false
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp)
                .clickable { menuExpanded = true }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_task),
                    contentDescription = "Ícono de tarea",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = contenidoTarea, // Mostramos el contenido
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        // Ejemplo: Tachar el texto si está completada
                        textDecoration = if (estaCompletada) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Borrar Tarea") },
                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = "Borrar") },
                onClick = { onAccionSeleccionadaDelMenu(TareaAction.Borrar) }
            )
            DropdownMenuItem(
                text = { Text("Modificar Tarea") },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Modificar") },
                onClick = { onAccionSeleccionadaDelMenu(TareaAction.Modificar) }
            )
            // Cambia "Marcar como Lista" a "Marcar como Pendiente" si ya está lista
            val textoOpcionListo = if (estaCompletada) "Marcar como Pendiente" else "Marcar como Lista"
            DropdownMenuItem(
                text = { Text(textoOpcionListo) },
                leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = textoOpcionListo) },
                onClick = { onAccionSeleccionadaDelMenu(TareaAction.Listo) } // 'Listo' puede significar 'toggle'
            )
        }
    }

    if (mostrarDialogoConfirmacion) {
        ConfirmacionDialog(
            accion = accionParaConfirmar,
            tareaNombre = contenidoTarea, // El diálogo sigue usando el contenido para el mensaje
            onConfirm = {
                onAccionConfirmada(accionParaConfirmar) // Llama a la lógica con la acción
                mostrarDialogoConfirmacion = false
                accionParaConfirmar = TareaAction.Ninguna
            },
            onDismiss = {
                mostrarDialogoConfirmacion = false
                accionParaConfirmar = TareaAction.Ninguna
            }
        )
    }
}


@Composable
fun ConfirmacionDialog(
    accion: TareaAction,
    tareaNombre: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val tituloDialogo = when (accion) {
        TareaAction.Borrar -> "Confirmar Borrado"
        TareaAction.Modificar -> "Confirmar Modificación" // O podría ser "Iniciar Modificación" si navega
        TareaAction.Listo -> "Confirmar Estado"
        TareaAction.Ninguna -> "" // No debería ocurrir
    }
    val textoDialogo = when (accion) {
        TareaAction.Borrar -> "¿Estás seguro de que quieres borrar la tarea \"$tareaNombre\"?"
        TareaAction.Modificar -> "¿Quieres modificar la tarea \"$tareaNombre\"?" // Ajusta según la acción
        TareaAction.Listo -> "¿Estás seguro de que quieres marcar la tarea \"$tareaNombre\" como lista?"
        TareaAction.Ninguna -> ""
    }

    if (accion != TareaAction.Ninguna) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(tituloDialogo) },
            text = { Text(textoDialogo) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// Compose para mostrar una lista de tareas expandible
@Composable
fun ExpandableTaskList(
    // Acepta la lista de Pares
    listaDeTareasConEstado: List<Pair<String, Boolean>>,
    expanded: Boolean,
    onAccionDeTareaConfirmada: (accion: TareaAction, tareaAfectadaContenido: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (expanded) {
            listaDeTareasConEstado.forEach { tareaPar ->
                // tareaPar es un Pair<String, Boolean>
                TareaItem(
                    // Pasamos el contenido (String) a TareaItem
                    contenidoTarea = tareaPar.first,
                    // Pasamos el estado (Boolean) a TareaItem
                    estaCompletada = tareaPar.second,
                    // La lambda de confirmación sigue esperando el contenido (String) de la tarea
                    onAccionConfirmada = { accion -> // TareaItem ahora solo devuelve la acción
                        // Propagamos la acción y el contenido de la tarea actual
                        onAccionDeTareaConfirmada(accion, tareaPar.first)
                    },
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
// Compose para mostrar un mensaje de estado vacío si no hay tareas
@Composable
fun EmptyStateCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "No hay tareas pendientes.",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}


// Compose de ejecucion
@Composable
fun ReadCheckCRUD(navController: NavController) {
    ReadCheckCRUDUI(
        modifier = Modifier,
        onNavegar = { route -> navController.navigate(route) }
    )
}

// Compose de vista previa
@Preview(showBackground = true)
@Composable
fun ReadCheckCRUDPreview() {
    ReadCheckCRUDUI()
}

