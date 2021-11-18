package com.deloitte.wtest.utils

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.deloitte.wtest.database.postalcode.PostalCode
import java.lang.Exception
import java.text.DecimalFormat

@BindingAdapter("postalCode")
fun TextView.setPostalCode(postalCode: PostalCode) {
    text = "${postalCode.postalCodeNum}-${postalCode.postalCodeExt}"
}