package ecofrost.app.dynamic.form

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.camerakit.CameraKitView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraFragment : Fragment() {

    val TAG = CameraFragment::class.java.simpleName
    var isRecording = false

    var CAMERA_PERMISSION = Manifest.permission.CAMERA
    var RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO

    var RC_PERMISSION = 101


    lateinit var mCamera : CameraKitView
    lateinit var mCapture : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  =
         inflater.inflate(R.layout.fragment_camer, container, false)
        mCamera = view.findViewById(R.id.camera)
        mCapture = view.findViewById(R.id.fab_camera)
        return view
    }

    override fun onResume() {
        super.onResume()
        mCamera.onResume()
    }

    override fun onStop() {
        super.onStop()
        mCamera.onStop()
    }

    override fun onPause() {
        super.onPause()
        mCamera.onPause()
    }

    override fun onStart() {
        super.onStart()
        mCamera.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkPermissions()) startCameraSession() else requestPermissions()

        mCapture.setOnClickListener(View.OnClickListener { v->
            mCamera.captureImage(object : CameraKitView.ImageCallback{
                override fun onImage(p0: CameraKitView?, p1: ByteArray?) {
                    val file  =  File(requireActivity().applicationContext.filesDir, "TKT 20201")
                    val out = FileOutputStream(file.path)

                    try {
                        out.write(p1)
                        Log.d("RES_CAM","Captured")
                        out.close()
                    }
                    catch (e: IOException){
                        e.printStackTrace()
                    }
                }

            })
        })

    }

    private fun requestPermissions() {
        requestPermissions(requireActivity(), arrayOf(CAMERA_PERMISSION, RECORD_AUDIO_PERMISSION), RC_PERMISSION)
    }

    private fun checkPermissions(): Boolean {
        return ((checkSelfPermission(requireContext(), CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (checkSelfPermission(requireContext(), CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            RC_PERMISSION -> {
                var allPermissionsGranted = false
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false
                        break
                    } else {
                        mCamera.onRequestPermissionsResult(requestCode, permissions, grantResults);
                        allPermissionsGranted = true
                    }
                }
                if (allPermissionsGranted) startCameraSession() else permissionsNotGranted()
            }
        }
    }

    private fun startCameraSession() {

    }

    private fun permissionsNotGranted() {
        AlertDialog.Builder(requireContext()).setTitle("Permissions required")
            .setMessage("These permissions are required to use this app. Please allow Camera and Audio permissions first")
            .setCancelable(false)
            .setPositiveButton("Grant") { dialog, which -> requestPermissions() }
            .show()
    }


}