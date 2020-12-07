package com.example.annotationprocessor

import com.example.annotation.AdapterModel
import com.example.annotation.ViewHolderBinding

@AdapterModel(R.layout.layout_person)
data class Cat(
    @ViewHolderBinding(R.id.catName) val name: String,
    @ViewHolderBinding(R.id.catAge) val age: String
)
