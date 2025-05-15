package com.example.clientserverpaa.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientserverpaa.utilities.Course
import com.example.clientserverpaa.utilities.CourseAdapter
import com.example.clientserverpaa.utilities.HistoryAdapter
import com.example.clientserverpaa.utilities.HistoryItem
import com.example.clientserverpaa.R
import com.example.clientserverpaa.net.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerViewCourses: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchView: SearchView
    private lateinit var noResultsPlaceholder: LinearLayout
    private lateinit var errorPlaceholder: LinearLayout
    private lateinit var retryButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var clearHistoryButton: Button

    private var lastQuery: String? = null
    private var allCourses: List<Course> = listOf()
    private val searchHandler = android.os.Handler()
    private var searchRunnable: Runnable? = null

    private val sharedPrefs by lazy {
        getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        searchView = findViewById(R.id.searchView)
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        noResultsPlaceholder = findViewById(R.id.noResultsPlaceholder)
        errorPlaceholder = findViewById(R.id.errorPlaceholder)
        retryButton = findViewById(R.id.retryButton)
        progressBar = findViewById(R.id.progressBar)
        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        clearHistoryButton = findViewById(R.id.clearHistoryButton)

        showLoading()

        searchView.queryHint = "Поиск курсов..."
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val closeButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeButton.visibility = View.GONE

        searchIcon.setOnClickListener {
            searchView.onActionViewExpanded()
            closeButton.visibility = View.GONE
        }

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showSearchHistory()
            } else {
                hideSearchHistory()
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchHandler.removeCallbacks(searchRunnable ?: Runnable {})
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    performSearch(newText)
                }
                searchHandler.postDelayed(searchRunnable!!, 2000)
                return true
            }
        })

        recyclerViewCourses.layoutManager = LinearLayoutManager(this)
        recyclerViewCourses.adapter = CourseAdapter(listOf()) { course ->
            Toast.makeText(this@SearchActivity, "Clicked: ${course.title}", Toast.LENGTH_SHORT).show()
        }

        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        clearHistoryButton.setOnClickListener {
            clearSearchHistory()
        }

        bottomNavigationView.selectedItemId = R.id.navigation_search
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> true
                R.id.navigation_my_courses -> {
                    startActivity(Intent(this, MyCoursesActivity::class.java))
                    true
                }
                R.id.navigation_schedule -> {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    true
                }
                else -> false
            }
        }

        retryButton.setOnClickListener {
            performSearch(lastQuery)
        }

        performSearch(null)
    }

    private fun filterCourses(query: String?) {
        val adapter = recyclerViewCourses.adapter as CourseAdapter
        if (query.isNullOrEmpty()) {
            adapter.updateData(allCourses)
            showResults()
        } else {
            val filteredCourses = allCourses.filter {
                it.title.contains(query, ignoreCase = true)
            }
            if (filteredCourses.isEmpty()) {
                showNoResultsPlaceholder()
            } else {
                adapter.updateData(filteredCourses)
                showResults()
            }
        }
    }

    private fun performSearch(query: String?) {
        Toast.makeText(this, "Поиск по запросу: $query", Toast.LENGTH_SHORT).show()
        lastQuery = query
        saveSearchQuery(query)
        searchCourses(query)
    }

    private fun searchCourses(query: String?) {
        showLoading()
        searchHandler.postDelayed({
            val api = RetrofitClient.api
            api.getCourses().enqueue(object : Callback<List<Course>> {
                override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        allCourses = response.body()!!
                        filterCourses(query)
                    } else {
                        showNoResultsPlaceholder()
                    }
                }

                override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                    showErrorPlaceholder()
                    Toast.makeText(this@SearchActivity, "Ошибка подключения: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }, 500)
    }

    private fun showLoading() {
        recyclerViewCourses.visibility = View.GONE
        historyRecyclerView.visibility = View.GONE
        clearHistoryButton.visibility = View.GONE
        noResultsPlaceholder.visibility = View.GONE
        errorPlaceholder.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showResults() {
        recyclerViewCourses.visibility = View.VISIBLE
        historyRecyclerView.visibility = View.GONE
        clearHistoryButton.visibility = View.GONE
        noResultsPlaceholder.visibility = View.GONE
        errorPlaceholder.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun showNoResultsPlaceholder() {
        recyclerViewCourses.visibility = View.GONE
        historyRecyclerView.visibility = View.GONE
        clearHistoryButton.visibility = View.GONE
        progressBar.visibility = View.GONE
        noResultsPlaceholder.visibility = View.VISIBLE
        errorPlaceholder.visibility = View.GONE
    }

    private fun showErrorPlaceholder() {
        recyclerViewCourses.visibility = View.GONE
        historyRecyclerView.visibility = View.GONE
        clearHistoryButton.visibility = View.GONE
        progressBar.visibility = View.GONE
        noResultsPlaceholder.visibility = View.GONE
        errorPlaceholder.visibility = View.VISIBLE
    }

    private fun saveSearchQuery(query: String?) {
        if (query.isNullOrBlank()) return
        val history = getSearchHistory().toMutableList()
        history.remove(query)
        history.add(0, query)
        if (history.size > 10) history.removeAt(history.lastIndex)
        sharedPrefs.edit().putStringSet("search_history", history.toSet()).apply()
    }

    private fun getSearchHistory(): List<String> {
        return sharedPrefs.getStringSet("search_history", emptySet())?.toList()?.sortedByDescending { it } ?: emptyList()
    }

    private fun showSearchHistory() {
        val history = getSearchHistory()
        if (history.isEmpty()) {
            historyRecyclerView.visibility = View.GONE
            clearHistoryButton.visibility = View.GONE
            return
        }
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = HistoryAdapter(
            history.map { HistoryItem(it, System.currentTimeMillis()) }.toMutableList()
        ) { selectedQuery ->
            searchView.setQuery(selectedQuery, true)
        }
        historyRecyclerView.visibility = View.VISIBLE
        clearHistoryButton.visibility = View.VISIBLE
    }

    private fun hideSearchHistory() {
        historyRecyclerView.visibility = View.GONE
        clearHistoryButton.visibility = View.GONE
    }

    private fun clearSearchHistory() {
        sharedPrefs.edit().remove("search_history").apply()
        showSearchHistory()
    }
}