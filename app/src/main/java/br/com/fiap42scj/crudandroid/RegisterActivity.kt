package br.com.fiap42scj.crudandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import br.com.fiap42scj.crudandroid.databinding.ActivityLoginBinding
import br.com.fiap42scj.crudandroid.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var emailRegex =
        Pattern.compile("[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        fullScreen()
        setContentView(view)

        binding.btRegister.setOnClickListener {

            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val passwordConfirm = binding.inputPasswordConfirm.text.toString()

            if ((email.isBlank() || !emailRegex.matcher(email).matches())) {
                toast(getString(R.string.invalid_email_message))
                return@setOnClickListener
            }

            if (password.isBlank()) {
                toast(getString(R.string.invalid_password_message))
                return@setOnClickListener
            }

            if (password.length < 8) {
                toast(getString(R.string.invalid_password_length_message))
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                toast(getString(R.string.invalid_password_confirm_message))
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    toast(getString(R.string.invalid_credentials_message))
                }
            }

        }

        binding.btBackLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    private fun fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }

    private fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}