package com.prisma.prismavi.api.model

data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(
    val token: String,
    val prefix: String
)