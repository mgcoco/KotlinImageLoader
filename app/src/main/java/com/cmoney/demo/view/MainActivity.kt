package com.cmoney.demo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.cmoney.demo.Constant
import com.cmoney.demo.R
import com.cmoney.demo.adapter.CellAdapter
import com.cmoney.demo.base.BaseAdapter
import com.cmoney.demo.model.CellInfo
import com.cmoney.demo.network.ApiRequest

class MainActivity : AppCompatActivity() {

    private lateinit var mList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mList = findViewById(R.id.list)
        findViewById<View>(R.id.main_request).setOnClickListener {
            it.visibility = View.GONE
            mList.visibility = View.VISIBLE
            val apiRequest = ApiRequest()
            apiRequest.getCellList(object : ApiRequest.OnRequestCallback<ArrayList<CellInfo>>{
                override fun onSuccess(data : ArrayList<CellInfo>){
                    val adapter = CellAdapter()
                    adapter.addItems(data)
                    adapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener<CellInfo> {
                        override fun onItemClick(position: Int, data: CellInfo){
                            val intent = Intent(applicationContext, CellInfoActivity::class.java).apply {
                                putExtra(Constant.CELL_DETAIL, data)
                            }
                            startActivity(intent)
                        }
                    })
                    mList.adapter = adapter
                }

                override fun onFailure(message : String){
                    println(message)
                }
            })
        }
    }
}
