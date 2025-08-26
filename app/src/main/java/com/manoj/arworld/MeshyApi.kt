package com.manoj.arworld.api

import com.manoj.arworld.model.CreateTaskRequest
import com.manoj.arworld.model.CreateTaskResponse
import com.manoj.arworld.model.TaskStatusResponse
import retrofit2.Call
import retrofit2.http.*

interface MeshyApi {

    // Create a new task
    @POST("image-to-3d")
    fun createTask(
        @Body request: CreateTaskRequest
    ): Call<CreateTaskResponse>

    @GET("image-to-3d/{Id}")
    fun getTaskStatus(
        @Path("Id") Id: String
    ): Call<TaskStatusResponse>
}
