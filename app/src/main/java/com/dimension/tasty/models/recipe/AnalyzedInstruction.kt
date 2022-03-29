package com.dimension.tasty.models.recipe

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)