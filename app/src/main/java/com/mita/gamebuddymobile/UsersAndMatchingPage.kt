package com.mita.gamebuddymobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mita.gamebuddymobile.api.UserDataClass

class UsersAndMatchingPage : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var randomUserRecyclerView: RecyclerView
    private lateinit var randomUserDataList: ArrayList<RandomUserDataClass>
    private lateinit var userAdapter: RandomUserAdapterClass
    private lateinit var recyclerView : RecyclerView
    private lateinit var userList : ArrayList<UserDataClass>
    private lateinit var UserAdapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_and_matching_page)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bottom_users -> {
                    // No need to navigate to the same activity
                    true
                }

                R.id.bottom_myAccount -> {
                    val intent = Intent(this, AccounSettings::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.bottom_users

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userList = ArrayList()

        userList.add(UserDataClass(R.drawable.baseline_people_24, "Lan2x", "Male", 18, "League of Legends, Valorant"))


        UserAdapter = UserAdapter(userList)
        recyclerView.adapter = UserAdapter



        randomUserRecyclerView = findViewById(R.id.RandomUserRecycleView)
        randomUserRecyclerView.setHasFixedSize(true)
        randomUserRecyclerView.layoutManager = LinearLayoutManager(this)

        randomUserDataList = ArrayList()

        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "Lan2x", "Male", 18))
        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "Rhalf", "Male", 18))
        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "R2x", "Male", 18))
        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "Ken", "Male", 18))
        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "Tristan", "Male", 18))
        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "Cy", "Male", 18))
        randomUserDataList.add(RandomUserDataClass(R.drawable.baseline_people_24, "Notnot", "Male", 18))


        userAdapter = RandomUserAdapterClass(randomUserDataList)
        randomUserRecyclerView.adapter = userAdapter
    }
}
