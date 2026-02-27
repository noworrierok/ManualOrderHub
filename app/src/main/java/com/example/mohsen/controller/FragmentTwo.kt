package com.example.mohsen.controller

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mohsen.adapter.AdapterAccept
import com.example.mohsen.databinding.Fragment2Binding
import com.example.mohsen.utility.Item
import org.json.JSONArray

class FragmentTwo : Fragment() {

    private lateinit var binding: Fragment2Binding
    private val items = mutableListOf<Item>()
    private val itemAdapter: AdapterAccept by lazy { AdapterAccept(items, requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = Fragment2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {   // : تابع onViewCreated برای انجام تنظیمات بعد از ایجاد ویو.

        super.onViewCreated(view, savedInstanceState)   // : فراخوانی نسخه پایه‌ای از onViewCreated.

        binding.recyclerTwo.layoutManager =
            LinearLayoutManager(requireContext()) // : تنظیم LayoutManager برای RecyclerView.(recyclerOne)
        binding.recyclerTwo.adapter = itemAdapter // تنظیم آداپتر برای RecyclerView (recyclerOne).

        loadItemsFromSharedPreferences()
        val objectAnimator = ObjectAnimator.ofInt(
            binding.tx2, "textColor",
            Color.parseColor("#FFFFFFFF"),
            Color.TRANSPARENT,  // بازگشت به رنگ سفید
            Color.parseColor("#FFFFFFFF")  // بازگشت به رنگ سفید
        )
        objectAnimator.duration = 600  // مدت زمان انیمیشن (به میلی‌ثانیه)
        objectAnimator.setEvaluator(ArgbEvaluator())
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.repeatCount = 1
        objectAnimator.start()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadItemsFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("Accept", 0)
        val jsonArrayString = sharedPreferences.getString("ITEMS", "[]")
        val jsonArray = JSONArray(jsonArrayString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val text1 = jsonObject.getString("text1")
            val text2 = jsonObject.getString("text2")
            items.add(Item(text1, text2, "Accept"))
        }

        itemAdapter.notifyDataSetChanged()
    }
}



