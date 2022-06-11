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
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var myAPI: INodeJS
    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Init API
        var retrofit = RetrofitClient.instance
        myAPI = retrofit.create(INodeJS::class.java)

        btnTvToLogin.setOnClickListener {
            val toLoginIntent = Intent(this, LoginActivity::class.java)
            startActivity(toLoginIntent)
        }

        btnRegister.setOnClickListener {
            register(etEmail.text.toString(),etPassword.text.toString())
        }
    }

    private fun register(email: String, password: String) {

        compositeDisposable.add(myAPI.registerUser(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ message ->
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
            })
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}