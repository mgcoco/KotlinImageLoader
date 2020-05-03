package com.cmoney.demo.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cmoney.demo.Constant
import com.cmoney.demo.R
import com.cmoney.demo.model.CellInfo
import com.cmoney.demo.network.ImageLoader

class CellInfoActivity : AppCompatActivity() {

    private lateinit var mImg : ImageView
    private lateinit var mId : TextView
    private lateinit var mTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cell_info)

        mImg = findViewById(R.id.cell_info_img)
        mTitle = findViewById(R.id.cell_info_title)
        mId = findViewById(R.id.cell_info_id)

        val detail = intent.getParcelableExtra<CellInfo>(Constant.CELL_DETAIL)
        mId.text = "" + detail.id
        mTitle.text = detail.title
        ImageLoader().load(mImg, detail.getConvertedUrl())
    }
}