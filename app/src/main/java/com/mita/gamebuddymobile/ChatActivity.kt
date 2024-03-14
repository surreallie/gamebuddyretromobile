package com.mita.gamebuddymobile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mita.gamebuddymobile.api.Message
import com.mita.gamebuddymobile.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var userId: String

    private var receiverRoom: String? = null
    private var senderRoom: String? = null

    private lateinit var username: String
    private lateinit var userIDReceiver: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val conversationId = intent.getStringExtra("ConversationId") ?: ""
        Log.e("conversationId", "conversationId: ${conversationId}")

        username = intent.getStringExtra("username") ?: ""
        userIDReceiver = intent.getStringExtra("userIDReceiver") ?: ""
        userId = getUserIdFromSharedPreferences()

        chatRecyclerView = findViewById(R.id.chatRecycleView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList, userId)


        chatRecyclerView.adapter = messageAdapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        sendButton.setOnClickListener {
            val message = messageBox.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessageToServer(message, conversationId.toInt())
                messageBox.text.clear()
            } else {
                Toast.makeText(applicationContext, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }


        if (conversationId.isNotEmpty())
        {
            loadMessagesFromServer(conversationId)
        }
    }

    private fun loadMessagesFromServer(conversationId: String) {
        val apiService = RetrofitClient.apiService

        apiService.getMessages(conversationId.toInt()).enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    val messages = response.body()
                    if (messages != null) {
                        messageList.addAll(messages)
                        messageAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(applicationContext, "Failed to load messages!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load messages!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendMessageToServer(message: String, conversationId: Int) {
        val apiService = RetrofitClient.apiService
        val messageBody = mapOf(
            "receiverID" to userIDReceiver,
            "body" to message
        )
        apiService.sendMessage(conversationId, messageBody).enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>,
                response: Response<Message>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let{
//                        conversationId = conversationId
                        loadMessagesFromServer(conversationId.toString())
                    }
                } else {
                    Toast.makeText(applicationContext, "Failed to send1 message!", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to send2 message!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserIdFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", "") ?: ""
    }
}
