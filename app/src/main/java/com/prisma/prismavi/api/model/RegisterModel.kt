package com.prisma.prismavi.api.model

data class RegisterRequest(
    val nickName: String,
    val email: String,
    val password: String
)
data class RegisterResponse(
    val message: String
)
