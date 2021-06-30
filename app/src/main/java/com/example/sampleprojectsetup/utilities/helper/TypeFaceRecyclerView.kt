package com.example.sampleprojectsetup.utilities.helper

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleprojectsetup.R
import com.example.sampleprojectsetup.utilities.extensions.dpToPx


class TypeFaceRecyclerView : RecyclerView {

    private var linearLayout: LinearLayout? = null
    private var mMaxHeight: Int = 0
    private var emptyIcon : Drawable? = null
    private var iconColor : Int = 0
    private var emptyText : String? = null
    private var emptyTextColor : Int = 0
    private var imageHeight  = context.dpToPx(50)
    private var imageWidth  = context.dpToPx(50)

    private val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initialize(context, attrs)
    }

    internal fun checkIfEmpty() {
        if (linearLayout != null && adapter != null) {
            val emptyViewVisible = adapter!!.itemCount == 0
            linearLayout!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }else if (linearLayout != null) {
            linearLayout!!.visibility = View.GONE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
        if (linearLayout != null) {
            val parent = this.parent
            if (parent is RelativeLayout) {
                parent.removeView(linearLayout)
            } else if (parent is LinearLayout) {
                parent.removeView(linearLayout)
            }else{
                var mainParent = parent.parent

                if (mainParent is RelativeLayout) {
                    mainParent.removeView(linearLayout)
                } else if (mainParent is LinearLayout) {
                    mainParent.removeView(linearLayout)
                }
            }
        }
        linearLayout = null
        isEmptyView(context)
        checkIfEmpty()
    }

    fun setEmptyIcon(icon: Drawable){
        this.emptyIcon = icon
    }

    fun setEmptyIconColor(iconColor : Int){
        this.iconColor = iconColor
    }

    fun setEmptyText(emptyText : String){
        this.emptyText = emptyText
    }

    fun setEmptyTextColor(emptyTextColor: Int){
        this.emptyTextColor = emptyTextColor
    }

    fun setImageHeight(height : Int){
        imageHeight = height
    }


    fun setImageWidth(width : Int){
        imageWidth = width
    }

    private fun isEmptyView(context: Context) {
        linearLayout = LinearLayout(context)
        linearLayout!!.layoutParams = this.layoutParams
        linearLayout!!.orientation = LinearLayout.VERTICAL
        linearLayout!!.gravity = Gravity.CENTER

        if (emptyIcon != null){
            val image = ImageView(context)
            image.layoutParams = LinearLayout.LayoutParams(
                imageWidth,
                imageHeight
            )
            image.setImageDrawable(emptyIcon)
            if (iconColor != 0){
                image.setColorFilter(iconColor)
            }/*else {
                image.setColorFilter(ContextCompat.getColor(context, R.color.pink))
            }*/
            linearLayout!!.addView(image)
        }

        if (emptyText != null){
            val textView = TextView(context)
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val margin = textView.layoutParams as LinearLayout.LayoutParams
            margin.setMargins(0,context.dpToPx(10),0,0)
            textView.layoutParams = margin
            if (emptyTextColor != 0){
                textView.setTextColor(emptyTextColor)
            }else
                textView.setTextColor(ContextCompat.getColor(context, R.color.gray))
            textView.text = emptyText
            textView.textSize = 15f
            textView.typeface = Typeface.SANS_SERIF
            linearLayout!!.addView(textView)
        }
        val parent = this.parent
        if (parent is RelativeLayout){
            parent.addView(linearLayout)
        }else if (parent is LinearLayout){
            parent.addView(linearLayout)
        }
        checkIfEmpty()
    }

    private fun initialize(context: Context, attrs: AttributeSet) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView)
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightScrollView_maxHeight, mMaxHeight)
        this.overScrollMode = View.OVER_SCROLL_NEVER
        arr.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        if (mMaxHeight > 0) {
            heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(mMaxHeight, View.MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


}