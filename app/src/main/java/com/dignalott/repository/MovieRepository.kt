package com.dignalott.repository

import android.content.Context
import com.dignalott.models.Movies
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

class MovieRepository {

     fun  getMoviesList(context: Context,fileName:String): Movies?
    {
        try {
            val jsonString = getJSONFromAssets(context,fileName)!!
            return Gson().fromJson(jsonString, Movies::class.java)
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }
         return null
    }
    fun getJSONFromAssets(context : Context,fileName:String): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = context.assets.open("CONTENTLISTINGPAGE-PAGE1.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}