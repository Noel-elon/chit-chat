package com.example.chitchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cometchat.pro.core.AppSettings
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.example.chitchat.utils.APP_ID
import com.example.chitchat.utils.AUTH_KEY
import com.example.chitchat.utils.REGION
import screen.unified.CometChatUnified

class MainActivity : AppCompatActivity() {
    lateinit var nameTv: TextView
    lateinit var uidTv: TextView
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameTv = findViewById(R.id.Name)
        uidTv = findViewById(R.id.Uid)
        loginButton = findViewById(R.id.login_button)

        initCometChat()

        loginButton.setOnClickListener {
            validateAndCreateUser()

        }
    }


    private fun initCometChat() {
        val appSettings = AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(
            REGION
        ).build()

        CometChat.init(this, APP_ID, appSettings, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(p0: String?) {

            }

            override fun onError(p0: CometChatException?) {

            }

        })
    }

    private fun validateAndCreateUser() {
        if (nameTv.text.isEmpty() || uidTv.text.isEmpty()) {
            Toast.makeText(this, "fill all fields please", Toast.LENGTH_SHORT).show()
        } else {

            val user = User()
            user.name = nameTv.text.toString()
            user.uid = uidTv.text.toString()

            CometChat.createUser(user, AUTH_KEY, object : CometChat.CallbackListener<User>() {
                override fun onSuccess(user: User) {
                    login(user.uid)

                }

                override fun onError(e: CometChatException) {
                    Log.e("createUser", e.message!!)
                }
            })
        }
    }

    private fun login(uid: String) {

        if (uid.isEmpty()) {
            Toast.makeText(this, "fill all fields please", Toast.LENGTH_SHORT).show()
        } else {
            CometChat.login(uid, AUTH_KEY, object : CometChat.CallbackListener<User>() {
                override fun onSuccess(p0: User?) {
                    val intent = Intent(this@MainActivity, CometChatUnified::class.java)
                    startActivity(intent)
                }

                override fun onError(p0: CometChatException?) {
                    Log.d(
                        com.example.chitchat.utils.TAG,
                        "Login failed with exception: " + p0?.message
                    )
                }

            })
        }

    }
}