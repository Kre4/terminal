package ru.kre4

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform