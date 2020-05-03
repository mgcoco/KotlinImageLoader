package com.cmoney.demo.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.cmoney.demo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageLoader {

    fun load(imageView: ImageView, url : String){
        var bitmap: Bitmap? = null
        val widthMeasureSpec: Int =
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec: Int =
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        imageView.measure(widthMeasureSpec, heightMeasureSpec)
        imageView.tag = url

        GlobalScope.launch {
            try {
                try {
                    val imageUrl = URL(url)
                    val conn: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
                    conn.setRequestProperty("User-Agent", "chrome")
                    conn.connectTimeout = 10000
                    conn.readTimeout = 10000
                    conn.instanceFollowRedirects = true

                    val inputStream: InputStream
                    val status: Int = conn.responseCode
                    if(status != HttpURLConnection.HTTP_OK) {
                        launch(Dispatchers.Main) {
                            imageView.setImageResource(R.drawable.ic_launcher_background)
                        }
                    }
                    else {
                        inputStream = conn.getInputStream()
                        if(url == imageView.tag){
                            bitmap = decodeInputStream(inputStream, imageView.measuredWidth, imageView.measuredHeight)
                            launch(Dispatchers.Main) {
                                imageView.setImageBitmap(bitmap)
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    null
                }
            }
            catch (e : Exception){

            }
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }


        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun decodeInputStream(input: InputStream, reqWidth: Int, reqHeight: Int): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {

            val outputStream = ByteArrayOutputStream()
            input.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            val byteArray = outputStream.toByteArray()

            inJustDecodeBounds = true

            BitmapFactory.decodeByteArray(byteArray, 0 , byteArray.size, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            return BitmapFactory.decodeByteArray(byteArray, 0 , byteArray.size, this)!!
        }
    }
}