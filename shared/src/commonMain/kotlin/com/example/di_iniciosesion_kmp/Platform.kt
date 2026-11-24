package com.example.di_iniciosesion_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform