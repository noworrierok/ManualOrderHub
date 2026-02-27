package com.example.mohsen.controller
// تعیین بسته‌ای که این فایل در آن قرار دارد.

import android.R.color.transparent
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mohsen.R
import com.example.mohsen.databinding.DialogBinding
import com.example.mohsen.databinding.MainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainBinding
    private var inDesign = true
    private var design = true
    private var accept = true
    private var deposit = true
    private var print = true
    private var delivery = true

//    private lateinit var itemAdapter: AdapterDesign
    // ERROR FIX lateinit property itemAdapter has not been initialized


    @SuppressLint("InflateParams", "ObjectAnimatorBinding", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (savedInstanceState == null) {
            // `if (savedInstanceState == null)`: بررسی می‌کند که آیا اکتیویتی برای اولین بار ایجاد شده است
            // (savedInstanceState خالی است).
            managerFragment(FragmentOne())
        }

        val fadeAnimation2 = AnimationUtils.loadAnimation(this, R.anim.fade)
        binding.imgView.startAnimation(fadeAnimation2)


        binding.btnStart.setOnClickListener {
            showDialog()
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btn1 -> {
                    managerFragment(FragmentOne())
                    true
                }

                R.id.btn2 -> {
                    managerFragment(FragmentTwo())
                    true
                }

                R.id.btn3 -> {
                    managerFragment(FragmentThree())
                    true
                }

                R.id.btn4 -> {
                    managerFragment(FragmentFour())
                    true
                }

                R.id.btn5 -> {
                    managerFragment(FragmentFive())
                    true
                }

                else -> {
                    false
                }
            }
        }


    }


    private fun managerFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        // - `supportFragmentManager.beginTransaction()`: شروع یک تراکنش Fragment.
        //- `replace(R.id.fragment_container, MyFragment())`: جایگزینی fragment_container با MyFragment.
        //- `commit()`: اعمال تغییرات تراکنش.

    }


    @SuppressLint("InflateParams", "MissingInflatedId", "NewApi")
    private fun showDialog() {

//        val bottomSheetDialog = BottomSheetDialog(this)
        val dialog = Dialog(this)  // ایجاد یک نمونه از Dialog.
        val dialogView = DialogBinding.inflate(layoutInflater)
        // : پر کردن (inflate) نمای Dialog از layout.


        dialogView.buttonSave.setOnClickListener {
            val editText1 = dialogView.editText1.text.toString().trim()
            // دریافت متن از `editText1`.
            val editText2 = dialogView.editText2.text.toString()
            // دریافت متن از `editText2`.


            if (!TextUtils.isEmpty(editText1)) {
                // !TextUtils.isEmpty() =  تابع برسی خالی نبودن یک رشته
                // TextUtils.isEmpty() =   تابع برسی خالی بودن یک رشته
                saveToSharedPreferences(editText1, editText2)
                managerFragment(FragmentOne())
                binding.bottomNavigation.menu.getItem(4).isChecked = true

                // Add new item to RecyclerView in the fragment
//                val fragment =
//                    supportFragmentManager.findFragmentById(R.id.container) as FragmentOne
//                fragment.addItem(Item(editText, textView))
                // پیدا کردن fragment موجود و تبدیل آن به FragmentOne.
                //- fragment.addItem(Item(editText1, editText2)): افزودن آیتم جدید به RecyclerView درون fragment.
                dialog.dismiss()

            } else {
                Toast.makeText(this, "نام مشتری را تایپ کنید", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setContentView(dialogView.root)
        dialog.window?.setBackgroundDrawableResource(transparent)
        //  تنظیم نمای محتوا برای Dialog.
        dialog.show()

    }

    private fun saveToSharedPreferences(text1: String, text2: String) {
        val sharedPreferences1 = getSharedPreferences("Design", 0)
        val sharedPreferences2 = getSharedPreferences("Accept", 0)
        val sharedPreferences3 = getSharedPreferences("Deposit", 0)
        val sharedPreferences4 = getSharedPreferences("Print", 0)
        val sharedPreferences5 = getSharedPreferences("Delivery", 0)

        val editor = sharedPreferences1.edit()
        // بازیابی داده‌های قبلی به عنوان JSONArray
        val jsonArray1 = JSONArray(sharedPreferences1.getString("ITEM", "[]"))
        val jsonArray2 = JSONArray(sharedPreferences2.getString("ITEMS", "[]"))
        val jsonArray3 = JSONArray(sharedPreferences3.getString("ITEMS", "[]"))
        val jsonArray4 = JSONArray(sharedPreferences4.getString("ITEMS", "[]"))
        val jsonArray5 = JSONArray(sharedPreferences5.getString("ITEMS", "[]"))

        for (i in 0 until jsonArray1.length()) {
            val jsonObject1 = jsonArray1.getJSONObject(i)
            val string1 = jsonObject1.getString("text1")
            // داده ای که برات ارسال کردم با دادهات برابر که نیستش  درسته
            if (text1 != string1) { // داده ای که برات ارسال کردم با دادهات برابر که نیستش  درسته
                design = true
            } else {
                design = false
                break
            }
        }

        for (i in 0 until jsonArray2.length()) {
            val jsonObject2 = jsonArray2.getJSONObject(i)
            val string2 = jsonObject2.getString("text1")
            if (text1 != string2) { // داده ای که برات ارسال کردم با دادهات برابر که نیستش  درسته
                accept = true
            } else {
                accept = false
                break
            }
        }
        for (i in 0 until jsonArray3.length()) {
            val jsonObject3 = jsonArray3.getJSONObject(i)
            val string3 = jsonObject3.getString("text1")
            if (text1 != string3) { // داده ای که برات ارسال کردم با دادهات برابر که نیستش  درسته
                deposit = true
            } else {
                deposit = false
                break
            }
        }
        for (i in 0 until jsonArray4.length()) {
            val jsonObject4 = jsonArray4.getJSONObject(i)
            val string4 = jsonObject4.getString("text1")
            if (text1 != string4) { // داده ای که برات ارسال کردم با دادهات برابر که نیستش  درسته
                print = true
            } else {
                print = false
                break
            }
        }
        for (i in 0 until jsonArray5.length()) {
            val jsonObject5 = jsonArray5.getJSONObject(i)
            val string5 = jsonObject5.getString("text1")
            if (text1 != string5) { // داده ای که برات ارسال کردم با دادهات برابر که نیستش  درسته
                delivery = true
            } else {
                delivery = false
                break
            }
        }

        if (design && accept && deposit && print && delivery) {
            inDesign = true
        } else {
            inDesign = false
            if (!design) {
                Toast.makeText(this, "ایتم در در بخش طراحی موجود است", Toast.LENGTH_LONG).show()
                design = true
            } else {
                if (!accept) {
                    Toast.makeText(this, "ایتم در بخش تایید موجود است", Toast.LENGTH_LONG).show()
                    accept = true
                } else {
                    if (!deposit) {
                        Toast.makeText(this, "ایتم در بخش واریز موجود است", Toast.LENGTH_LONG).show()
                        deposit = true
                    }else{
                        if (!print){
                            Toast.makeText(this, "ایتم در بخش چاپ موجود است", Toast.LENGTH_LONG).show()
                            print = true
                        }else{
                            if (!this.delivery) {
                                Toast.makeText(this, "ایتم در بخش تحویل موجود است ", Toast.LENGTH_LONG).show()
                                delivery = true
                            }
                        }
                    }

                }
            }
        }

        if (!sharedPreferences1.contains("ITEM")) {

            val jsonObject = JSONObject().apply { // ایجاد یک JSONObject برای آیتم جدید
                put("text1", text1)
                put("text2", text2)
                put("source", "Design")
            }
            jsonArray1.put(jsonObject) // اضافه کردن آیتم جدید به JSONArray
            editor.putString(
                "ITEM",
                jsonArray1.toString()
            ) // ذخیره JSONArray به عنوان String در SharedPreferences
            editor.apply()

        } else {

            if (this.inDesign) {
                val jsonObject = JSONObject().apply { // ایجاد یک JSONObject برای آیتم جدید
                    put("text1", text1)
                    put("text2", text2)
                    put("source", "Design")
                }
                jsonArray1.put(jsonObject) // اضافه کردن آیتم جدید به JSONArray
                editor.putString(
                    "ITEM",
                    jsonArray1.toString()
                ) // ذخیره JSONArray به عنوان String در SharedPreferences
                editor.apply()

            }

        }
    }

}


//    @SuppressLint("MutatingSharedPrefs")
//    private fun saveToSharedPreferences(text1: String, text2: String) {
//        val sharedPreferences = getSharedPreferences("Pref", 0) // : دریافت SharedPreferences با نام `Pref`.
//        val editor = sharedPreferences!!.edit() // دریافت Editor برای SharedPreferences
//        val existingItems =
//            sharedPreferences.getStringSet("ITEMS", mutableSetOf())?.toMutableSet()  // دریافت لیست آیتم‌های موجود با کلید ITEMS
//            //چرا از toMutableSet() استفاده می‌شود؟
//            // نوع داده Set که از SharedPreferences برگردانده می‌شود :
//            // وقتی که داده‌ها از SharedPreferences برگردانده می‌شوند،
//            //  به صورت یک Set غیر قابل تغییر (Set<String>) هستند.
//            // این بدان معناست که نمی‌توانید مستقیماً به این Set آیتم اضافه یا حذف کنید
//            // قابلیت تغییر (Mutability):
//            // برای اینکه بتوانید آیتم‌های جدیدی به Set اضافه کنید،
//            // نیاز دارید که آن را به یک MutableSet تبدیل کنید.
//            // این کار با استفاده از toMutableSet() انجام می‌شود
//            // نتیجه نهایی:
//            // با استفاده از این کد، مطمئن می‌شوید که همیشه یک MutableSet
//            // غیر نال در دست دارید که می‌توانید آیتم‌های جدیدی به آن اضافه کنید
//            // و سپس آن را دوباره در SharedPreferences ذخیره کنید.
//
//        existingItems?.add("$text1,$text2") // افزودن آیتم جدید به لیست قبلی
//        editor.putStringSet("ITEMS", existingItems) // ذخیره مجموعه آیتم‌ها در SharedPreferences با کلید ITEMS
//        editor.apply() // اعمال تغییرات در SharedPreferences
//    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun data(dataText: TextView) {
//        val datePicker =    // دریافت تاریخ شمسی
//        val year = datePicker.year
//        val month = datePicker.month + 1
//        val day = datePicker.dayOfWeek
//
//        val currentTime = LocalTime.now()    // دریافت زمان فعلی
//        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//        val formattedTime = currentTime.format(timeFormatter)
//
//        val formattedDateTime = String.format(  // ساخت فرمت تاریخ و زمان شمسی
//            "%04d-%02d-%02d %s",
//            year, month, day, formattedTime
//        )
//        Log.d("time", formattedDateTime)
//
//        dataText.text = formattedDateTime
//    }






