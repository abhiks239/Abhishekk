package com.jarvis.accountant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Message(val role: String, val text: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { JarvisApp() }
    }
}

@Composable
fun JarvisApp() {
    var input by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(
            Message("JARVIS",
                "Good morning. I am JARVIS Accountant. Ask me about Accounts, Excel, Tally, GST, TDS or RA Bill Audit.")
        ))
    }

    fun respond(q: String): String {
        return when {
            q.contains("gst", true) ->
                "GST module ready: GST treatment, ITC, RCM, e-invoice, e-way bill, HSN/SAC and reconciliation. The production engine will verify current official sources before live-law answers."
            q.contains("tds", true) ->
                "TDS module ready: applicability, provision/section, threshold, rate, deduction/payment timing and interest. I will use transaction date and current official sources."
            q.contains("excel", true) ->
                "Excel module ready: formulas, reconciliation, duplicates, MIS, pivots, VBA assistance and exception reports. Upload the workbook for analysis."
            q.contains("tally", true) ->
                "Tally module ready: vouchers, ledgers, GST/TDS, BRS, PDC, import/export, reconciliation and TDL workflows."
            q.contains("ra", true) || q.contains("audit", true) ->
                "RA Audit module ready: Agreement, clauses, BOQ, Change Orders, previous/current RA, quantity/rate/amount excess, EOT, compliance and audit-query generation."
            else ->
                "Understood. The production JARVIS engine will route this request to the appropriate accounting tool."
        }
    }

    Column(
        Modifier.fillMaxSize()
            .background(Color(0xFF07111F))
            .padding(16.dp)
    ) {
        Text("JARVIS", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("ACCOUNTANT EDITION • v0.1", color = Color(0xFF82B3CC), fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))

        LazyColumn(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (msg.role == "JARVIS")
                            Color(0xFF122335) else Color(0xFF193B45)
                    )
                ) {
                    Column(Modifier.padding(13.dp)) {
                        Text(msg.role, color = Color(0xFF63D7FF), fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text(msg.text, color = Color.White)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask JARVIS…") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (input.isNotBlank()) {
                    val q = input
                    messages = messages + Message("YOU", q) + Message("JARVIS", respond(q))
                    input = ""
                }
            }) { Text("SEND") }
        }
    }
}
