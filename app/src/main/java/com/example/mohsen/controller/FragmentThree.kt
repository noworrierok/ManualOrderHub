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
import com.example.mohsen.adapter.AdapterDeposit
import com.example.mohsen.databinding.Fragment3Binding
import com.example.mohsen.utility.Item
import org.json.JSONArray

class FragmentThree : Fragment() {

    private lateinit var binding: Fragment3Binding
    private val items = mutableListOf<Item>()
    private val itemAdapter: AdapterDeposit by lazy { AdapterDeposit(items, requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = Fragment3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {   // : تابع onViewCreated برای انجام تنظیمات بعد از ایجاد ویو.

        super.onViewCreated(view, savedInstanceState)   // : فراخوانی نسخه پایه‌ای از onViewCreated.

        binding.recyclerThree.layoutManager =
            LinearLayoutManager(requireContext()) // : تنظیم LayoutManager برای RecyclerView.(recyclerOne)
        binding.recyclerThree.adapter = itemAdapter // تنظیم آداپتر برای RecyclerView (recyclerOne).
        loadItemsFromSharedPreferences()

        val objectAnimator = ObjectAnimator.ofInt(
            binding.tx3, "textColor",
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
        val sharedPreferences = requireContext().getSharedPreferences("Deposit", 0)
        val jsonArrayString = sharedPreferences.getString("ITEMS", "[]")
        val jsonArray = JSONArray(jsonArrayString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val text1 = jsonObject.getString("text1")
            val text2 = jsonObject.getString("text2")
            items.add(Item(text1, text2, "Deposit"))
        }

        itemAdapter.notifyDataSetChanged()
    }
}



