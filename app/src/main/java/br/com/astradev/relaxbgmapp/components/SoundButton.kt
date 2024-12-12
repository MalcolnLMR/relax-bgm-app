package br.com.astradev.relaxbgmapp.components

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SoundButton(
    label: String,
    iconResId: Int,
    soundResId: Int,
    mediaPlayer: MediaPlayer,
    volume: Float
) {
    val context = LocalContext.current
    var estaTocando by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                if (estaTocando) {
                    mediaPlayer.pause()
                } else {
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(
                        context,
                        android.net.Uri.parse("android.resource://${context.packageName}/$soundResId")
                    )
                    mediaPlayer.prepare()
                    mediaPlayer.setVolume(volume, volume)
                    mediaPlayer.start()
                }
                estaTocando = !estaTocando
            }
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(15.dp)

    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = label,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(label, fontSize = 20.sp, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold )
    }
}