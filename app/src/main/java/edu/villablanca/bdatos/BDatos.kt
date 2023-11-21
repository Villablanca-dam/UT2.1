package edu.villablanca.bdatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.villablanca.bdatos.ui.theme.DiceRollerTheme

class BDatos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bd = BDSim()
        bd.open()
        setContent {
            DiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(bd)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCard(bd: BDSim,
              siguienteMenu: (String)-> Unit,
              estado: (String) -> Unit){

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf(0) }

 //   var usuario by remember { mutableStateOf(Usuario()) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = nombre,
            onValueChange = { nuevoNombre -> nombre = nuevoNombre },
            label = { Text("nombre") }
        )
        TextField(
            value = edad.toString(),
            onValueChange = { edad = it.toIntOrNull() ?: 0 },
            label = { Text("edad") }
        )

        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("correo") }
        )
        Button(onClick = {
            bd.create(Usuario(nombre,edad,correo))
            siguienteMenu("I")
            estado("Creado usuario: ${nombre}")
        }) {
            Text(text = "Añadir")
        }
    }
}
/*
    Version lectura con get, menos recomendable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LecturaCard(
    bd: BDSim,
    siguienteMenu: (String) -> Unit,
    estado: (String) -> Unit
) {
    var id by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("Demo CRUD con FireBase Realtime DB") }
        )

        Button(onClick = {
            val res= bd.read(id)
            estado("Respuesta leer = $res")
            siguienteMenu("I")

        }) {
            Text(text = "Leer")
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrarCard(
    bd: BDSim,
    siguienteMenu: (String) -> Unit,
    estado: (String) -> Unit
) {
    var id by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("Nombre para borrar") }
        )


        Button(onClick = {
            val res= bd.delete( id)

            estado("Respuesta leer = $res")
            siguienteMenu("I")

        }) {
            Text(text = "Borrar")
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarCard(
    bd: BDSim,
    siguienteMenu: (String) -> Unit,
    estado: (String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf(0) }

    var encontrado by remember { mutableStateOf(false) }

    //   var usuario by remember { mutableStateOf(Usuario()) }



    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ){
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = nombre,
                enabled = true,
                onValueChange = { nuevoNombre -> nombre = nuevoNombre },
                label = { Text("nombre") }
            )
            Button(
                enabled = !encontrado,
                onClick = {

                    val  us = bd.read(nombre)
                    if(us != null) {
                        encontrado = true
                        edad = us.edad
                        correo = us.correo
                    }else {
                        nombre=""
                    }

                }) {
                Text(text = "Buscar")
            }
        }


        TextField(
            value = edad.toString(),
            enabled = encontrado,
            onValueChange = { edad = it.toIntOrNull() ?: 0 },
            label = { Text("edad") }
        )

        TextField(
            value = correo,
            enabled = encontrado,
            onValueChange = { correo = it },
            label = { Text("correo") }
        )
        Row {
            Button(
                enabled = encontrado,
                onClick = {
                bd.update(nombre,Usuario(nombre,edad,correo))
                siguienteMenu("I")
                estado("Creado usuario: ${nombre}")
            }) {
                Text(text = "Actualizar")
            }
            Button(onClick = {
                siguienteMenu("I")
            }) {
                Text(text = "Volver")
            }
        }

    }

}

@Composable
fun menu(selec: (String)-> Unit) {
    Column {
        Button(onClick = { selec("C") }) {
            Text(text = "Crear Usario")
        }
        Button(onClick = { selec("R") }) {
            Text(text = "(R) Leer")
        }
        Button(onClick = { selec("U") }) {
            Text(text = "(U) Actualizar")
        }
        Button(onClick = { selec("D") }) {
            Text(text = "(D) Borrar")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal( bd: BDSim , modifier: Modifier = Modifier) {

    var text by remember { mutableStateOf<String>("") }
    var operacion by remember { mutableStateOf("I") }
    var estadoUltimo by remember {  mutableStateOf("ultimo estado") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Demo CRUD con FireBase Realtime DB") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // menu y operaciones
        when(operacion){
            "I" -> menu(selec = {nueva: String -> operacion = nueva})

            "C" -> {
                CrearCard(bd = bd,{ nueva: String -> operacion = nueva} ){ estadoUltimo = it}

            }
            "R"  ->{
                LecturaCard(bd , { nueva: String -> operacion = nueva} ){ estadoUltimo = it}

            }
            "U" -> {
                ActualizarCard(bd , { nueva: String -> operacion = nueva} ){ estadoUltimo = it}
            }
            "D" -> {
                BorrarCard(bd , { nueva: String -> operacion = nueva} ){ estadoUltimo = it}
            }
            else -> println("Error")

        }

        Text(text = estadoUltimo)


    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiceRollerTheme {
       // PantallaPrincipal()
    }
}