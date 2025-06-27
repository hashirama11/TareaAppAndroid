package com.example.tareaapp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tareaapp.Service.PersistenciaDataService
import com.example.tareaapp.Service.crearTarea

@Composable
fun CreateCheckCRUDUI(onNavigate : (String) -> Unit = {}){

    // Variables de Estado
    var textoTarea by remember { mutableStateOf("") }

    // Este es un formulario Sencillo para crear una nueva Tarea

    // Fondo de la app
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier
                    .padding(80.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Crear una nueva tarea",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            OutlinedTextField(
                value = textoTarea,
                onValueChange = { newText: String -> textoTarea = newText },
                label = { Text("Tarea") },
                modifier = Modifier.padding(16.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (textoTarea.isNotBlank()) {
                        val nuevoId = System.currentTimeMillis()
                        val nuevaTarea = PersistenciaDataService(nuevoId, textoTarea)
                        crearTarea(nuevaTarea)
                        textoTarea = ""
                    }else{
                        textoTarea = "Ingrese una tarea"
                    }
                },
            ) {
                Text("Crear")
            }
        }

    }

}

@Composable
fun CreateCheckCRUD(navController: NavController){
    CreateCheckCRUDUI(
        onNavigate = { route ->
            navController.navigate(route)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CreateCheckCRUDPreview(){
    CreateCheckCRUDUI()
}