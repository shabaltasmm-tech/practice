package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ColorSearchScreen()
            }
        }
    }
}

// Structure for storing colors by name
val colorPalette = mapOf(
    "red" to Color(0xFFFF0000),
    "orange" to Color(0xFFFFA500),
    "yellow" to Color(0xFFFFFF00),
    "green" to Color(0xFF00FF00),
    "blue" to Color(0xFF0000FF),
    "indigo" to Color(0xFF4B0082),
    "violet" to Color(0xFF8B00FF)
)

@Composable
fun ColorSearchScreen() {
    var inputColorName by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf<Color?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Поле для ввода названия цвета
        OutlinedTextField(
            value = inputColorName,
            onValueChange = { inputColorName = it },
            label = { Text("Введите название цвета") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Кнопка с изменяемым фоном
        Button(
            onClick = {
                val foundColor = colorPalette[inputColorName.lowercase()]
                if (foundColor != null) {
                    buttonColor = foundColor
                    Log.d("ColorSearch", "Цвет '$inputColorName' найден! Применяем к фону кнопки.")
                } else {
                    Log.d("ColorSearch", "Пользовательский цвет '$inputColorName' не найден")
                }
            },
            colors = if (buttonColor != null) {
                ButtonDefaults.buttonColors(containerColor = buttonColor!!)
            } else {
                ButtonDefaults.buttonColors()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поиск цвета")
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colorPalette.entries.toList()) { (name, color) ->
                ColorPaletteItem(colorName = name, color = color)
            }
        }
    }
}

@Composable
fun ColorPaletteItem(colorName: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = colorName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = if (color == Color(0xFF0000FF) || color == Color(0xFF4B0082) || color == Color(0xFF8B00FF)) Color.White else Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorSearchScreenPreview() {
    MyApplicationTheme {
        ColorSearchScreen()
    }
};
