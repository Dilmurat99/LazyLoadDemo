package com.uyghar.lazyloaddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.uyghar.lazyloaddemo.model.UserDB
import com.uyghar.lazyloaddemo.model.UserDetail
import com.uyghar.lazyloaddemo.ui.home.UserAdapter
import okhttp3.*
import java.io.IOException
import java.net.URL

class NotificationDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_detail)
        val id = intent.getIntExtra("id", 0)
        getUser(id)
        val root: ConstraintLayout = findViewById(R.id.root)
        Snackbar.make(root,id.toString(),Snackbar.LENGTH_LONG).show()
        Toast.makeText(this, id.toString(),Toast.LENGTH_LONG).show()
    }

    fun getUser(id: Int){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL("http://reqres.in/api/users/$id"))
            .build()
        client.newCall(request).enqueue(
            object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val json_str = response.body?.string()
                    val gson = GsonBuilder().create()
                    val user_data = gson.fromJson(json_str, UserDetail::class.java)
                    Log.i("user_id", user_data.data?.id.toString())
                    runOnUiThread {

                    }

                }

            }
        )
    }
}