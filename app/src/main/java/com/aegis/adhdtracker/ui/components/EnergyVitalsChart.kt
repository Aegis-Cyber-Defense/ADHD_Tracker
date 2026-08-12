package com.aegis.adhdtracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.aegis.adhdtracker.data.local.DailyLogEntity

@Composable
fun EnergyVitalsChart(
    logs: List<DailyLogEntity>,
    modifier: Modifier = Modifier
) {
    if (logs.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Log data for 2+ days to render your 7-day energy trend.")
        }
        return
    }

    val sortedLogs = logs.sortedBy { it.timestamp }.takeLast(7)
    val purpleColor = MaterialTheme.colorScheme.primary
    val tealColor = MaterialTheme.colorScheme.tertiary

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp).background(purpleColor, RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Energy (1-10)", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp).background(tealColor, RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Sleep Hours (0-12h)", style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            val width = size.width
            val height = size.height
            val spacing = width / (maxOf(sortedLogs.size - 1, 1))

            val energyPath = Path()
            val sleepPath = Path()

            sortedLogs.forEachIndexed { index, log ->
                val x = index * spacing
                
                // Map Energy (1-10 scale) to Y coordinate
                val energyY = height - ((log.energyLevel.coerceIn(1, 10) / 10f) * height)
                
                // Map Sleep Hours (0-12 hr scale, default 7h if null) to Y coordinate
                val sleepVal = 7f
                val sleepY = height - ((sleepVal.coerceIn(0f, 12f) / 12f) * height)

                if (index == 0) {
                    energyPath.moveTo(x, energyY)
                    sleepPath.moveTo(x, sleepY)
                } else {
                    energyPath.lineTo(x, energyY)
                    sleepPath.lineTo(x, sleepY)
                }

                // Draw data points
                drawCircle(color = purpleColor, radius = 5.dp.toPx(), center = Offset(x, energyY))
                drawCircle(color = tealColor, radius = 5.dp.toPx(), center = Offset(x, sleepY))
            }

            drawPath(path = energyPath, color = purpleColor, style = Stroke(width = 3.dp.toPx()))
            drawPath(path = sleepPath, color = tealColor, style = Stroke(width = 2.dp.toPx()))
        }
    }
}
