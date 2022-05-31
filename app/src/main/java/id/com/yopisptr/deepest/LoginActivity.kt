package id.com.yopisptr.deepest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import id.com.yopisptr.deepest.databinding.ActivityLoginBinding
import id.com.yopisptr.deepest.modelfactory.ModelFactoryAuth
import id.com.yopisptr.deepest.viewmodel.ViewModelLogin
import kotlin.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelLogin: ViewModelLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar>.hide()

        binding.btnTvRegister.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
        runViewModel()
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.etEmail.error = resources.getString(R.string.login_error)
            }
            password.isEmpty() -> {
                binding.etPassword.error = resources.getString(R.string.login_error)
            }
            else -> {
                viewModelLogin.login(email, password).observe(this){ result ->
                    if (result != null) {
                        when (result) {
                            is Result.Success -> {
                                val user = result.data
                                if (user.error) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        user.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val token = user.loginResult?.token ?: ""
                                    viewModelLogin.setToken(token, true)
                                }
                            }
                            is Result.Error -> {
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.login_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun runViewModel() {
        val factory: ModelFactoryAuth = ModelFactoryAuth.getInstance(this)
        viewModelLogin = ViewModelProvider(this, factory)[ViewModelLogin::class.java]

        viewModelLogin.getToken().observe(this) { token ->
            if (token.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

}