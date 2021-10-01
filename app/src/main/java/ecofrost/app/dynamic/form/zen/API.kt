package ecofrost.app.dynamic.form.zen

import retrofit2.Call
import retrofit2.http.GET

interface API {

    @GET("49d42596-da04-42c3-bed7-3b9c376facd2")
    fun getForms() : Call<List<FO>>
}