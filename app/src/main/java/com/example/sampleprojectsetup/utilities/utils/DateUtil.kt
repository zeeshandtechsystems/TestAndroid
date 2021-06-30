package com.example.sampleprojectsetup.utilities.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
/**
 * Develop By Messagemuse
 */
object DateUtil {

    fun dateFromUTC(date: Date): Date {
        return Date(date.time + Calendar.getInstance().timeZone.getOffset(date.time))
    }

    fun dateToUTC(date: Date): Date {
        return Date(date.time - Calendar.getInstance().timeZone.getOffset(date.time))
    }

    fun getDateInUtc(OurDate: String): String {
        var OurDate = OurDate
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(OurDate)
            OurDate = formatter.format(dateToUTC(value))
        } catch (e: Exception) {
            OurDate = "00-00-0000 00:00"
        }

        return OurDate
    }


    fun getDateInString(OurDate: Date): String {
        var stringDate = "0000-00-00T00:00:00"
        stringDate = try {
            val formatter = SimpleDateFormat("vyyy-MM-dd'T'HH:mm:ss")
            formatter.format(dateToUTC(OurDate))
        } catch (e: Exception) {
            "0000-00-00T00:00:00"
        }

        return stringDate
    }

    fun SendDateToDate(OurDate: String): String {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm a")
            val value = formatter.parse(OurDate)
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") //this format changeable
            val formattedDate = dateFormatter.format(value)
            formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        // val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long {
        var mDate = getLocalDate(date)
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var startDate = df.parse(mDate)
        val strCurrentDate = df.format(Calendar.getInstance().time)
        var currentDate = df.parse(strCurrentDate)
        val diff = currentDate.time!! - startDate.time!!
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
    }


    fun getYear(): Int {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, 2020)
        return cal.get(Calendar.YEAR)
    }

    fun convertDate(startDate: String, endDate: String): String {
        var finalDate: String? = null
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentDate = df.format(Calendar.getInstance().time)
        val splitedStartDate = startDate.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val splitedEndDate = endDate.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val subSplitedndDate = splitedEndDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val time1 = convertIn12HourFormat(startDate)
        val time2 = convertIn12HourFormat(endDate)
        if (splitedStartDate[0].equals(splitedEndDate[0], ignoreCase = true)) {
            finalDate = when {
                isToday(startDate) -> "Today $time1 to $time2"
                isYesterday(startDate) -> "Yesterday $time1 to $time2"
                isTomorrow(startDate) -> "Tomorrow $time1 to $time2"
                getDaysDifference(
                    startDate,
                    currentDate
                ) in 0..5 -> getDayName(startDate) + ", " + time1 + " to " + time2
                else -> getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0] +
                        " - " + time1 + " Between " + time2
            }
        } else {
            when {
                isToday(startDate) -> {
                    finalDate = "Today $time1"
                    finalDate = when {
                        isTomorrow(endDate) -> "$finalDate - Tomorrow $time2"
                        getDaysDifference(
                            endDate,
                            currentDate
                        ) in 0..5 -> finalDate + " - " + getDayName(endDate) + " " + time2
                        else -> finalDate + " - " + getMonthNameByValue(
                            Integer.parseInt(
                                subSplitedndDate[1]
                            )
                        ) + " " + subSplitedndDate[2] + ", " + subSplitedndDate[0] + "  " + time1
                    }
                }
                isTomorrow(startDate) -> {
                    finalDate = "Tomorrow $time1"
                    finalDate = when {
                        isTomorrow(endDate) -> "$finalDate - Tomorrow $time2"
                        getDaysDifference(
                            endDate,
                            currentDate
                        ) in 0..5 -> finalDate + " - " + getDayName(endDate) + " " + time2
                        else -> finalDate + " - " + getMonthNameByValue(
                            Integer.parseInt(
                                subSplitedndDate[1]
                            )
                        ) + " " + subSplitedndDate[2] + ", " + subSplitedndDate[0] + "  " + time1
                    }
                }
                isYesterday(startDate) -> {
                    finalDate = "Yesterday $time1"
                    finalDate = when {
                        isToday(endDate) -> "$finalDate - Today $time2"
                        isTomorrow(endDate) -> "$finalDate - Tomorrow $time2"
                        getDaysDifference(
                            endDate,
                            currentDate
                        ) in 0..5 -> finalDate + " - " + getDayName(endDate) + " " + time2
                        else -> finalDate + " - " + getMonthNameByValue(
                            Integer.parseInt(
                                subSplitedndDate[1]
                            )
                        ) + " " + subSplitedndDate[2] + ", " + subSplitedndDate[0] + "  " + time1
                    }
                }
                getDaysDifference(startDate, endDate) in 0..5 -> finalDate =
                        getDayName(startDate) + " " + time1 + " - " + getDayName(endDate) + " " + time2
                else -> finalDate =
                        getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0] +
                                "  " + time1 + " - " + getMonthNameByValue(
                            Integer.parseInt(
                                subSplitedndDate[1]
                            )
                        ) + " " + subSplitedndDate[2] + ", " + subSplitedndDate[0] + " " + time2
            }
        }
        return finalDate
    }

    fun convertSingleDateAndTime(startDate: String?): String {
        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)
            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            getDayName(startDate)  + ", " + getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0] + " \u00B7 " + time1
        } else {
            " "
        }
    }

    fun convertSingleDateNotification(startDate: String): String {
        val startDate = getLocalDate(startDate)
        var finalDate: String? = null
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentDate = df.format(Calendar.getInstance().time)
        getMinutesDifference(startDate, currentDate)
        val splitedStartDate = startDate.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val time1 = convertIn12HourFormatWithMinute(startDate)
        finalDate = when {
            isToday(startDate) -> when {
                getMinutesDifference(startDate, currentDate) < 1 -> "Just Now"
                getMinutesDifference(startDate, currentDate) < 60 -> "${
                    getMinutesDifference(
                    startDate,
                    currentDate
                )
                }min ago"
                else -> "${getHoursDifference(startDate, currentDate)}h ago"
            }
            isYesterday(startDate) -> "Yesterday $time1"
            isTomorrow(startDate) -> "Tomorrow $time1"
            getDaysDifference(startDate, currentDate) in 0..7 -> getDaysDifference(
                startDate,
                currentDate
            ).toString() +" days ago"
            else -> getDayName(startDate)  + ", " + getMonthNameByValue(
                Integer.parseInt(
                    subSplitedStartDate[1]
                )
            ) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0] + " \u00B7 " + time1
        }
        return finalDate
    }

    fun convertSingleDate(startDate: String?): String {
        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)

            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            getMonthFullNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0]
        } else {
            " "
        }
    }

    fun convertSingleDateWithoutYear(startDate: String?): String {
        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)

            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2]
        } else {
            " "
        }
    }


    fun convertSingleDateWithYear(startDate: String?): String {
        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)
            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0]
        } else {
            " "
        }
    }


    fun convertDateWithoutTime(startDate: String?): String {

        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)
            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            getMonthFullNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0]
        } else {
            " "
        }
    }


    fun convertSingleTime(startDate: String?): String {
        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)
            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            time1
        } else {
            " "
        }
    }

    private fun getMonthNameByValue(position: Int): String {
        return when (position - 1) {
            Calendar.JANUARY -> "Jan"
            Calendar.FEBRUARY -> "Feb"
            Calendar.MARCH -> "Mar"
            Calendar.APRIL -> "Apr"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "Jun"
            Calendar.JULY -> "Jul"
            Calendar.AUGUST -> "Aug"
            Calendar.SEPTEMBER -> "Sep"
            Calendar.OCTOBER -> "Oct"
            Calendar.NOVEMBER -> "Nov"
            Calendar.DECEMBER -> "Dec"
            else -> ""
        }
    }


    private fun getMonthFullNameByValue(position: Int): String {
        return when (position - 1) {
            Calendar.JANUARY -> "Jan"
            Calendar.FEBRUARY -> "Feb"
            Calendar.MARCH -> "Mar"
            Calendar.APRIL -> "Apr"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "Jun"
            Calendar.JULY -> "Jul"
            Calendar.AUGUST -> "Aug"
            Calendar.SEPTEMBER -> "Sep"
            Calendar.OCTOBER -> "Oct"
            Calendar.NOVEMBER -> "Nov"
            Calendar.DECEMBER -> "Dec"
            else -> ""
        }
    }

    fun convertSingleDateDoubleLine(startDate: String?): String {
        if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val currentDate = df.format(Calendar.getInstance().time)
            getMinutesDifference(getLocalDate(startDate), currentDate)
            val splitedStartDate = getLocalDate(startDate).split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(getLocalDate(startDate))
            finalDate = when {
                isToday(getLocalDate(startDate)) -> when {
                    getMinutesDifference(getLocalDate(startDate), currentDate).toInt() == 0 -> "Just Now"
                    getMinutesDifference(getLocalDate(startDate), currentDate) < 60 -> "${
                        getMinutesDifference(
                        getLocalDate(startDate), currentDate
                    )
                    }min ago"
                    else -> "${getHoursDifference(getLocalDate(startDate), currentDate)}h ago"
                }
                isYesterday(getLocalDate(startDate)) -> "Yesterday $time1"
                isTomorrow(getLocalDate(startDate)) -> "Tomorrow $time1"/* else if (getDaysDifference(startDate, currentDate) in 0..5) {
                    getDayName(startDate) + ", " + time1
                }*/
                else -> time1 + "\n" + getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0]
            }
            return finalDate
        } else {
            return " "
        }

    }

    fun convertSingleDateSchedule(startDate: String?): String {
        var startDate = getLocalDate(startDate!!)
        return if (startDate != null) {
            var finalDate: String? = null
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val splitedStartDate =startDate.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val subSplitedStartDate = splitedStartDate[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val time1 = convertIn12HourFormatWithMinute(startDate)
            finalDate = getMonthNameByValue(Integer.parseInt(subSplitedStartDate[1])) + " " + subSplitedStartDate[2] + ", " + subSplitedStartDate[0] +" - "+ time1
            finalDate
        } else {
            " "
        }

    }

    fun convertNotificationSingleDate(dataDate: String?): String? {
        var convTime: String? = null
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val pasTime = dateFormat.parse(dataDate)
            val nowTime = Date()
            val dateDiff = nowTime.time - pasTime.time
            val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
            if (second < 60) {
                convTime = "Just a moment ago"
            } else if (minute == 1.toLong()) {
                convTime = "1 minute ago"
            }else if (minute < 60) {
                convTime = "${minute} minutes ago"
            } else if (hour == 1.toLong()) {
                convTime = "1 hour ago"
            } else if (hour < 24) {
                convTime = "${hour} hours ago"
            }else if (day == 1.toLong()) {
                convTime = "1 day ago"
            } else if (day > 7) {
                convTime = if (day == 360.toLong()) {
                    "1 year ago"
                }else if (day > 360) {
                    (day / 360).toString() + " years ago"
                } else if (day == 30.toLong()) {
                    "1 month ago"
                } else if (day > 30) {
                    (day / 30).toString() + " months ago"
                } else {
                    (day / 7).toString() + " weeks ago"
                }
            } else if (day < 7) {
                convTime = "${day} days ago"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message!!)
        }
        return convTime
    }


    private fun convertIn12HourFormat(startTime: String): String {
        var startTime = startTime
        val splitedStartDate = startTime.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val date = splitedStartDate[0]
        val time = splitedStartDate[1]

        val sdf = SimpleDateFormat("HH:mm:ss")
        val sdfs = SimpleDateFormat("hh:mm a")
        val dt: Date
        try {
            dt = sdf.parse(time)
            startTime = sdfs.format(dt)
            println("Time Display: " + sdfs.format(dt)) // <-- I got result here
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return startTime
    }

    private fun convertIn12HourFormatWithMinute(startTime: String): String {
        var startTime = startTime
        val splitedStartDate = startTime.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val date = splitedStartDate[0]
        val time = splitedStartDate[1]

        val sdf = SimpleDateFormat("hh:mm:ss")
        val sdfs = SimpleDateFormat("h:mm a")
        val dt: Date
        try {
            dt = sdf.parse(time)
            startTime = sdfs.format(dt)
            println("Time Display: " + sdfs.format(dt)) // <-- I got result here
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return startTime.replace("am", "AM").replace("pm", "PM")
    }


    fun getDaysDifference(startDate: String, currentDate: String): Long {
        val diff = stringToDate(startDate)?.time!! - stringToDate(currentDate)?.time!!
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    }



/*    fun getMonthDifference(startDate: String): Int {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentDate = df.format(Calendar.getInstance().time)
        val startCalendar: Calendar = GregorianCalendar()
        startCalendar.time = stringToDate(startDate)!!
        val endCalendar: Calendar = GregorianCalendar()
        endCalendar.time = stringToDate(currentDate)!!

        val diff = startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH)

        return diff
    }*/

    fun getMonthDifference(startDate: String): Int {
        val dob = Calendar.getInstance()
        dob.time = stringToDate(startDate)!!
        val today = Calendar.getInstance()
        var monthsBetween = 0
        var dateDiff = today[Calendar.DAY_OF_MONTH] - dob[Calendar.DAY_OF_MONTH]
        if (dateDiff < 0) {
            val borrrow = today.getActualMaximum(Calendar.DAY_OF_MONTH)
            dateDiff =  dob[Calendar.DAY_OF_MONTH] - today[Calendar.DAY_OF_MONTH] + borrrow
            monthsBetween--
            if (dateDiff > 0) {
                monthsBetween++
            }
        } else {
            monthsBetween++
        }
        monthsBetween += dob[Calendar.MONTH] - today[Calendar.MONTH]
        monthsBetween += (dob[Calendar.YEAR] - today[Calendar.YEAR]) * 12
        return monthsBetween
    }

    fun getMinutesDifference(startDate: String, currentDate: String): Long {
        val diff = stringToDate(currentDate)?.time!! - stringToDate(startDate)?.time!!
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
    }

    fun getHoursDifference(startDate: String, currentDate: String): Long {
        val diff = stringToDate(currentDate)?.time!! - stringToDate(startDate)?.time!!
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)
    }

    private fun isToday(date: String): Boolean {
        val d = stringToDate(date)
        return android.text.format.DateUtils.isToday(d!!.time)
    }

    private fun isYesterday(date: String): Boolean {
        val d = stringToDate(date)
        return android.text.format.DateUtils.isToday(d!!.time + android.text.format.DateUtils.DAY_IN_MILLIS)
    }

    fun isTomorrow(date: String): Boolean {
        val d = stringToDate(date)
        return android.text.format.DateUtils.isToday(d!!.time - android.text.format.DateUtils.DAY_IN_MILLIS)
    }

    fun checkGreater(date:String):Boolean{
        val strDate = stringToDate(date)
        return Date().before(strDate)
    }

    fun stringToDate(dtString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date: Date? = null
        try {
            date = format.parse(dtString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    private fun getDayName(date: String): String? {
        var name: String? = null
        val outFormat = SimpleDateFormat("EEE")
        name = outFormat.format(stringToDate(date))
        return name
    }

    fun localToGMT(): String {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
    }

    fun getDateForPastFromDays(days: Int): String {
        var calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return dateFormat.format(dateToUTC(calendar.time))
    }

    fun getDateForPastFromDays(days: Int, ourDate: String): String {
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val value = formatter.parse(ourDate)
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = value
        calendar.add(Calendar.DAY_OF_YEAR, days)
        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return dateFormat.format(calendar.time)
    }

    fun getCurrentDate(): String? {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentDate = df.format(Calendar.getInstance().time)
        return currentDate
    }




    fun getLocalDate(OurDate: String): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date = simpleDateFormat.parse(OurDate)
            simpleDateFormat.format(dateFromUTC(date))
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun getDateDaysHoursDifference(date: String): String {
        var days = getDaysDifferenceComming(getLocalDate(date), getCurrentDate()!!)
        var hours = getHoursDifferenceComming(getLocalDate(date), getCurrentDate()!!) - (days * 24)
        var min = getMinutesDifferenceComming(getLocalDate(date), getCurrentDate()!!) - (((days * 24) + hours) * 60)

        if (days > 0) {
            return "$days Days, $hours Hours"
        } else if (hours > 0) {
            return "$hours Hours, $min Minutes"
        } else {
            return "$min Minutes"
        }

    }

    fun getDaysDifferenceComming(startDate: String, currentDate: String): Long {
        val diff = stringToDate(startDate)?.time!! - stringToDate(currentDate)?.time!!
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    }


    fun getMinutesDifferenceComming(startDate: String, currentDate: String): Long {
        val diff = stringToDate(startDate)?.time!! - stringToDate(currentDate)?.time!!
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
    }

    fun getHoursDifferenceComming(startDate: String, currentDate: String): Long {
        val diff = stringToDate(startDate)?.time!! - stringToDate(currentDate)?.time!!
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)
    }


    fun convertSeconds(seconds: Int): String {
        return String.format("%02d:%02d", (seconds % 3600) / 60, seconds % 60)
    }

    fun getDateofBirthWithoutT(dob: String): String {
        val split = dob.split("T")
        return split[0]
    }

    private fun getWeeksDates(weekNumber: Int, year: String): ArrayList<String> {
        val daysList = ArrayList<String>()
        val c = GregorianCalendar(Locale.getDefault())
        c.set(Calendar.WEEK_OF_YEAR, weekNumber)
        c.set(Calendar.YEAR, year.toInt())
        val firstDayOfWeek = c.firstDayOfWeek
        for (i in firstDayOfWeek until firstDayOfWeek + 7) {
            c.set(Calendar.DAY_OF_WEEK, i)
            daysList.add(SimpleDateFormat("yyyy-MM-dd").format(c.time)) }
        return daysList
    }

    fun getChartFilterStartDate(ourDate: String, year: String): String? {
        val c = Calendar.getInstance()
        val ourDate = ourDate +" $year"
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val value = formatter.parse(ourDate)
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        return dateFormatter.format(value)
    }

    fun getChartFilterEndDate(ourDate: String, year: String): String? {
        val c = Calendar.getInstance()
        val ourDate = ourDate +" $year"
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val value = formatter.parse(ourDate)
        c.time = value
        c.add(Calendar.DATE, 6)
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        return dateFormatter.format(c.time)
    }

    fun getChartFilterDateData(ourDate: String, year: String): ArrayList<String> {
        var list = ArrayList<String>()
        var ourDate = ourDate +" $year"
        if (getDaysDifference(
                getDateForPastFromDays(7, ourDate),
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(
                    Calendar.getInstance().time
                )
            ) > 0){
            ourDate = SimpleDateFormat("dd MMM yyyy").format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(
                    getDateForPastFromDays(
                        -getDaysDifference(
                            getDateForPastFromDays(
                                7,
                                ourDate
                            ),
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().time)
                        ).toInt(), ourDate
                    )
                )
            )
        }

        val formatter = SimpleDateFormat("dd MMM yyyy")
        val value = formatter.parse(ourDate)
        for (i in 0 until 7){
            val c = Calendar.getInstance()
            c.time = value
            c.add(Calendar.DATE, i)
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
            list.add(dateFormatter.format(c.time))
        }
        return list
    }


    fun getWeeksBetween(a: Date, b: Date): Int {
        var a = a
        var b = b
        if (b.before(a)) {
            return -getWeeksBetween(b, a)
        }
        a = resetTime(a)
        b = resetTime(b)

        val cal = GregorianCalendar()
        cal.time = a
        var weeks = 0
        while (cal.time.before(b)) {

            cal.add(Calendar.WEEK_OF_YEAR, 1)
            weeks++
        }
        return weeks
    }

    private fun resetTime(d: Date): Date {
        val cal = GregorianCalendar()
        cal.time = d
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }


    fun getStartAndEndDateOfMonth(month: Int) : String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, month)
        calendar[Calendar.DATE] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val monthFirstDay = calendar.time
        calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthLastDay = calendar.time

        val df = SimpleDateFormat("yyyy-MM-dd")
        val startDateStr = df.format(monthFirstDay)
        val endDateStr = df.format(monthLastDay)
        Log.e("DateFirstLast", "$startDateStr $endDateStr")
        return "$startDateStr $endDateStr"
    }

}