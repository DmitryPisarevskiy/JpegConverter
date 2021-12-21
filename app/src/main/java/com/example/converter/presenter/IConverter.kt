package com.example.converter.presenter

import io.reactivex.rxjava3.core.Completable

interface IConverter {
    fun convert(image: Image, uri: String): Completable

}

