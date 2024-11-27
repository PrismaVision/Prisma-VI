package com.prisma.prismavi.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.prisma.prismavi.api.RetrofitConfig
import com.prisma.prismavi.api.model.LoginRequest
import com.prisma.prismavi.api.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val loginStatus = MutableLiveData<LoginStatus>(LoginStatus.Loading)
    val loginState: LiveData<LoginStatus> = loginStatus

    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            loginStatus.value = LoginStatus.Error("Email e Senha são obrigatórios.")
            return
        }

        val loginRequest = LoginRequest(email, password)
        RetrofitConfig.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {

            private fun parseError(response: Response<*>): String {
                Log.d("LoginViewModel", "HTTP Status Code: ${response.code()}")
                Log.d("LoginViewModel", "Response Body: ${response.message()}")
                val errorBody = response.errorBody()?.string()
                return if (!errorBody.isNullOrEmpty()) {
                    try {
                        val errorJson = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        errorJson.message ?: "Erro desconhecido da API"
                    } catch (e: Exception) {
                        "JSON inválido"
                    }
                } else {
                    "Email ou Senha Inválidos"
                }
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        loginStatus.value = LoginStatus.Success(loginResponse.token)
                    } ?: run {
                        loginStatus.value = LoginStatus.Error("Resposta inválida da API.")
                    }
                } else {
                    loginStatus.value = LoginStatus.Error(parseError(response))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginStatus.value = LoginStatus.Error("Erro de conexão: ${t.message}")
            }
        })
    }
}

sealed class LoginStatus {
    object Loading : LoginStatus()
    data class Success(val token: String?) : LoginStatus()
    data class Error(val message: String) : LoginStatus()
}

data class ErrorResponse(
    val message: String?
)
