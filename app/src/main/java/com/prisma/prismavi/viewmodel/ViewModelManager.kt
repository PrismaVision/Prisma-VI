package com.prisma.prismavi.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.prisma.prismavi.viewmodel.color.ColorViewModel
import com.prisma.prismavi.viewmodel.user.LoginViewModel
import com.prisma.prismavi.viewmodel.user.RegisterViewModel

class ViewModelManager(
    private val viewModelProvider: ViewModelProvider
) {
    fun colorViewModelConnection(colorToHexString: String) {
        colorViewModel.fetchColorDetails(colorToHexString)
    }

    fun loginViewModelConnection(email: String, password: String) {
        loginViewModel.login(email, password)
    }

    fun registerViewModelConnection(nickName: String, email: String, password: String) {
        registerViewModel.register(nickName, email, password)
    }

    val colorViewModel: ColorViewModel by lazy {
        viewModelProvider[ColorViewModel::class.java]
    }

    val loginViewModel: LoginViewModel by lazy {
        viewModelProvider[LoginViewModel::class.java]
    }

    val registerViewModel: RegisterViewModel by lazy {
        viewModelProvider[RegisterViewModel::class.java]
    }
}
