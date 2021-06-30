package com.example.sampleprojectsetup.utilities.extensions

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.sampleprojectsetup.R

/**
 * Develop By Messagemuse
 */
var loadingDialoge: Dialog? = null

fun Context.showAppLoader() {
    if (loadingDialoge != null) {
        if (!loadingDialoge!!.isShowing) {
            loadingDialoge = Dialog(this, R.style.Theme_Dialog_Loader)
            loadingDialoge!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            loadingDialoge!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialoge!!.setContentView(R.layout.dialoge_app_loader)
            loadingDialoge!!.setCancelable(false)
            loadingDialoge!!.show()
        }
    } else {
        loadingDialoge = Dialog(this, R.style.Theme_Dialog_Loader)
        loadingDialoge!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialoge!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialoge!!.setContentView(R.layout.dialoge_app_loader)
        loadingDialoge!!.setCancelable(false)
        loadingDialoge!!.show()
    }
}

fun hideAppLoader() {
    if (loadingDialoge != null && loadingDialoge!!.isShowing) {
        try {
            loadingDialoge!!.dismiss()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
}


