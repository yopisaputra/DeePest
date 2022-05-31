package id.com.yopisptr.deepest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import id.com.yopisptr.deepest.databinding.ActivityRegisterBinding
import id.com.yopisptr.deepest.viewmodel.ViewModelRegister
import kotlin.Result

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelRegister: ViewModelRegister
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnTvRegister.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }

        binding.btnRegister.setOnClickListener{
            register()
        }
    }
    private fun register() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.etEmail.error =
                    resources.getString(R.string.message_validation, "email")
            }
            password.isEmpty() -> {
                binding.etPassword.error =
                    resources.getString(R.string.message_validation, "password")
            }
            else -> {
                viewModelRegister.register(email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Success -> {
                                val user = result.data
                                if (user.error) {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        user.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        resources.getString(R.string.register_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    AlertDialog.Builder(this@RegisterActivity).apply {
                                        setTitle("Yeah!")
                                        setMessage("Your account successfully created!")
                                        setPositiveButton("Next") { _, _ ->
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            }
                            is Result.Error -> {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    resources.getString(R.string.register_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}