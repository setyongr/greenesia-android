package com.setyongr.greenesia.views

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.setyongr.greenesia.R
import android.graphics.Bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_take_event_detail.*


class TakeEventDetailActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_event_detail)

        val id= intent.getStringExtra("id")
        try {
            val bitmap = encodeAsBitmap(id)
            imageView.setImageBitmap(bitmap)
        }catch (e: WriterException) {
            e.printStackTrace()
        }

    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String): Bitmap? {
        val WIDTH = 400
        val HEIGHT = 400
        val WHITE = 0xFFFFFFFF
        val BLACK = 0xFF000000
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null)
        } catch (iae: IllegalArgumentException) {
            // Unsupported format
            return null
        }

        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0..h - 1) {
            val offset = y * w
            for (x in 0..w - 1) {
                pixels[offset + x] = if (result.get(x, y)) BLACK.toInt() else WHITE.toInt()
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }
}