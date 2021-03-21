package com.example.converter.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.converter.presenter.IConverter
import com.example.converter.presenter.Image
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

const val FILE_PNG = "converted.png"

class Converter(val context: Context) : IConverter {
    override fun convert(image: Image, uri: String) = Completable.fromAction {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            println("конвертация прервана")
        }

        val bmp = BitmapFactory.decodeByteArray(image.data, 0, image.data!!.size)
        val out = FileOutputStream(File(context.getExternalFilesDir(null), FILE_PNG))
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.close();
    }.subscribeOn(Schedulers.io())
}