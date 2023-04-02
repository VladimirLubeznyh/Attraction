package com.example.attractions.presentation.createphoto

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.attractions.*
import com.example.attractions.databinding.CreatePhotoFragmentBinding
import com.example.attractions.BaseApp.Companion.NOTIFICATION_CHANNEL_ID
import com.example.attractions.R
import com.example.attractions.presentation.checkPermissionsGranted
import com.example.attractions.presentation.getDateString
import com.example.attractions.presentation.isPermissionsGranted
import com.example.attractions.presentation.photolistfragment.showToastPhotoSave
import com.example.attractions.presentation.photolistfragment.showToastPhotoSaveFailed
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject


class CreatePhotoFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: CreatePhotoViewModelFactory
    private var _binding: CreatePhotoFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var executor: Executor
    private var imageCapture: ImageCapture? = null
    private val launcher = registerForActivityResult(RequestMultiplePermissions()) {
        if (it.values.all { true }) startCamera()
    }
    private val name = SimpleDateFormat(FILENAME_FORMAT, Locale.UK)
        .format(System.currentTimeMillis())
    private val viewModel: CreatePhotoViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.injectInCreatePhotoFragment(this)
        executor = ContextCompat.getMainExecutor(requireContext())
        isPermissionsGranted(launcher, REQUEST_PERMISSION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreatePhotoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isPermissionsGranted(launcher, REQUEST_PERMISSION)) startCamera()
        binding.photoCreateBt.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val calendar = Calendar.getInstance()
                    val savedUrlString = outputFileResults.savedUri.toString()

                    viewModel.addPhotoToDataBase(
                        savedUrlString,
                        calendar.getDateString()
                    )
                    showToastPhotoSave(savedUrlString)
                    createNotification(savedUrlString)
                }

                override fun onError(exception: ImageCaptureException) {
                    showToastPhotoSaveFailed(exception.message)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
            preview.setSurfaceProvider(binding.previewView.surfaceProvider)
        }, executor)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun createNotification(urlPhotoSave: String) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
            else PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        val notification = NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Photo save")
            .setContentText("Photo save in $urlPhotoSave")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        requireContext().checkPermissionsGranted(launcher, REQUEST_PERMISSION){
            NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
        }

    }

    companion object {
        private const val NOTIFICATION_ID = 1000
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"
        val REQUEST_PERMISSION: Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) add(Manifest.permission.POST_NOTIFICATIONS)
        }.toTypedArray()
    }
}
