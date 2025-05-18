package com.example.dreamhealthy.wear.presentation

import android.util.Log
import com.google.android.gms.wearable.*
import org.json.JSONObject

 class DataReceiverService : WearableListenerService()
{
    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for(event in dataEvents)
        {
          if(event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/simulated_metrics")
          {
              val map = DataMapItem.fromDataItem(event.dataItem).dataMap
              val heart = map.getFloat("heart_rate")
              val temp = map.getFloat("temp")
              val noise = map.getFloat("noise")
              val time = map.getFloat("time")

              Log.d("Phone", "Received : HR = $heart , Temp = $temp , Noise=$noise , Time=$time")

              //Save local JSON
             saveLocally(heart,temp,noise,time)
          }
        }

    }

    private fun saveLocally(heart : Float , temp : Float, noise: Float, time : Float)
    {

        val json = JSONObject().apply {
            put("heart" , heart)
            put("temp", temp)
            put("noise" , noise)
            put("time" , time)
        }

        Log.d("Phone", "Saved SimulateData : $json")
    }
}
