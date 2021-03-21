package com.example.converter.presenter

interface MainView {
    fun showMessage(msg: String)
    fun showConvertingDialog()
    fun stopConvertingDialog()
}