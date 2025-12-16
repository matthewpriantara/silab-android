package com.example.silab_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.silab_app.models.request.LoginRequest
import com.example.silab_app.models.response.LoginResponse
import com.example.silab_app.models.response.RegisterResponse
import com.example.silab_app.network.ApiClient
import com.example.silab_app.utils.SessionManager
import com.example.silab_app.utils.UserManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var sessionManager: SessionManager
//    private lateinit var userManager: UserManager

    private val client = ApiClient.getInstance("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
//        userManager = UserManager(this)

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

            if (!email.endsWith("@mail.ugm.ac.id") && !email.endsWith("@ugm.ac.id")) {
                Toast.makeText(this, "Email yang digunakan harus email UGM", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate with UserManager
            loggedIn(email, password) {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            }
//            val user = userManager.getUserByEmail(email)
//            if (user != null && user.password == password) {
//                // Save session
//                sessionManager.saveLoginSession(
//                    nama = user.nama,
//                    nim = user.nim,
//                    email = user.email,
//                    prodi = user.jurusan
//                )
//
//                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
//                navigateToMain()
//            } else {
//                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
//            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun loggedIn(email: String, password: String, onSuccess: () -> Unit){
        val body = LoginRequest(email, password)
        client.loginAccount(body).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                p0: Call<LoginResponse>,
                p1: Response<LoginResponse>
            ) {
                if (!p1.isSuccessful) {
                    Toast.makeText(this@LoginActivity,
                        "Login gagal: HTTP ${p1.message()}",
                        Toast.LENGTH_SHORT).show()
                    return
                }

                Toast.makeText(this@LoginActivity, "Ini Tokennya: ${p1.body()?.token} ", Toast.LENGTH_SHORT).show()
                val token = p1.body()!!.token
                val user = p1.body()!!.data
                sessionManager.saveLoginSession(user.nama, user.nim , user.email, user.prodi, token )
                onSuccess()
            }
            override fun onFailure(
                p0: Call<LoginResponse>,
                p1: Throwable
            ) {
                Log.d("ERROR", p1.message?: "Waduh gatau dh")
                Toast.makeText(this@LoginActivity, "Gagal ${p1.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}