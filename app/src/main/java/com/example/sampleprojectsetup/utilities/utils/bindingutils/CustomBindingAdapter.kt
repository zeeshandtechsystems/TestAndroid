package com.example.sampleprojectsetup.utilities.utils.bindingutils

import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

import com.example.sampleprojectsetup.utilities.Constants
import com.example.sampleprojectsetup.utilities.extensions.*
import com.example.sampleprojectsetup.utilities.helper.TypeFaceEditText
import com.example.sampleprojectsetup.utilities.helper.TypeFaceRecyclerView
import com.example.sampleprojectsetup.utilities.helper.TypeFaceTextView
import com.example.sampleprojectsetup.utilities.utils.DateUtil
import com.example.sampleprojectsetup.utilities.utils.DateUtil.getLocalDate
import com.example.sampleprojectsetup.view.base.ActivityBase
import de.hdodenhof.circleimageview.CircleImageView
/**
 * Develop By Messagemuse
 */
class CustomBindingAdapter {
    companion object {
        @BindingAdapter("app:visibleGone")
        @JvmStatic
        fun showHide(view: View, show: Boolean) {
            view.visibility = if (show) View.VISIBLE else View.GONE
        }

        @BindingAdapter("app:visibleInVisible")
        @JvmStatic
        fun showInvisible(view: View, show: Boolean) {
            view.visibility = if (show) View.VISIBLE else View.INVISIBLE
        }


        @BindingAdapter("app:loadImage")
        @JvmStatic
        fun setImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null)
                view.load(imageUrl)
        }

        @BindingAdapter("app:loadImageDp")
        @JvmStatic
        fun setImageDp(view: ImageView, imageUrl: String?) {
            if (imageUrl != null)
                view.loadDps(imageUrl)
        }


        @BindingAdapter("app:loadSvg")
        @JvmStatic
        fun setSVG(view: ImageView, imageUrl: String?) {
            //if (imageUrl != null)
               // ActivityBase.activity.loadSVG(view, imageUrl)
        }



        @BindingAdapter("app:emptyText")
        @JvmStatic
        fun setEmptyText(view: TypeFaceRecyclerView, data: String) {
            view.setEmptyText(data)
        }

        @BindingAdapter("app:emptyTextColor")
        @JvmStatic
        fun setEmptyTextColor(view: TypeFaceRecyclerView, data: Int) {
            view.setEmptyTextColor(data)
        }

        @BindingAdapter("app:emptyIcon")
        @JvmStatic
        fun setEmptyIcon(view: TypeFaceRecyclerView, data: Drawable) {
            view.setEmptyIcon(data)
        }

        @BindingAdapter("app:emptyIconColor")
        @JvmStatic
        fun setEmptyIconColor(view: TypeFaceRecyclerView, data: Int) {
            view.setEmptyIconColor(data)
        }


        @BindingAdapter("app:emptyIconHeight")
        @JvmStatic
        fun setEmptyIconHeight(view: TypeFaceRecyclerView, data: Float) {
            view.setImageHeight(ActivityBase.activity.dpToPx(data.toInt()))
        }


        @BindingAdapter("app:emptyIconWidth")
        @JvmStatic
        fun setEmptyIconWidth(view: TypeFaceRecyclerView, data: Float) {
            view.setImageWidth(ActivityBase.activity.dpToPx(data.toInt()))
        }


        @BindingAdapter("app:rightIcon")
        @JvmStatic
        fun setRightIcon(view: ImageView, drawable: Int) {
            if (drawable == 0) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
                view.setImageResource(drawable)
            }
        }



        @BindingAdapter("app:genderSelection")
        @JvmStatic
        fun GenderSelection(view: TypeFaceTextView, genderId: Int) {
            when (genderId) {
                1 -> view.text = "Male"
                2 -> view.text = "Female"
                3 -> view.text = "Other / GNC"
            }
        }





        @BindingAdapter("app:setFirstLetter")
        @JvmStatic
        fun firstLetter(view: TypeFaceTextView, data: String) {
            if (data.isNotEmpty()) {
                view.text = data[0].toUpperCase().toString()
            }
        }



        @BindingAdapter("app:setDateTime")
        @JvmStatic
        fun loadDateTime(view: TypeFaceTextView, date: String) {
            if (date != null && !TextUtils.isEmpty(date)) {
                view.text = DateUtil.convertSingleDateSchedule(date)
            }
        }

        @BindingAdapter("app:setNotiDate")
        @JvmStatic
        fun loadNotiTime(view: TypeFaceTextView, date: String) {
            if (date != null && !TextUtils.isEmpty(date)) {
                view.text = DateUtil.convertNotificationSingleDate(getLocalDate(date!!))
            }
        }




        @BindingAdapter("app:setDate")
        @JvmStatic
        fun loadDate(view: TypeFaceTextView, date: String) {
            if (date != null && !TextUtils.isEmpty(date)) {
                view.text = DateUtil.convertDateWithoutTime(date)
            }
        }

        @BindingAdapter("app:setTime")
        @JvmStatic
        fun loadTime(view: TypeFaceTextView, date: String) {
            if (date != null && !TextUtils.isEmpty(date)) {
                view.text = DateUtil.convertSingleTime(date)
            }
        }

        @BindingAdapter("app:setCompleteDateWithTime")
        @JvmStatic
        fun loadDateWithTime(view: TypeFaceTextView, date: String?) {
            if (date != null && !TextUtils.isEmpty(date)) {
                view.text = "${DateUtil.convertDateWithoutTime(date)}"
            }
        }

        @BindingAdapter("app:setCompleteTime")
        @JvmStatic
        fun loadTimeMain(view: TypeFaceTextView, date: String?) {
            if (date != null && !TextUtils.isEmpty(date)) {
                view.text = "${DateUtil.convertSingleTime(date)}"
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        @BindingAdapter("app:loadHtmlData")
        @JvmStatic
        fun loadHtmlData(view: TypeFaceTextView, data: String) {
            if (data != null && data.isNotEmpty())
                view.text = Html.fromHtml(data, Html.FROM_HTML_MODE_LEGACY)
        }


        @BindingAdapter("app:setHTMLText")
        @JvmStatic
        fun setTextFromHTML(view: TypeFaceTextView, text: String) {
            if (!TextUtils.isEmpty(text)) {
                view.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }

        @BindingAdapter("app:setFloatValue")
        @JvmStatic
        fun setTextFloat(view: TypeFaceTextView, value: Double) {
            view.text = "$${String.format("%.2f", value)}"
        }




    }


}