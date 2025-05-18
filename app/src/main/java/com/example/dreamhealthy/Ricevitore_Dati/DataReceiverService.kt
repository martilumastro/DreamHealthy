package com.example.dreamhealthy.Ricevitore_Dati

import android.content.Context
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.util.Log

class DataReceiverService : WearableListenerService() {

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        val sharedPrefs = getSharedPreferences("sim_data", Context.MODE_PRIVATE)
        val existing = sharedPrefs.getString("sim_data_list", "[]")
        val jsonArray = JSONArray(existing)

        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val item = event.dataItem
                if (item.uri.path == "/simulated_metrics") {
                    val dataMap = DataMapItem.fromDataItem(item).dataMap

                    val heart = dataMap.getFloat("heart rate")
                    val temp = dataMap.getFloat("temperature")
                    val noise = dataMap.getFloat("noise")
                    val time = dataMap.getFloat("time")
                    val day = getCurrentDayName()

                    val json = JSONObject().apply {
                        put("heart", heart)
                        put("temp", temp)
                        put("noise", noise)
                        put("time", time)
                        put("day", day)
                    }

                    jsonArray.put(json)

                    sharedPrefs.edit().putString("sim_data_list", jsonArray.toString()).apply()
                    Log.d("Receiver", "Ricevuto e salvato: $json")
                }
            }
        }
    }

    private fun getCurrentDayName(): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }
}
