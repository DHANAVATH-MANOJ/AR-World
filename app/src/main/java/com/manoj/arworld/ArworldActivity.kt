package com.manoj.arworld

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ArworldActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var isModelPlaced = false  // Prevent multiple placements


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arworld)
        val modelUrl = intent.getStringExtra("link")
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        // Plane detection log
        arFragment.arSceneView.scene.addOnUpdateListener {
            val planes = arFragment.arSceneView.session?.getAllTrackables(Plane::class.java)
            if (planes != null && planes.any { it.trackingState == TrackingState.TRACKING }) {
                Log.d("AR_DEBUG", "Plane detected!")
            }
        }

        // Tap on plane
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (!isModelPlaced) {
                val anchor = hitResult.createAnchor()
                placeObject(anchor, modelUrl!!)
                isModelPlaced = true
            }
        }
    }

    private fun placeObject(anchor: Anchor, url: String) {
        val modelUri = Uri.parse(url)

        val renderableSource = RenderableSource.builder()
            .setSource(this, modelUri, RenderableSource.SourceType.GLB) // ✅ Load GLB from URL
            .setScale(0.5f) // Adjust model size
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        ModelRenderable.builder()
            .setSource(this, renderableSource)
            .setRegistryId(url) // ✅ Cache by URL
            .build()
            .thenAccept { renderable ->
                addNodeToScene(anchor, renderable)
            }
            .exceptionally { throwable ->
                Toast.makeText(this, "Error loading model: ${throwable.message}", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun addNodeToScene(anchor: Anchor, renderable: ModelRenderable) {
        val anchorNode = AnchorNode(anchor)
        val transformableNode = TransformableNode(arFragment.transformationSystem)
        transformableNode.setParent(anchorNode)
        transformableNode.renderable = renderable
        arFragment.arSceneView.scene.addChild(anchorNode)
        transformableNode.select()
    }
}
