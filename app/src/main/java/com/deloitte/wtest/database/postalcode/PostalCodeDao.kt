package com.deloitte.wtest.database.postalcode

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostalCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPostalCode(postalCode: PostalCode)

    @Query("Select * from PostalCode order by postal_code_number, postal_code_extension, postal_code_name")
    fun getAllPostalCodes(): List<PostalCode>

    @Query("Select * from PostalCode WHERE " +
            "postal_code_number in (:filterOption) OR " +
            "postal_code_extension in (:filterOption) OR " +
            "lower(postal_code_name) in (:filterOption) " +
            "order by postal_code_number, postal_code_extension, postal_code_name")
    fun filterPostalCode(filterOption: List<String>): List<PostalCode>

    //MISSES IN (....) probably change everything to string to be easier

}