package com.sandbox.ftptransfer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sandbox.ftptransfer.model.SenderConfig
import com.google.gson.Gson

class SenderConfigActivity : AppCompatActivity() {

    private lateinit var etMonitorFolder: EditText
    private lateinit var etTargetPort: EditText
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender_config)

        initViews()
        setupClickListeners()
        loadExistingConfig()
    }

    private fun initViews() {
        etMonitorFolder = findViewById(R.id.etMonitorFolder)
        etTargetPort = findViewById(R.id.etTargetPort)
        btnSave = findViewById(R.id.btnSaveSender)
        btnBack = findViewById(R.id.btnBackSender)
    }

    private fun setupClickListeners() {
        btnSave.setOnClickListener {
            saveConfiguration()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadExistingConfig() {
        // Load existing configuration if any
        val sharedPref = getSharedPreferences("sender_config", MODE_PRIVATE)
        val configJson = sharedPref.getString("sender_config", null)
        
        configJson?.let {
            val config = Gson().fromJson(it, SenderConfig::class.java)
            etMonitorFolder.setText(config.monitorFolder)
            etTargetPort.setText(config.targetPort.toString())
        }
    }

    private fun saveConfiguration() {
        val monitorFolder = etMonitorFolder.text.toString().trim()
        val targetPort = etTargetPort.text.toString().trim().toIntOrNull()

        if (monitorFolder.isEmpty()) {
            Toast.makeText(this, "Please enter monitor folder", Toast.LENGTH_SHORT).show()
            return
        }

        if (targetPort == null || targetPort !in 5151..5160) {
            Toast.makeText(this, "Please enter valid port (5151-5160)", Toast.LENGTH_SHORT).show()
            return
        }

        val config = SenderConfig(
            monitorFolder = monitorFolder,
            targetPort = targetPort
        )

        val sharedPref = getSharedPreferences("sender_config", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("sender_config", Gson().toJson(config))
        editor.apply()

        Toast.makeText(this, "Configuration saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
