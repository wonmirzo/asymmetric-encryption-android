package com.wonmirzo.asymmetric.model

import java.io.Serializable

data class User(
    val email: String,
    val password: String
) : Serializable
