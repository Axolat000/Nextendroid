package com.axolat.nextendroid.data.api

import com.axolat.nextendroid.data.model.*
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class UsernameRequest(val username: String)
data class CountryRequest(val country: String)
data class ProfileRequest(
    val image: String? = null,
    val avatar: String? = null
)

data class SendFriendRequestPayload(
    @SerializedName("friend_code") val friendCode: String
)

data class PidPayload(
    val pid: Long
)

interface NextendoApiService {

    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/me")
    suspend fun getMe(): Response<MeResponse>

    @GET("/api/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @POST("/api/profile")
    suspend fun updateProfile(@Body request: ProfileRequest): Response<ProfileResponse>

    @GET("/api/friends")
    suspend fun getFriends(): Response<FriendListResponse>

    @POST("/api/friends")
    suspend fun sendFriendRequest(@Body payload: SendFriendRequestPayload): Response<Map<String, Any>>

    @POST("/api/friends/accept")
    suspend fun acceptFriendRequest(@Body payload: PidPayload): Response<Map<String, Any>>

    @POST("/api/friends/decline")
    suspend fun declineFriendRequest(@Body payload: PidPayload): Response<Map<String, Any>>

    @POST("/api/friends/favorite")
    suspend fun toggleFavorite(@Query("pid") pid: Long): Response<Unit>

    @GET("/api/friends/history")
    suspend fun getFriendHistory(@Query("pid") pid: Long): Response<PlayHistoryResponse>

    @GET("/api/saves")
    suspend fun getSaves(): Response<SavesResponse>

    @GET("/api/online-counts")
    suspend fun getOnlineCounts(): Response<OnlineCountsResponse>

    @GET("/api/history")
    suspend fun getHistory(): Response<PlayHistoryResponse>

    @GET("/api/gameinfo")
    suspend fun getGameInfo(@Query("title_id") titleId: String): Response<GameInfoResponse>

    @POST("/api/username")
    suspend fun updateUsername(@Body request: UsernameRequest): Response<MeResponse>

    @POST("/api/country")
    suspend fun updateCountry(@Body request: CountryRequest): Response<MeResponse>
}
