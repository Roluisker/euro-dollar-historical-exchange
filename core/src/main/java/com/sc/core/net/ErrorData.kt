package com.sc.core.net

data class BasicError(
    val code: Int,
    val exception: Exception,
    val message: String
)