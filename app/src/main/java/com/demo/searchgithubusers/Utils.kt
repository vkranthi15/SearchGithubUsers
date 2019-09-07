package com.demo.searchgithubusers

import java.text.SimpleDateFormat

open class Utils {
    companion object {
        fun dateFormat(strCurrentDate: String): String {
            var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            val newDate = format.parse(strCurrentDate)
            format = SimpleDateFormat("MMM dd,yyyy hh:mm a")
            return format.format(newDate)
        }
    }
}

