package com.prisma.prismavi.viewmodel.color

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prisma.prismavi.api.RetrofitConfig.apiService
import com.prisma.prismavi.api.model.ColorModel
import com.prisma.prismavi.api.model.ColorResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//A ColorViewModel é responsável por gerenciar o estado da tela que exibe os detalhes de uma cor.
// Ela realiza a chamada para a API, lida com o carregamento e possíveis erros,
//e fornece os dados formatados para a UI (Jetpack Compose ou XML).
class ColorViewModel : ViewModel() {


    private val _colorResponse = MutableLiveData<ColorModel>() //Armazena os detalhes da cor recebidos da API.
    val colorResponse: LiveData<ColorModel> = _colorResponse

    private val _loading = MutableLiveData<Boolean>() //Indica se a requisição está em andamento. A UI pode exibir um spinner de carregamento.
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>() //Guarda mensagens de erro (falha na requisição ou resposta inválida), que podem ser mostradas na tela.
    val error: LiveData<String> = _error


    fun fetchColorDetails(hex: String) { //Função pública que inicia a requisição para buscar os detalhes da cor com o código hexadecimal informado.
        _loading.value = true

        val hexWithQuotes = "\"$hex\"" // garante que a string seja interpretada como JSON string
        val body = hexWithQuotes.toRequestBody("text/plain".toMediaType())

        apiService.getColorInfo(body).enqueue(object : Callback<ColorResponse> { //Chamada assíncrona com Retrofit.
            override fun onResponse(call: Call<ColorResponse>, response: Response<ColorResponse>) {
                Log.d("ColorViewModel", "Response Body: ${response.body()}")
                Log.d("ColorViewModel", "HTTP Code: ${response.code()}")
                Log.d("ColorViewModel", "Enviando HEX: $hex")

                _loading.value = false
                if (response.isSuccessful) {
                    response.body()?.color?.let {
                        _colorResponse.value = it
                    }
                } else {
                    _error.value = "Erro na API: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<ColorResponse>, t: Throwable) {
                Log.e("API_ERROR", "Request Error: ${t.message}")

                _loading.value = false
                _error.value = "Falha na requisição: ${t.message}"
            }
        })
    }
}