package com.example.annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class ViewHolderBinding(val viewId: Int)
