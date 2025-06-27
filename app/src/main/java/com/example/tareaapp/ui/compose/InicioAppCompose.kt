package com.example.tareaapp.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tareaapp.R
import com.example.tareaapp.Service.Rutas



@Composable
fun MiApp(){
    // Implementacion de la Clase NavController
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.Home){
        composable(Rutas.Home){
            InicioAppComposable(navController)}
        composable(Rutas.CreateCheck){
            CreateCheckCRUD(navController)}
        composable(Rutas.ReadCheck){
            ReadCheckCRUD(navController)}
    }

}
@Composable
fun InicioAppComposableUI(onNavegar : (String) -> Unit = {} ){

    val image = painterResource(R.drawable.tareapng)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Titulo de la App
            Card(modifier = Modifier
                .padding(80.dp)
                .fillMaxWidth()){
                Text(
                    text = "Tarea App",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 50.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Imagen de la App
            Card(modifier = Modifier
                .padding(100.dp)
                .width(1000.dp)
                .fillMaxWidth()
                .height(300.dp)){

                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )

            }

            // Botones de Seleccion
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(onClick = {onNavegar(Rutas.CreateCheck)}) {
                    Text("Agregar Tarea")
                }

                Button(onClick =  {onNavegar(Rutas.ReadCheck)}) {
                    Text("Tareas Pendientes")
                }
            }
        }

    }

}

@Composable
fun InicioAppComposable(navController: NavHostController){
    InicioAppComposableUI(onNavegar = { ruta -> navController.navigate(ruta)
        })
}

@Preview
@Composable
fun InicioAppComposablePreview(){
    InicioAppComposableUI()
}
