package com.mita.gamebuddymobile

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mita.gamebuddymobile.api.ApiService
import com.mita.gamebuddymobile.api.RetrofitClient
import com.mita.gamebuddymobile.api.StartMatchingResponse
import com.mita.gamebuddymobile.api.UserDataClass
import com.mita.gamebuddymobile.api.UserProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log


class UsersAndMatchingPage : AppCompatActivity() {

    companion object {
        private const val TAG = "UsersAndMatchingPage"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var RandomUserRecycleView: RecyclerView
    private lateinit var RandomuserList: ArrayList<RandomUserDataClass>
    private lateinit var userList: ArrayList<UserDataClass>
    private lateinit var RandomUserAdapter: RandomUserAdapterClass
    private lateinit var userAdapter: UserAdapter
    private lateinit var startMatchingButton: Button
    private lateinit var apiService: ApiService // Your Retrofit API service interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_and_matching_page)

        // Initialize Retrofit API service
        apiService = RetrofitClient.apiService

        startMatchingButton = findViewById(R.id.StartMatchingBtn)

        startMatchingButton.setOnClickListener {
            // Retrieve the interest ID of the logged-in user
            getLoggedInUserInterestId { interestId ->
                interestId?.let {
                    startMatching(it)
                } ?: run {
                    Toast.makeText(
                        this@UsersAndMatchingPage,
                        "Failed to retrieve interest ID",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RandomUserRecycleView = findViewById(R.id.RandomUserRecycleView)
        RandomUserRecycleView.setHasFixedSize(true)
        RandomUserRecycleView.layoutManager = LinearLayoutManager(this)

        RandomuserList = ArrayList()

        userList = ArrayList()

        // Call the function to fetch user data from the server
        fetchUserData()

        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        RandomUserAdapter = RandomUserAdapterClass(RandomuserList)
        RandomUserRecycleView.adapter = userAdapter
    }

    private fun fetchUserData() {
        apiService.getUser().enqueue(object : Callback<List<RandomUserDataClass>> {
            override fun onResponse(
                call: Call<List<RandomUserDataClass>>,
                response: Response<List<RandomUserDataClass>>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()
                    users?.let {
                        RandomuserList.addAll(it)
                        RandomUserAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@UsersAndMatchingPage,
                        "Failed to fetch user data from server",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<RandomUserDataClass>>, t: Throwable) {
                Toast.makeText(
                    this@UsersAndMatchingPage,
                    "Error occurred while fetching user data",
                    Toast.LENGTH_SHORT
                ).show()
                t.printStackTrace()
            }
        })
    }

    private fun getLoggedInUserInterestId(callback: (String?) -> Unit) {
        apiService.getUserProfile().enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val userProfile = response.body()
                    val interestId = userProfile?.interestId
                    callback(interestId)
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    private fun startMatching(interestId: String) {
        apiService.startMatching(interestId).enqueue(object : Callback<StartMatchingResponse> {
            override fun onResponse(
                call: Call<StartMatchingResponse>,
                response: Response<StartMatchingResponse>
            ) {
                if (response.isSuccessful) {
                    // Call successful, now fetch users with the same interest
                    val startMatchingResponse = response.body()
                    startMatchingResponse?.let { matchingResponse ->
                        // Extract interest id from startMatchingResponse and pass it to fetchUsersWithSameInterest function
                        val interestsId = matchingResponse.interestsId
                        fetchUsersWithSameInterest(interestsId.toString())
                    }
                } else {
                    // Handle error
                    Toast.makeText(
                        this@UsersAndMatchingPage,
                        "Failed to start matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<StartMatchingResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(
                    this@UsersAndMatchingPage,
                    "Error occurred while starting matching",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun fetchUsersWithSameInterest(interestId: String) {
        apiService.startMatching(interestId).enqueue(object : Callback<StartMatchingResponse> {
            override fun onResponse(
                call: Call<StartMatchingResponse>,
                response: Response<StartMatchingResponse>
            ) {
                if (response.isSuccessful) {
                    val startMatchingResponse = response.body()
                    // Process the response according to your requirements
                } else {
                    // Handle error
                    Toast.makeText(
                        this@UsersAndMatchingPage,
                        "Failed to start matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<StartMatchingResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(
                    this@UsersAndMatchingPage,
                    "Error occurred while starting matching",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}