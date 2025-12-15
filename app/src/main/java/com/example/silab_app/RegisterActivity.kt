package com.example.silab_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.silab_app.models.User
import com.example.silab_app.models.request.RegisterRequest
import com.example.silab_app.models.response.RegisterResponse
import com.example.silab_app.network.ApiClient
import com.example.silab_app.utils.UserManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNama: EditText
    private lateinit var txtNim: EditText
    private lateinit var txtJurusan: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
//    private lateinit var userManager: UserManager
    private val client = ApiClient.getInstance("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



//        userManager = UserManager(this)

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

            if(!email.endsWith("@ugm.ac.id") && !email.endsWith("@mail.ugm.ac.id")) {
                Toast.makeText(this, "Email yang digunakan harus email UGM!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if email already exists
//            if (userManager.getUserByEmail(email) != null) {
//                Toast.makeText(this, "Email sudah terdaftar!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Save user
//            val user = User(nama, nim, jurusan, email, password)
//            userManager.saveUser(user)
            registered(nama, nim, jurusan, email, password) {
                Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show()

                // Navigate to login
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun registered(nama: String, nim: String, jurusan: String, email: String, password: String, onSuccess: ()-> Unit) {
        val body = RegisterRequest( nama, nim, jurusan, email,  password)
        client.registerAccount(body).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                p0: Call<RegisterResponse>,
                p1: Response<RegisterResponse>
            ) {
                if (!p1.isSuccessful) {
                    Toast.makeText(this@RegisterActivity,
                        "Register gagal: HTTP ${p1.message()}",
                        Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(this@RegisterActivity, "Ini Body: ${p1.body()?.data} ", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            override fun onFailure(
                p0: Call<RegisterResponse>,
                p1: Throwable
            ) {
                Toast.makeText(this@RegisterActivity, "Gagal ${p1.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}