package com.setyongr.greenesia.views

import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.os.Bundle
import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.zxing.Result
import com.setyongr.greenesia.MainApp
import com.setyongr.greenesia.data.DataManager
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {


    private var mScannerView: ZXingScannerView? = null
    @Inject lateinit var dataManager: DataManager
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        MainApp.greenesiaComponent.inject(this)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        Log.v("QR", rawResult.getText()); // Prints scan results
        Log.v("QR", rawResult.getBarcodeFormat().toString()) // Prints the scan format (qrcode, pdf417 etc.)
        dataManager.acceptAttendant(rawResult.text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            AlertDialog.Builder(this).setTitle("Sukses").setMessage("Tiket Valid").show()
                            mScannerView!!.resumeCameraPreview(this)
                        },
                        {
                            AlertDialog.Builder(this).setTitle("Error").setMessage("Error").show()
                            mScannerView!!.resumeCameraPreview(this)
                        }
                )
    }

}