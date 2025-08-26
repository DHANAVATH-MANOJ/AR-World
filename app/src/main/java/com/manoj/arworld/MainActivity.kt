package com.manoj.arworld

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.manoj.arworld.api.ApiClient
import com.manoj.arworld.api.CloudinaryManager
import com.manoj.arworld.model.CreateTaskRequest
import com.manoj.arworld.model.CreateTaskResponse
import com.manoj.arworld.model.TaskStatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnPickImage: Button
    private lateinit var btnGenerate: Button
    private lateinit var btnStartAr: Button
    private lateinit var imageView: ImageView

    private var uploadedImageUrl: String? = null
    private var modelUrl: String? = null
    private var taskId: String? = null
    private var timer: Timer? = null
    private var pollingTask: TimerTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CloudinaryManager.init(this)

        btnPickImage = findViewById(R.id.btnPick)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnStartAr = findViewById(R.id.btnStartAr)
        imageView = findViewById(R.id.holder)

        btnGenerate.isEnabled = false
        btnStartAr.isEnabled = false

        btnPickImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnGenerate.setOnClickListener {
            if (uploadedImageUrl != null) {
                create3DTask(uploadedImageUrl!!)
            } else {
                Toast.makeText(this, "Please upload image first", Toast.LENGTH_SHORT).show()
            }
        }

        btnStartAr.setOnClickListener {
            modelUrl?.let { url ->
                val intent = Intent(this, ArworldActivity::class.java)
                intent.putExtra("link", url)
                startActivity(intent)
            } ?: Toast.makeText(this, "Model not ready yet", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageView.setImageURI(it)
            uploadToCloudinary(it)
        }
    }

    private fun uploadToCloudinary(uri: Uri) {
        Toast.makeText(this, "Uploading image...", Toast.LENGTH_SHORT).show()
        CloudinaryManager.uploadImage(this, uri) { url ->
            if (url != null) {
                uploadedImageUrl = url
                Log.d("Cloudinary", "Uploaded: $url")
                Toast.makeText(this, "Upload complete! Click Generate", Toast.LENGTH_SHORT).show()
                btnGenerate.isEnabled = true
            } else {
                Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun create3DTask(imageUrl: String) {
        Toast.makeText(this, "Generating 3D model...", Toast.LENGTH_SHORT).show()
        Log.d("API_CALL", "Sending request for image: $imageUrl")

        val request = CreateTaskRequest(
            imageUrl = imageUrl,
            enablePbr = true,
            shouldRemesh = true,
            shouldTexture = true
        )

        ApiClient.instance.createTask(request).enqueue(object : Callback<CreateTaskResponse> {
            override fun onResponse(call: Call<CreateTaskResponse>, response: Response<CreateTaskResponse>) {
                Log.d("CREATE_TASK", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    val newTaskId = response.body()?.id
                    if (!newTaskId.isNullOrEmpty()) {
                        taskId = newTaskId
                        Toast.makeText(this@MainActivity, "Task started! Polling...", Toast.LENGTH_SHORT).show()
                        Polling()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "CreateTask failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CreateTaskResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun Polling() {
        timer = Timer()
        pollingTask = object : TimerTask() {
            override fun run() {
                taskId?.let { id ->
                    ApiClient.instance.getTaskStatus(id).enqueue(object : Callback<TaskStatusResponse> {
                        override fun onResponse(call: Call<TaskStatusResponse>, response: Response<TaskStatusResponse>) {
                            if (response.isSuccessful) {
                                val status = response.body()?.status ?: "UNKNOWN"
                                runOnUiThread {
                                    Toast.makeText(this@MainActivity, "Status: $status", Toast.LENGTH_SHORT).show()
                                }
                                if (status.equals("SUCCEEDED", true)) {
                                    stopPolling()
                                    modelUrl = response.body()?.modelUrls?.glb
                                    runOnUiThread {
                                        btnStartAr.isEnabled = true
                                        Toast.makeText(this@MainActivity, "Model ready!", Toast.LENGTH_SHORT).show()
                                    }
                                } else if (status.equals("FAILED", true)) {
                                    stopPolling()
                                    runOnUiThread {
                                        Toast.makeText(this@MainActivity, "Model generation failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<TaskStatusResponse>, t: Throwable) {
                            Log.e("Polling", "Failure: ${t.message}")
                        }
                    })
                }
            }
        }
        timer?.schedule(pollingTask, 0, 5000)
    }

    private fun stopPolling() {
        pollingTask?.cancel()
        timer?.cancel()
        timer?.purge()
    }
}
