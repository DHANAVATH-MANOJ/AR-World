package com.manoj.arworld.model

import com.google.gson.annotations.SerializedName

data class CreateTaskRequest(
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("enable_pbr") val enablePbr: Boolean = true,
    @SerializedName("should_remesh") val shouldRemesh: Boolean = true,
    @SerializedName("should_texture") val shouldTexture: Boolean = true
)
data class CreateTaskResponse(
    @SerializedName("result") val id: String?
)

data class TaskStatusResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("progress") val progress: Int?,
    @SerializedName("thumbnail_url") val thumbnailUrl: String?,
    @SerializedName("model_urls") val modelUrls: ModelUrls?,
    @SerializedName("texture_urls") val textureUrls: List<TextureUrls>?,
    @SerializedName("task_error") val taskError: TaskError?
)

data class ModelUrls(
    @SerializedName("glb") val glb: String?,
    @SerializedName("fbx") val fbx: String?,
    @SerializedName("obj") val obj: String?,
    @SerializedName("usdz") val usdz: String?
)

data class TextureUrls(
    @SerializedName("base_color") val baseColor: String?,
    @SerializedName("metallic") val metallic: String?,
    @SerializedName("normal") val normal: String?,
    @SerializedName("roughness") val roughness: String?
)

data class TaskError(
    @SerializedName("message") val message: String?
)