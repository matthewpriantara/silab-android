package com.example.silab_app.network

import com.example.silab_app.models.request.CreateReservesRequest
import com.example.silab_app.models.request.LoginRequest
import com.example.silab_app.models.request.RegisterRequest
import com.example.silab_app.models.response.CreateReservesResponse
import com.example.silab_app.models.response.InventoryItemResponse
import com.example.silab_app.models.response.LoginResponse
import com.example.silab_app.models.response.RegisterResponse
import com.example.silab_app.models.response.ReservesInformationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/register")
    fun registerAccount( @Body body: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun loginAccount( @Body body: LoginRequest): Call<LoginResponse>

    @GET("inventories")
    fun getAllInventories():Call<InventoryItemResponse>

    @GET("reserves/history")
    fun getALlHistory():Call<InventoryItemResponse>

    @GET("reserves/{id}")
    fun getDetailInformation( @Path("id") id:Int):Call<ReservesInformationResponse>

    @POST("reserves")
    fun createReserves(@Body body: CreateReservesRequest):Call<CreateReservesResponse>

//    @GET("employees")
//    fun getAllUsers():Call<EmployeResponse>
//
//    @GET("employee/{id}")
//    fun getDetailsUsers( @Path("id") id: Int):Call<EmployeeDetailResponse>
//
//    @POST("create")
//    fun createUser ( @Body body: CreateEmployeeRequest): Call<EmployeeDetailResponse>
//
//    @PUT("update/{id}")
//    fun updateUser (@Path("id") id: Int, @Body body: CreateEmployeeRequest): Call<EmployeeDetailResponse>
//
//    @DELETE("delete/{id}")
//    fun deleteUser(@Path("id") id:Int): Call<EmployeeDeleteResponse>
}