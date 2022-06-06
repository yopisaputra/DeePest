package id.com.yopisptr.deepest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.com.yopisptr.deepest.Retrofit.INodeJS
import id.com.yopisptr.deepest.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var myAPI: INodeJS
    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Init API
        val retrofit = RetrofitClient.instance
        myAPI = retrofit.create(INodeJS::class.java)

        btnTvToRegister.setOnClickListener {
            val toRegisterIntent = Intent(this, RegisterActivity::class.java)
            startActivity(toRegisterIntent)
        }

        btnLogin.setOnClickListener {
            login(etEmail.text.toString(),etPassword.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        compositeDisposable.add(myAPI.loginUser(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ message -> Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            })
    }

}