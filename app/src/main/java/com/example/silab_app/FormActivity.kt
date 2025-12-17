package com.example.silab_app

import InventoryAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.silab_app.models.CreateReserves
import com.example.silab_app.models.ReserveInformation
import com.example.silab_app.models.Reserves
import com.example.silab_app.models.Session
import com.example.silab_app.models.Subject
import com.example.silab_app.network.ApiClient
import com.example.silab_app.network.ApiService
import com.example.silab_app.repository.ReservesModel
import com.example.silab_app.repository.ReservesRepository
import com.example.silab_app.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class FormActivity : AppCompatActivity() {
    private val dummySubject: List<Subject> = listOf(Subject(1, "PPK"), Subject(2, "PPW"))
    private val dummySession: List<Session> = listOf(Session(1, "09.00"))

    private lateinit var viewModel: ReservesModel
    private lateinit var sessionManager: SessionManager
    private lateinit var client: ApiService
    private lateinit var reserves: ReserveInformation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)

        // --- Ambil data dari intent ---
        val name = intent.getStringExtra("name")
        val location = intent.getStringExtra("location")
        val status = intent.getStringExtra("status")
        val imageRes = intent.getIntExtra("image", 0)
        val id = intent.getIntExtra("id", -1)

        if (id == -1) {
            finish()
        }

        sessionManager = SessionManager(this@FormActivity)
        client = ApiClient.getInstance(sessionManager.getToken())
        val repository = ReservesRepository(client)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReservesModel(repository) as T
            }
        })[ReservesModel::class.java]

        viewModel.getDataReserves(id)

        val txtNama = findViewById<EditText>(R.id.txtNama)
        txtNama.setText(sessionManager.getNama())
        val spinnerSesi = findViewById<Spinner>(R.id.spinnerSesi)
        val txtTanggal = findViewById<EditText>(R.id.txtTanggal)
        val spinnerSubject = findViewById<Spinner>(R.id.spinnerSubject)
        val txtPj = findViewById<EditText>(R.id.txtPj)

        viewModel.reserves.observe(this@FormActivity) { data ->
            reserves = data
            val spinnerSesiAdapter = ArrayAdapter(this@FormActivity, android.R.layout.simple_spinner_item,reserves.session)
            val spinnerSubjectAdapter = ArrayAdapter(this@FormActivity, android.R.layout.simple_spinner_item, reserves.subject)

            spinnerSesiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSubjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerSesi.adapter = spinnerSesiAdapter
            spinnerSubject.adapter = spinnerSubjectAdapter
        }

        viewModel.isSuccessfull.observe(this@FormActivity) { data ->
            Toast.makeText(this, "Submit berhasil!", Toast.LENGTH_SHORT).show()
            finish()
        }

        Toast.makeText(this@FormActivity, "Id item yg diklik ${id}", Toast.LENGTH_SHORT).show()

        Log.d("FormActivity", "Name: $name")
        Log.d("FormActivity", "Location: $location")
        Log.d("FormActivity", "Status: $status")
        Log.d("FormActivity", "ImageRes: $imageRes")



        val btnCancel = findViewById<Button>(R.id.btnBatal)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)





        txtTanggal.setOnClickListener {
            showDatePicker(txtTanggal)
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {
            val session = spinnerSesi.selectedItem as Session
            Log.d("ID", session.session_id.toString())
            val subject = spinnerSubject.selectedItem as Subject
            Log.d("ID", subject.subject_id.toString())
            val nama = txtNama.text.toString().trim()
            val sesi = session.session_id.toString()
            val tanggal = txtTanggal.text.toString().trim()
            val matkul = subject.subject_id.toString()
            val pj = txtPj.text.toString().trim()


            if (nama.isEmpty() || tanggal.isEmpty() || sesi.isEmpty() ||
                matkul.isEmpty() || pj.isEmpty()
            ) {
                Toast.makeText(this, "Semua form harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            val tanggalIso = try {
                uiDateToIsoUtc(tanggal)
            } catch (e: Exception) {
                Toast.makeText(this, "Format tanggal tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("FormActivity", "=== DATA SUBMIT ===")
            Log.d("FormActivity", "Nama: $nama")
            Log.d("FormActivity", "Sesi: $sesi")
            Log.d("FormActivity", "Tanggal: $tanggal")
            Log.d("FormActivity", "Mata Kuliah: $matkul")
            Log.d("FormActivity", "Penanggung Jawab: $pj")
            Log.d("FormActivity", "Ruangan: $name")
            Log.d("FormActivity", "Lokasi: $location")

            viewModel.postReserves(listOf(CreateReserves(id, pj, subject.subject_id, tanggalIso, session.session_id)))

            viewModel.isSuccessfull.observe(this@FormActivity) { data ->
                if (data) {
                    Toast.makeText(this, "Submit berhasil!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val tomorrow = calendar.clone() as Calendar
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)

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
        datePickerDialog.datePicker.minDate = tomorrow.timeInMillis

        datePickerDialog.show()
    }

    private fun uiDateToIsoUtc(uiDate: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
        inputFormat.isLenient = false

        val date = inputFormat.parse(uiDate)
            ?: throw IllegalArgumentException("Format tanggal tidak valid")

        val outputFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")

        return outputFormat.format(date)
    }
}