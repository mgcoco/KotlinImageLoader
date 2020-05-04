package com.cmoney.demo.network

import com.cmoney.demo.model.CellInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class ApiRequest{

    interface OnRequestCallback<T>{

        fun onSuccess(data : T)

        fun onFailure(message: String)
    }

    fun getCellList(callback: OnRequestCallback<ArrayList<CellInfo>>) {
        startRequest("https://jsonplaceholder.typicode.com/photos", callback)
    }

    private inline fun <reified T> startRequest(url: String, callback : OnRequestCallback<T>){
        GlobalScope.launch (Dispatchers.Default){
            try {
                val url = URL(url)
                var result : T? = null
                with(url.openConnection() as HttpURLConnection) {
                    try{
                        result = fromJson<T>(inputStream.bufferedReader().readText())
                    }
                    catch (e : Exception){
                        var exc: String = e.message + "\n"
                        val trace: Array<StackTraceElement> =  e.stackTrace
                        for (traceElement in trace) exc += "\nat $traceElement"
                        callback.onFailure(exc)
                    }
                }

                GlobalScope.launch (Dispatchers.Main){
                    callback.onSuccess(result!!)
                }
            }
            catch (e : Exception){

            }
        }
    }

    private inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object: TypeToken<T>(){}.type)
    }
}