package com.example.skopal.foodme.layouts.scanner


import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import android.view.SurfaceView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.SurfaceHolder

class Scanner : Fragment() {
    companion object {
        @JvmStatic
        val REQUEST_CAMERA_PERMISSION_ID = 1001
    }

    var cameraView: SurfaceView? = null
    var textView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_scanner, container, false)
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_scanner))
        cameraView = view.findViewById(R.id.scanner_surface_view)
        textView = view.findViewById(R.id.scanner_text_view)

        initCamera((activity as MainActivity).baseContext)

        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_CAMERA_PERMISSION_ID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission((activity as MainActivity).baseContext, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        return
                    }
                    try {
                        initCamera((activity as MainActivity).baseContext)
                        //cameraSource?.start(cameraView?.holder)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun initCamera(context: Context) {
        val textRecognizer = TextRecognizer.Builder(context).build()
        if (!textRecognizer.isOperational) {
            Log.w("MainActivity", "Detector dependencies are not yet available")
        } else {

            val cameraSource = CameraSource.Builder(context, textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build()

            cameraView!!.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(surfaceHolder: SurfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            requestPermissions(
                                    arrayOf(Manifest.permission.CAMERA),
                                    REQUEST_CAMERA_PERMISSION_ID)
                            return
                        }
                        cameraSource.start(cameraView?.holder)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

                }

                override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                    cameraSource.stop()
                }
            })

            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {

                }

                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {

                    val items = detections.detectedItems
                    if (items.size() != 0) {
                        textView?.post {
                            val stringBuilder = StringBuilder()
                            for (i in 0 until items.size()) {
                                val item = items.valueAt(i)
                                stringBuilder.append(item.value)
                                stringBuilder.append("\n")
                            }
                            textView?.text = stringBuilder.toString()
                        }
                    }
                }
            })
        }
    }
}
