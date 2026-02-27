package com.example.mohsen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mohsen.R
import com.example.mohsen.utility.Item
import org.json.JSONArray
import org.json.JSONObject

class AdapterDesign(private val items: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<AdapterDesign.ItemViewHolder>() {
    // : تعریف کلاس AdapterDesign که از RecyclerView.AdapterDesign  ارث می‌برد و ViewHolder داخلی خود را دارد.

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemViewHolder {  //  : تابعی برای ایجاد ViewHolder جدید.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
        // : پر کردن (inflate) نمای آیتم.
        //- `return ItemViewHolder(view)`: بازگشت یک نمونه از ViewHolder.
    }

    override fun getItemCount(): Int {  //  : تابعی برای بازگرداندن تعداد آیتم‌ها.
        return items.count()  //  : تعداد آیتم‌های موجود در لیست.

    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int,
    ) {  // : تابعی برای اتصال داده‌ها به ViewHolder.

        val item = items[position]  //  : دریافت آیتم در موقعیت مشخص شده.
        holder.textView1.text = item.text1  //   تنظیم متن TextView1  با داده‌های آیتم.
        holder.textView2.text = item.text2  //   تنظیم متن TextView2  با داده‌های آیتم.

        holder.itemAccept.setOnClickListener {

            saveItemToOtherSharedPref(item)

            removeItemFromSharedPreferences(item)
            items.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.itemDismiss.setOnClickListener{

            removeItemFromSharedPreferences(item)
            items.removeAt(position)
            notifyItemRemoved(position)

        }

    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // : تعریف کلاس ItemViewHolder داخلی برای نگه‌داری نمای آیتم‌ها.
        val textView1: TextView =
            itemView.findViewById(R.id.itemText1)  //  : دسترسی به TextView اول.
        val textView2: TextView =
            itemView.findViewById(R.id.itemText2)  //  : دسترسی به TextView دوم.

        val itemAccept: Button = itemView.findViewById(R.id.itemAccept)
        val itemDismiss: Button = itemView.findViewById(R.id.itemDismiss)

//        fun bindCategory(item: Item) {
//            textView1.text = item.text1
//            editText2.text = item.text2
//        }
    }

    private fun saveItemToOtherSharedPref(item: Item) {
        val sharedPreferences = context.getSharedPreferences("Accept", 0)
        val editor = sharedPreferences?.edit()

        val jsonArray = JSONArray(sharedPreferences?.getString("ITEMS", "[]"))  // بازیابی داده‌های قبلی به عنوان JSONArray


        val jsonObject = JSONObject().apply { // ایجاد یک JSONObject برای آیتم جدید
            put("text1", item.text1)
            put("text2", item.text2)
            put("source", "Accept")
        }
        jsonArray.put(jsonObject) // اضافه کردن آیتم جدید به JSONArray
        editor?.putString("ITEMS", jsonArray.toString()) // ذخیره JSONArray به عنوان String در SharedPreferences
        editor?.apply()
        Toast.makeText(context, "${item.text1}  به بخش تایید منتقل شد", Toast.LENGTH_SHORT)
            .show()

    }

    private fun removeItemFromSharedPreferences(item: Item) {
        val sharedPreferences = context.getSharedPreferences("Design", 0)
        val jsonArrayString = sharedPreferences.getString("ITEM", "[]")
        val jsonArray = JSONArray(jsonArrayString)

        val newJsonArray = JSONArray()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val text1 = jsonObject.getString("text1")
            val text2 = jsonObject.getString("text2")

            // اگر این آیتم با آیتمی که می‌خواهیم حذف کنیم مطابقت نداشت، آن را در newJsonArray قرار دهیم
            if (text1 != item.text1 || text2 != item.text2) {
                newJsonArray.put(jsonObject)
            }
        }

        // ذخیره‌ی newJsonArray به روز شده در SharedPreferences
        sharedPreferences.edit()
            .putString("ITEM", newJsonArray.toString())
            .apply()
    }



//    fun addItem(item: Item) {   // : تابعی برای افزودن آیتم جدید به لیست.
//        items.add(item)   // : افزودن آیتم به لیست.
//
//        Log.d("addItem", "$this" )
//        notifyItemInserted(items.size - 1)  //  : اطلاع دادن به RecyclerView برای به‌روزرسانی و نمایش آیتم جدید.
//    }


}