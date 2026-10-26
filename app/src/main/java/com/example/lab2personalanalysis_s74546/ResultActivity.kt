
package com.example.lab2personalanalysis_s74546

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import java.time.LocalDate
import java.time.Period
import java.time.format.TextStyle
import java.util.Locale

class ResultActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val txtNama = findViewById<TextView>(R.id.txtNama)
        val txtTarikh = findViewById<TextView>(R.id.txtTarikh)

        val nama = intent.getStringExtra("NAMA") ?: ""
        val tarikh = intent.getStringExtra("TARIKH") ?: ""

        // PART A: NAME ANALYSIS

        val namaTanpaRuang = nama.filter { !it.isWhitespace() }

        val jumlahAbjad = namaTanpaRuang.length

        val jumlahVokal = namaTanpaRuang
            .lowercase()
            .count { it in "aeiou" }

        val hurufBerulang = namaTanpaRuang
            .lowercase()
            .filter { it.isLetter() }
            .groupingBy { it }
            .eachCount()
            .filterValues { it > 1 }

        val senaraiBerulang = if (hurufBerulang.isEmpty()) {
            "Tiada"
        } else {
            hurufBerulang.entries.joinToString(", ") {
                "${it.key}: ${it.value}"
            }
        }

        txtNama.text = """
            Nama: $nama
            Jumlah abjad: $jumlahAbjad
            Bilangan vokal: $jumlahVokal
            Abjad berulang: $senaraiBerulang
        """.trimIndent()

        // PART B: DATE OF BIRTH ANALYSIS

        val tarikhLahir = LocalDate.parse(tarikh)
        val hariIni = LocalDate.now()

        val umur = Period.between(
            tarikhLahir,
            hariIni
        ).years

        val hariLahir = tarikhLahir.dayOfWeek
            .getDisplayName(
                TextStyle.FULL,
                Locale.forLanguageTag("ms-MY")
            )

        txtTarikh.text = """
            Umur anda: $umur tahun
            Dilahirkan pada: $hariLahir
        """.trimIndent()
    }
}
