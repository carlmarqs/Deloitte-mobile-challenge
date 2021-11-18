package com.deloitte.wtest.repository

import android.util.Log
import com.deloitte.wtest.database.postalcode.PostalCode
import com.deloitte.wtest.database.postalcode.PostalCodeDao
import com.deloitte.wtest.repository.utils.RetrofitManager
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

class PostalCodeRepository(private val databaseDao: PostalCodeDao) {

    private val retrofit = RetrofitManager.create<PostalCodeApi>()

    interface PostalCodeApi {
        @Streaming
        @GET("codigos_postais.csv")
        suspend fun getFile(): ResponseBody
    }

    suspend fun addPostalCodes() {
        withContext(Dispatchers.IO) {
            try {
                val postalCodeFile = runCatching { retrofit.getFile() }.getOrThrow()
                val br = postalCodeFile.byteStream().bufferedReader()
                val reader = CSVReader(br)
                var nextLine: Array<String>? = reader.readNext()
                nextLine = reader.readNext() //to make sure it skips the line with the columns name
                while (nextLine != null) {
                    val postalCode = PostalCode(
                        locationCode = nextLine[2],
                        locationName = nextLine[3],
                        postalCodeNum = nextLine[14],
                        postalCodeExt = nextLine[15],
                        postalCodeName = nextLine[16]
                    )
                    databaseDao.addPostalCode(postalCode)
                    nextLine = reader.readNext()
                }
                Log.e("teste", "FINISHED")
            } catch (e: Exception) {
                Log.e("PostalCodeRepository", e.message ?: "An error occurred while getting data from file")
            }
        }
    }
}