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
import com.example.mohsen.adapter.AdapterDesign
import com.example.mohsen.databinding.Fragment1Binding
import com.example.mohsen.utility.Item
import org.json.JSONArray

class FragmentOne : Fragment() {
    // : تعریف کلاس FragmentOne که از Fragment()  ارث می‌برد.
    private lateinit var binding: Fragment1Binding
    private val items = mutableListOf<Item>()
    //  متغیری است که یک لیست قابل تغییر از اشیاء از نوع Item
    //  را نگه می‌دارد این لیست در طول اجرای برنامه می‌تواند عناصر جدیدی اضافه یا حذف کند.

    private val itemAdapterDesign: AdapterDesign by lazy { AdapterDesign(items, requireContext()) }
    //  تعریف آداپتر برای RecyclerView با استفاده از lazy initialization.
    // این کلمه کلیدی در کاتلین برای ایجاد یک ویژگی (lazy)
    // استفاده می‌شود. به این معنی که مقدار این متغیر تا زمانی که برای اولین بار به آن دسترسی پیدا شود، محاسبه نمی‌شود.

    // مزایای استفاده از by lazy:
    //  بهینه سازی عملکرد با استفاده از by lazy،
    //  نمونه ItemAdapter فقط زمانی ایجاد می‌شود که به آن نیاز باشد.
    //  این باعث می‌شود که برنامه در شروع کار سریع‌تر اجرا شود، به‌خصوص اگر ایجاد ItemAdapter زمان‌بر باشد.
    //  تاخیر در مقداردهی اولیه اگر ایجاد ItemAdapter به داده‌های دیگری وابسته باشد که هنوز در دسترس نیستند
    //  می‌توان از by lazy برای به تعویق انداختن ایجاد آن تا زمانی که تمام داده‌ها آماده باشند، استفاده کرد.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = Fragment1Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {   // : تابع onViewCreated برای انجام تنظیمات بعد از ایجاد ویو.

        super.onViewCreated(view, savedInstanceState)   // : فراخوانی نسخه پایه‌ای از onViewCreated.

        // Initialize the RecyclerView and AdapterDesign
        binding.recyclerOne.layoutManager =
            LinearLayoutManager(requireContext()) // : تنظیم LayoutManager برای RecyclerView.(recyclerOne)
        binding.recyclerOne.adapter = itemAdapterDesign // تنظیم آداپتر برای RecyclerView (recyclerOne).
        loadItemsFromSharedPreferences()
        
        val objectAnimator = ObjectAnimator.ofInt(
            binding.tx1, "textColor",
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
        val sharedPreferences = requireContext().getSharedPreferences("Design", 0)
        val jsonArrayString = sharedPreferences.getString("ITEM", "[]")
        val jsonArray = JSONArray(jsonArrayString)


        // تبدیل JSONArray به لیست آیتم‌ها
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val text1 = jsonObject.getString("text1")
            val text2 = jsonObject.getString("text2")
            items.add(Item(text1, text2, "Design" ))
        }

        // اطلاع‌رسانی به آداپتر که داده‌ها تغییر کرده است
        itemAdapterDesign.notifyDataSetChanged()

    }

}
