package com.example.silab_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.silab_app.models.User
import com.example.silab_app.utils.UserManager

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNama: EditText
    private lateinit var txtNim: EditText
    private lateinit var txtJurusan: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userManager = UserManager(this)

        txtNama = findViewById(R.id.txtNama)
        txtNim = findViewById(R.id.txtNim)
        txtJurusan = findViewById(R.id.txtJurusan)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val nama = txtNama.text.toString().trim()
            val nim = txtNim.text.toString().trim()
            val jurusan = txtJurusan.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            // Validation
            if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty() ||
                email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if email already exists
            if (userManager.getUserByEmail(email) != null) {
                Toast.makeText(this, "Email sudah terdaftar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save user
            val user = User(nama, nim, jurusan, email, password)
            userManager.saveUser(user)

            Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show()

            // Navigate to login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }
}