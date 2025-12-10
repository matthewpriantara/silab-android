package com.example.silab_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.silab_app.utils.SessionManager
import com.example.silab_app.utils.UserManager

class LoginActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        userManager = UserManager(this)

        // Check if already logged in
        if (sessionManager.isLoggedIn()) {
            navigateToMain()
            return
        }

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate with UserManager
            val user = userManager.getUserByEmail(email)
            if (user != null && user.password == password) {
                // Save session
                sessionManager.saveLoginSession(
                    nama = user.nama,
                    nim = user.nim,
                    email = user.email,
                    prodi = user.jurusan
                )

                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}