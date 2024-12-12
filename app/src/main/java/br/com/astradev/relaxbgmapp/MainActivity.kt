package br.com.astradev.relaxbgmapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.astradev.relaxbgmapp.components.SoundButton
import br.com.astradev.relaxbgmapp.ui.theme.RelaxbgmappTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RelaxbgmappTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    WhiteNoise()
                }
            }
        }
    }
}

@Composable
fun WhiteNoise() {
    val context = LocalContext.current
    var volume by remember { mutableStateOf(1f) } // Volume inicial
    var timerDuration by remember { mutableStateOf(60000L) } // Tempo inicial: 1 minuto
    val mediaPlayer = remember { MediaPlayer() }
    var remainingTime by remember { mutableStateOf(timerDuration / 1000) } // Tempo em segundos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // contagem do temporizador
        Text(
            text = "Tempo restante: $remainingTime segundos",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(150.dp))

        Text(
            text = "Selecione um som para dormir",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(){
            SoundButton("Chuva", R.drawable.night_cloud_wind_icon, R.raw.aaaaaaarain, mediaPlayer, volume)
            Spacer(modifier = Modifier.height(5.dp))
            SoundButton("DK :D", R.drawable.donkey_kong, R.raw.dk, mediaPlayer, volume)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(16.dp))
                    //.clip(RoundedCornerShape(16.dp))
                    .padding(5.dp)

            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp)


                ) {
                    // Controle de temporizador
                    Text("Definir temporizador (em segundos):", fontSize = 16.sp)
                    Slider(
                        value = timerDuration.toFloat(),
                        onValueChange = { timerDuration = it.toLong() },
                        valueRange = 10000f..1800000f,
                        steps = 50,
                        modifier = Modifier.padding(horizontal = 32.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.onPrimary,
                            activeTrackColor = MaterialTheme.colorScheme.onBackground,
                            inactiveTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                        )
                    )
                }
            }


        }

        Text("${timerDuration / 1000} segundos", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))




        Button(
            onClick = {
                object : CountDownTimer(timerDuration, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        remainingTime = millisUntilFinished / 1000 // Atualiza o tempo restante
                    }

                    override fun onFinish() {
                        remainingTime = 0 // Zera o tempo restante
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                        }
                    }
                }.start()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary // cor do botão
            )
        ) {
            Text(
                "Começar",
                fontSize = 25.sp
            )
        }
    }
}