package com.example.converter.presenter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.*
import io.reactivex.rxjava3.disposables.Disposable

const val CONVERTING_START = "converting was started"
const val CONVERTING_FINISH = "converting is finished"

class MainPresenter(val converter: IConverter, val view: MainView) {
    var disposable: Disposable? = null

    fun imageChosen(image: Image, uri: String) {
        view.showMessage(CONVERTING_START)
        disposable = converter.convert(image, uri)
                .observeOn(mainThread())
                .subscribe({
                    view.showMessage(CONVERTING_FINISH)
                    view.stopConvertingDialog()
                }, {
                    println("ошибка при конвертации")
                })
        view.showConvertingDialog()
    }

    fun cancelConverting() {
        disposable?.dispose()
        view.stopConvertingDialog()
    }
}
