package com.example.lab05_danp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import kotlin.math.min
import com.example.lab05_danp.R

@Composable
fun CombinedChartsExample() {
    val barChartData = listOf(100f, 150f, 75f, 200f, 120f)
    val barChartColors = listOf(
        Color(0xFF4CAF50),   // Verde
        Color(0xFF8BC34A),   // Verde más claro
        Color(0xFFCDDC39),   // Verde lima
        Color(0xFF009688),   // Verde azulado
        Color(0xFF4DB6AC)    // Verde turquesa
    )
    val barChartLabels = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

    val pieChartData = listOf(
        PieChartData(100f, Color.Blue, "Lunes"),
        PieChartData(150f, Color.Green, "Martes"),
        PieChartData(75f, Color.Yellow, "Miércoles"),
        PieChartData(200f, Color.Red, "Jueves"),
        PieChartData(120f, Color.Magenta, "Viernes")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Reporte Semanal",
            fontSize = 54.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el gráfico circular (pie chart) en la parte superior
        PieChartExample(data = pieChartData)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el gráfico de barras en la parte inferior
        BarChart(
            data = barChartData,
            barColors = barChartColors,
            labels = barChartLabels,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Ajusta la altura según tus necesidades
        )
    }
}

@Composable
fun PieChartExample(data: List<PieChartData>) {
    val totalValue = data.sumByDouble { it.value.toDouble() }.toFloat()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Ajusta la altura según tus necesidades
            .padding(16.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = min(size.width, size.height) / 2
        var currentAngle = 0f

        for (slice in data) {
            val sweepAngle = (slice.value / totalValue) * 360
            drawArc(
                color = slice.color,
                startAngle = currentAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2)
            )
            currentAngle += sweepAngle
        }
    }
}

data class PieChartData(val value: Float, val color: Color, val label: String)

@Composable
fun BarChart(
    data: List<Float>,
    barColors: List<Color>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    maxBarHeight: Float = 250f // Ajusta este valor para controlar la longitud de las barras
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val barCount = data.size
        val barWidth = size.width / (barCount * 2)

        for ((index, value) in data.withIndex()) {
            val barHeight = value / maxBarHeight * size.height // Usar maxBarHeight para ajustar la altura
            val startX = (index * 2 + 1) * barWidth
            drawBar(
                startX = startX,
                startY = size.height - barHeight,
                width = barWidth,
                height = barHeight,
                color = barColors.getOrNull(index) ?: Color.Gray
            )
            drawLabels(
                label = labels[index],
                x = startX + barWidth / 2,
                y = size.height + 16.dp.toPx() // Ajustar la posición de la etiqueta
            )
        }
    }
}



private fun DrawScope.drawCircularIndicator(offset: Float, size: Float) {
    val center = Offset(offset + size / 2, size / 2)
    drawArc(
        color = Color.Gray, // Puedes cambiar el color del ProgressBar
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = Offset(offset, 0f),
        size = Size(size, size)
    )
}

private fun DrawScope.drawLabels(label: String, x: Float, y: Float) {
    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            color = Color.Black.toArgb()
            textSize = 12.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.nativeCanvas.drawText(label, x - 16.dp.toPx(), y, paint)
    }
}

private fun DrawScope.drawBar(
    startX: Float,
    startY: Float,
    width: Float,
    height: Float,
    color: Color
) {
    val lowerColor = color.copy(alpha = 0.5f) // Ajustar la opacidad del color más bajo

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(color, lowerColor), // Usar el color original y el color más bajo en el degradado
            startY = startY,
            endY = startY + height
        ),
        topLeft = Offset(startX, startY),
        size = Size(width, height)
    )
}

