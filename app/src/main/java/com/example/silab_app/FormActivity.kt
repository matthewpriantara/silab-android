package com.example.silab_app

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)

        // --- Ambil data dari intent ---
        val name = intent.getStringExtra("name")
        val location = intent.getStringExtra("location")
        val status = intent.getStringExtra("status")
        val imageRes = intent.getIntExtra("image", 0)

        Log.d("FormActivity", "Name: $name")
        Log.d("FormActivity", "Location: $location")
        Log.d("FormActivity", "Status: $status")
        Log.d("FormActivity", "ImageRes: $imageRes")

        // --- Ambil semua input EditText ---
        val txtNama = findViewById<EditText>(R.id.txtNama)
        val txtSesi = findViewById<EditText>(R.id.txtSesi)
        val txtTanggal = findViewById<EditText>(R.id.txtTanggal)
        val txtMatkul = findViewById<EditText>(R.id.txtMatkul)
        val txtPj = findViewById<EditText>(R.id.txtPj)

        val btnCancel = findViewById<Button>(R.id.btnBatal)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // ===============================
        // DATE PICKER TANGGAL
        // ===============================
        txtTanggal.setOnClickListener {
            showDatePicker(txtTanggal)
        }

        // ===============================
        // BUTTON CANCEL
        // ===============================
        btnCancel.setOnClickListener {
            finish()
        }

        // ===============================
        // BUTTON SUBMIT
        // ===============================
        btnSubmit.setOnClickListener {
            val nama = txtNama.text.toString().trim()
            val sesi = txtSesi.text.toString().trim()
            val tanggal = txtTanggal.text.toString().trim()
            val matkul = txtMatkul.text.toString().trim()
            val pj = txtPj.text.toString().trim()

            // Validasi field
            if (nama.isEmpty() || sesi.isEmpty() || tanggal.isEmpty() ||
                matkul.isEmpty() || pj.isEmpty()
            ) {
                Toast.makeText(this, "Semua form harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Log data yang disubmit
            Log.d("FormActivity", "=== DATA SUBMIT ===")
            Log.d("FormActivity", "Nama: $nama")
            Log.d("FormActivity", "Sesi: $sesi")
            Log.d("FormActivity", "Tanggal: $tanggal")
            Log.d("FormActivity", "Mata Kuliah: $matkul")
            Log.d("FormActivity", "Penanggung Jawab: $pj")
            Log.d("FormActivity", "Ruangan: $name")
            Log.d("FormActivity", "Lokasi: $location")

            Toast.makeText(this, "Submit berhasil!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // ===============================
    // FUNCTION DATE PICKER
    // ===============================
    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format tanggal dd/MM/yyyy
                val formattedDate = String.format(
                    "%02d/%02d/%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
                )
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        // Optional: Set minimum date (today)
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        datePickerDialog.show()
    }
}