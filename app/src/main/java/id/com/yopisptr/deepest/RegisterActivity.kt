package id.com.yopisptr.deepest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import id.com.yopisptr.deepest.databinding.ActivityRegisterBinding
import id.com.yopisptr.deepest.modelfactory.ModelFactoryAuth
import id.com.yopisptr.deepest.viewmodel.ViewModelRegister
import id.com.yopisptr.deepest.Result.Error
import id.com.yopisptr.deepest.Result.Success

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelRegister: ViewModelRegister
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Register"

        val factory: ModelFactoryAuth = ModelFactoryAuth.getInstance(this)
        viewModelRegister = ViewModelProvider(this, factory)[ViewModelRegister::class.java]

        binding.btnTvRegister.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
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
                binding.etEmail.error = resources.getString(R.string.register_error)
            }
            password.isEmpty() -> {
                binding.etEmail.error = resources.getString(R.string.register_error)
            }
            else -> {
                viewModelRegister.register(email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Success -> {
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
                            is Error -> {
                                Toast.makeText(
                                    this,
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