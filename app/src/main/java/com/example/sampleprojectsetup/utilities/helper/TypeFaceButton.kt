package com.example.sampleprojectsetup.utilities.helper

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.TintableBackgroundView
import com.example.sampleprojectsetup.R


class TypeFaceButton : AppCompatButton, TintableBackgroundView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setCustomFont(context, attrs)
    }

    private fun setCustomFont(ctx: Context, attrs: AttributeSet) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus)
        val customFont = a.getString(R.styleable.TextViewPlus_customFont)
        setCustomFont(ctx, customFont)
        a.recycle()
    }

    fun setCustomFont(ctx: Context, asset: String?): Boolean {
        var tf: Typeface? = null
        tf = if (asset != null) {
            try {
                Typeface.createFromAsset(ctx.assets, asset)
            } catch (e: Exception) {

                return false
            }
        } else {
            try {
                Typeface.createFromAsset(ctx.assets, "roboto_regular.ttf")
            } catch (e: Exception) {

                return false
            }
        }
        typeface = tf
        return true
    }

    companion object {
        private val TAG = "Button"
    }

}