package com.prisma.prismavi.viewmodel.color

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prisma.prismavi.api.RetrofitConfig.apiService
import com.prisma.prismavi.api.model.ColorModel
import com.prisma.prismavi.api.model.ColorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColorViewModel : ViewModel() {
    private val _colorResponse = MutableLiveData<ColorModel>()
    val colorResponse: LiveData<ColorModel> = _colorResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchColorDetails(hex: String) {
        _loading.value = true

        apiService.getColorInfo(hex).enqueue(object : Callback<ColorResponse> {
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