package edu.villablanca.invitacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.villablanca.invitacion.ui.theme.DiceRollerTheme

class InvitacionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Invitacion("Feliz Cumpleaños","Droid")
                }
            }
        }
    }
}

@Composable
fun Invitacion(mensaje: String, de: String, modifier: Modifier = Modifier) {

    Column {
        Text(
            text = mensaje,
            modifier = modifier,
            fontSize = 100.sp,
            lineHeight = 116.sp

        )
        Text(
            text= de,
            fontSize = 36.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
fun InvitacionPreview() {
    DiceRollerTheme {
        Invitacion("Feliz Cumpleaños","Droid")
    }
}