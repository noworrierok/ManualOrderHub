package com.example.mohsen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mohsen.R
import com.example.mohsen.utility.Item
import org.json.JSONArray

class AdapterDelivery (private val items5 : MutableList<Item>, private val context: Context) : RecyclerView.Adapter<AdapterDelivery.ItemsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent , false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items5.count()
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = items5[position]
        holder.itemText1.text = item.text1
        holder.itemText2.text = item.text2

        holder.btnAccept.setOnClickListener{

//            saveItemToOtherSharedPref(item)

//            removeItemFromSharedPreferences(item)
//            items5.removeAt(position)
//            notifyItemRemoved(position)
        }

        holder.btnDismiss.setOnClickListener{

            removeItemFromSharedPreferences(item)
            items5.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    inner class ItemsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemText1 : TextView = itemView.findViewById(R.id.itemText1)
        val itemText2 : TextView = itemView.findViewById(R.id.itemText2)
        val btnAccept : Button = itemView.findViewById(R.id.itemAccept)
        val btnDismiss : Button = itemView.findViewById(R.id.itemDismiss)
    }

//    private fun saveItemToOtherSharedPref(item: Item) {
//        val sharedPreferences = context.getSharedPreferences("Delivery", 0)
//        val editor = sharedPreferences?.edit()
//
//        val jsonArray = JSONArray(sharedPreferences?.getString("ITEMS", "[]"))  // بازیابی داده‌های قبلی به عنوان JSONArray
//        val jsonObject = JSONObject().apply { // ایجاد یک JSONObject برای آیتم جدید
//            put("text1", item.text1)
//            put("text2", item.text2)
//            put("source", "Delivery")
//        }
//        jsonArray.put(jsonObject) // اضافه کردن آیتم جدید به JSONArray
//        editor?.putString("ITEMS", jsonArray.toString()) // ذخیره JSONArray به عنوان String در SharedPreferences
//        editor?.apply()
//        Toast.makeText(context, "${item.text1}  به بخش چاپ منتقل شد", Toast.LENGTH_SHORT)
//            .show()
//    }
    private fun removeItemFromSharedPreferences(item: Item) {
        val sharedPreferences = context.getSharedPreferences("Delivery", 0)
        val jsonArrayString = sharedPreferences.getString("ITEMS", "[]")
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
            .putString("ITEMS", newJsonArray.toString())
            .apply()
    }
}