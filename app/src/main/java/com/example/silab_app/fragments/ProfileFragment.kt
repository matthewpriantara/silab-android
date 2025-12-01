package com.example.silab_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.silab_app.LoginActivity
import com.example.silab_app.R
import com.example.silab_app.utils.SessionManager

class ProfileFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var tvNama: TextView
    private lateinit var tvNim: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvProdi: TextView
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sessionManager = SessionManager(requireContext())

        tvNama = view.findViewById(R.id.tvNamaValue)
        tvNim = view.findViewById(R.id.tvNimValue)
        tvEmail = view.findViewById(R.id.tvEmailValue)
        tvProdi = view.findViewById(R.id.tvProdiValue)
        btnLogout = view.findViewById(R.id.btnLogout)

        // Load data from session
        tvNama.text = sessionManager.getNama()
        tvNim.text = sessionManager.getNim()
        tvEmail.text = sessionManager.getEmail()
        tvProdi.text = sessionManager.getProdi()

        btnLogout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}