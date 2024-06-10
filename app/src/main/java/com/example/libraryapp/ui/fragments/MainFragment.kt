import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.R

@Preview
@Composable
fun MainFragment() {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Занимает максимально возможное пространство
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Занимает оставшееся пространство в столбце
                contentAlignment = Alignment.Center // Центрирование по вертикали и горизонтали
            ) {
                Text("Головна сторінка", fontSize = 40.sp, fontFamily = font)
            }
            Image(modifier = Modifier.fillMaxWidth(), painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Spacer(modifier = Modifier.weight(1f)) // Пустое пространство, чтобы кнопка была внизу
            Button(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), onClick = { /* Действие при нажатии кнопки */ }) {
                Text("Пошук книг", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier.fillMaxWidth().padding(bottom=10.dp), onClick = { /* Действие при нажатии кнопки */ }) {
                Text("Улюблені книги", fontFamily = font, fontSize = 18.sp)
            }
            Button(modifier = Modifier.fillMaxWidth().padding(bottom=40.dp), onClick = { /* Действие при нажатии кнопки */ }) {
                Text("Налаштування", fontFamily = font, fontSize = 18.sp)
            }
        }
    }
}

val font = FontFamily(
    Font(R.font.freakyfont)
)







