package com.example.loginregisterdata2.Models

data class LoginResponse(val error: Boolean, var message: String, val user: User)