package id.com.yopisptr.deepest.Retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var ourInstance:Retrofit?= null
    val instance:Retrofit
        get(){
            if (ourInstance == null)
                ourInstance = Retrofit.Builder()
                    .baseUrl("http://34.101.169.185:5000/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
            return ourInstance!!
        }
}