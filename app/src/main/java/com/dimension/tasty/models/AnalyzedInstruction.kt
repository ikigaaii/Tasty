package com.dimension.tasty.models

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)