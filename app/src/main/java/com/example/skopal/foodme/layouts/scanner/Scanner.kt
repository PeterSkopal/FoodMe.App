package com.example.skopal.foodme.layouts.scanner


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import com.github.florent37.camerafragment.CameraFragment
import com.github.florent37.camerafragment.configuration.Configuration
import com.github.florent37.camerafragment.widgets.RecordButton
import com.github.florent37.camerafragment.widgets.FlashSwitchView
import com.github.florent37.camerafragment.listeners.CameraFragmentStateAdapter
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener
import java.io.File
import com.example.skopal.foodme.services.ReceiptRecognitionApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Scanner : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_scanner, container, false)
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_scanner))

        openCamera(view)
        return view
    }

    private fun openCamera(view: View) {

        if (ContextCompat.checkSelfPermission(
                        (activity as MainActivity).baseContext,
                        Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED) {

            val cameraFragment = CameraFragment.newInstance(Configuration.Builder().build())
            (activity as MainActivity).changeScreen(cameraFragment, R.id.scanner_frame)

            val button = view.findViewById<RecordButton>(R.id.take_photo_button)
            val flash = view.findViewById<FlashSwitchView>(R.id.flash_switch_view)


            button.setRecordButtonListener {

                cameraFragment.takePhotoOrCaptureVideo(object : CameraFragmentResultListener {
                    override fun onVideoRecorded(filePath: String) {}
                    override fun onPhotoTaken(bytes: ByteArray, filePath: String) {
                        val file = File(filePath)

                        ReceiptRecognitionApi((activity as MainActivity).baseContext).parseReceipt(file) {
                            GlobalScope.launch(Dispatchers.Main) {
                                if (it !== null) {
                                    println("response:\t$it")
                                }
                            }
                        }
                    }
                }, "/storage/emulated/0/FoodMe", "file")
            }

            flash.setOnClickListener { cameraFragment.toggleFlashMode() }


            cameraFragment.setStateListener(object : CameraFragmentStateAdapter() {

                override fun onFlashAuto() {
                    flash.displayFlashAuto()
                }

                override fun onFlashOn() {
                    flash.displayFlashOn()
                }

                override fun onFlashOff() {
                    flash.displayFlashOff()
                }

                override fun onCameraSetupForPhoto() {
                    button.displayPhotoState()
                    flash.visibility = View.VISIBLE
                }

            })

        }
    }
}
