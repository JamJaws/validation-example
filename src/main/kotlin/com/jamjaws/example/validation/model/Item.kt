package com.jamjaws.example.validation.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import jakarta.validation.constraints.Positive

data class Item(
    @get:Null(groups = [Create::class])
    @get:NotNull(groups = [Update::class])
    @get:Positive(groups = [Update::class])
    val id: Long? = null,
    @get:NotBlank
    val name: String? = null,
    @get:NotBlank
    val description: String? = null,
)
