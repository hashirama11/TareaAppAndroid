package com.example.tareaapp.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tareaapp.Service.leerTareas
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import androidx.navigation.Navigator


@Composable
fun ReadCheckCRUDUI(modifier: Modifier = Modifier, onNavegar : (String) -> Unit = {}){

    // Llamar la funcion que obtienen las tareas creadas en ejecucion
    val tareasexistencias : List<String> = leerTareas()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (tareasexistencias.isEmpty()){
                // Mostrar un mensaje si no hay tareas
                Text(
                    text = "No hay tareas pendientes.",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp
                )
            }else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(tareasexistencias){ tarea : String ->
                        TareaItem(tarea = tarea)
                    }
                }
            }
        }
    }
}

// Composable para representar a cada tarea
@Composable
fun TareaItem(tarea: String, modifier: Modifier = Modifier) {
    Text(
        text = tarea,
        fontSize = 20.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    // Podrías añadir más elementos aquí, como un Divider, un Icon, etc.

}


// Compose de Ejecucion
@Composable
fun ReadCheckCRUD(navController: NavController) {
    ReadCheckCRUDUI(
        modifier = Modifier,
        onNavegar = { route -> navController.navigate(route) }
    )
}

// Previcualizacion
@Preview(showBackground = true)
@Composable
fun ReadCheckCRUDPreview() {
    ReadCheckCRUDUI()
}

