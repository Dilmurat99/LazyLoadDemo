package com.uyghar.lazyloaddemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.uyghar.lazyloaddemo.R
import com.uyghar.lazyloaddemo.databinding.FragmentHomeBinding
import com.uyghar.lazyloaddemo.model.User
import com.uyghar.lazyloaddemo.model.UserDB
import okhttp3.*
import java.io.IOException
import java.net.URL

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var page = 1
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.scrollView.setOnScrollChangeListener(object: NestedScrollView.OnScrollChangeListener
        {
            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY > oldScrollY)
                {
                    if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                        loadMore()
                    }
                }
            }

        })
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        getUser()
        return root
    }

    fun loadMore() {
        page += 1
        getUser()
    }

    fun getUser(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL("http://reqres.in/api/users?page=$page"))
            .build()
        client.newCall(request).enqueue(
            object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val json_str = response.body?.string()
                    val gson = GsonBuilder().create()
                    val user_data = gson.fromJson(json_str, UserDB::class.java)
                    activity?.runOnUiThread {
                        binding.recyclerView.adapter = UserAdapter(user_data)

                    }

                }

            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}