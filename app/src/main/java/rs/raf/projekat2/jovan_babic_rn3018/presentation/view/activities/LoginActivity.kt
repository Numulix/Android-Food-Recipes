package rs.raf.projekat2.jovan_babic_rn3018.presentation.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import rs.raf.projekat2.jovan_babic_rn3018.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val PIN = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initListeners()
    }

    private fun initListeners() {
        binding.loginBtn.setOnClickListener {
            val uname = binding.unameEt.text.toString()
            val pin = binding.pinEt.text.toString()

            if (uname.isBlank() || pin.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pin == PIN) {
                val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("LOGGED_IN", true)
                    apply()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "The PIN you have entered is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}