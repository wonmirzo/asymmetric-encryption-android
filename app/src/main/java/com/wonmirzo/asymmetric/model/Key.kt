package com.wonmirzo.asymmetric.model

import java.io.Serializable

data class Key(
    val privateKey: String,
    val publicKey: String
) : Serializable