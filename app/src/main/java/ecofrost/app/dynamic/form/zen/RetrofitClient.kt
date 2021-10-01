package ecofrost.app.dynamic.form.zen

import ecofrost.app.dynamic.form.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val BASE_URL = "https://mocki.io/v1/"

    val retrofitClient : Retrofit.Builder by lazy {

        val levelType : HttpLoggingInterceptor.Level
        if(BuildConfig.BUILD_TYPE.contentEquals("debug")){
            levelType = HttpLoggingInterceptor.Level.BODY
        }
        else
            levelType = HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val api : API by lazy {
        retrofitClient.build()
            .create(API::class.java)
    }
}