package com.example.converter.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.documentfile.provider.DocumentFile
import com.example.converter.R
import com.example.converter.presenter.Image
import com.example.converter.presenter.MainPresenter
import com.example.converter.presenter.MainView
import java.io.File
import java.net.URI

const val CHOOSE_IMAGE_TAG = 777

class MainActivity : AppCompatActivity(), MainView {
    val presenter: MainPresenter = MainPresenter(Converter(this), this)
    lateinit var btnStart: Button
    var convertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btn_start)
        btnStart.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(Intent.createChooser(intent, getString(R.string.choose_jpeg)), CHOOSE_IMAGE_TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE_TAG && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val bytes = contentResolver.openInputStream(uri)?.buffered()?.use {
                    it.readBytes()
                }
                bytes.let {
                    presenter.imageChosen(Image(bytes), File(uri.path!!).path)
                }
            }
        }
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        println(msg)
    }

    override fun showConvertingDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.message))
                .setMessage(getString(R.string.converting_is_proceed))
                .setIcon(R.drawable.ic_stat_name)
                .setNegativeButton(getString(R.string.cancel)) { dialog, it ->
                    presenter.cancelConverting()
                }
        convertDialog = builder.create()
        convertDialog!!.show()
    }

    override fun stopConvertingDialog() {
        convertDialog?.dismiss()
    }
}