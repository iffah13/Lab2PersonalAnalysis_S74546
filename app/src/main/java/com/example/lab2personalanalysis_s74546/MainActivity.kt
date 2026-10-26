
package com.example.lab2personalanalysis_s74546

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editNama = findViewById<EditText>(R.id.editNama)
        val editTarikh = findViewById<EditText>(R.id.editTarikh)
        val btnAnalisis = findViewById<Button>(R.id.btnAnalisis)

        val formatter = DateTimeFormatter
            .ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT)

        btnAnalisis.setOnClickListener {

            val nama = editNama.text.toString().trim()
            val tarikh = editTarikh.text.toString().trim()

            if (nama.isEmpty()) {
                editNama.error = "Sila masukkan nama"
                editNama.requestFocus()
                return@setOnClickListener
            }

            if (tarikh.isEmpty()) {
                editTarikh.error = "Sila masukkan tarikh lahir"
                editTarikh.requestFocus()
                return@setOnClickListener
            }

            try {
                val tarikhLahir = LocalDate.parse(tarikh, formatter)

                if (tarikhLahir.isAfter(LocalDate.now())) {
                    editTarikh.error = "Tarikh lahir tidak boleh pada masa depan"
                    return@setOnClickListener
                }

                val intent = Intent(
                    this,
                    ResultActivity::class.java
                )

                intent.putExtra("NAMA", nama)
                intent.putExtra("TARIKH", tarikh)

                startActivity(intent)

            } catch (e: DateTimeParseException) {
                Toast.makeText(
                    this,
                    "Format tarikh mesti YYYY-MM-DD",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
