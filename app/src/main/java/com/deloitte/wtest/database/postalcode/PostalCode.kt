package com.deloitte.wtest.database.postalcode

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "PostalCode", primaryKeys = ["postal_code_number", "postal_code_extension"])
data class PostalCode(
    @ColumnInfo(name="location_code")
    val locationCode: String,
    @ColumnInfo(name="location_name")
    val locationName: String,
    @ColumnInfo(name="postal_code_number")
    val postalCodeNum: String,
    @ColumnInfo(name="postal_code_extension")
    val postalCodeExt: String,
    @ColumnInfo(name="postal_code_name")
    val postalCodeName: String
)
