package com.cmoney.demo.network

import com.cmoney.demo.model.CellInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class ApiRequest{

    interface OnRequestCallback<T>{

        fun onSuccess(data : T)

        fun onFailure(message: String)
    }

    fun <T> getCellList(callback: OnRequestCallback<T>) {
        startRequest("https://jsonplaceholder.typicode.com/photos", callback)
    }

    private fun <T> startRequest(url: String, callback : OnRequestCallback<T>){
        val executor = Executors.newFixedThreadPool(1)
        val worker = Runnable {
            val url = URL(url)

            with(url.openConnection() as HttpURLConnection) {
                try{
                    val itemType = object : TypeToken<List<CellInfo>>() {}.type
                    callback.onSuccess(Gson().fromJson(inputStream.bufferedReader().readText(), itemType))
                }
                catch (e : Exception){
                    var exc: String = e.message + "\n"
                    val trace: Array<StackTraceElement> =
                        e.getStackTrace()
                    for (traceElement in trace) exc += "\nat $traceElement"
                    callback.onFailure(exc)
                }

            }
        }
        executor.execute(worker)
        executor.shutdown()
        while (!executor.isTerminated) {
        }
    }
}