package com.setyongr.greenesia.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks

import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask

import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText

import java.util.ArrayList

import com.setyongr.greenesia.R

import android.Manifest.permission.READ_CONTACTS
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.widget.Toolbar
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.presenters.LoginPresenter
import com.setyongr.greenesia.views.interfaces.LoginMvpView
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(), LoginMvpView {


    @Inject lateinit var mLoginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MainApp.greenesiaComponent.inject(this)

        mLoginPresenter.attachView(this)

        btn_login.setOnClickListener {
            mLoginPresenter.doLogin(input_email.text.toString(), input_password.text.toString())
        }
    }

    var dialog: ProgressDialog? = null

    override fun showProgress() {
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true)
    }

    override fun hideProgress() {
        dialog?.dismiss()
    }

    override fun onLoginSuccess() {
        hideProgress()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showError() {
        AlertDialog.Builder(this).setTitle("Error").setMessage("Login Gagal").show()
    }

}

