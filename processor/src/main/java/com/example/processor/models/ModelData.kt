package com.example.processor.models

data class ModelData(
    val packageName: String,
    val modelName: String,
    val layoutId: Int,
    val viewHolderBindingData: List<ViewHolderBindingData>
)