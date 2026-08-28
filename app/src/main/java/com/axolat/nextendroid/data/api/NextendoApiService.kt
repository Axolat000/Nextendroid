package com.axolat.nextendroid.data.api

import com.axolat.nextendroid.data.model.*
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
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

data class FavoritePayload(
    val pid: Long,
    val favorite: Boolean
)

interface NextendoApiService {

    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @POST("/api/guest")
    suspend fun guestLogin(@Body request: GuestRequest): Response<LoginResponse>

    @GET("/api/site-config")
    suspend fun getSiteConfig(): Response<SiteConfigResponse>

    @GET("/api/username-available")
    suspend fun checkUsernameAvailable(@Query("username") username: String): Response<UsernameAvailableResponse>

    @POST("/api/forgot")
    suspend fun forgotPassword(@Body request: ForgotRequest): Response<Map<String, Any>>

    @POST("/api/reset")
    suspend fun resetPassword(@Body request: ResetRequest): Response<Map<String, Any>>

    @GET("/api/verify")
    suspend fun verifyEmail(@Query("token") token: String): Response<Map<String, Any>>

    @POST("/api/resend-verification")
    suspend fun resendVerification(): Response<Map<String, Any>>

    @POST("/api/email")
    suspend fun changeEmail(@Body request: ChangeEmailRequest): Response<MeResponse>

    @POST("/api/delete-account")
    suspend fun deleteAccount(@Body request: DeleteAccountRequest): Response<Map<String, Any>>

    @GET("/api/sessions")
    suspend fun getSessions(): Response<SessionsResponse>

    @POST("/api/sessions/revoke")
    suspend fun revokeSession(@Body request: RevokeSessionRequest): Response<Map<String, Any>>

    @POST("/api/sessions/revoke-all")
    suspend fun revokeAllSessions(): Response<Map<String, Any>>

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
    suspend fun setFavorite(@Body payload: FavoritePayload): Response<Map<String, Any>>

    @POST("/api/friends/remove")
    suspend fun removeFriend(@Body payload: PidPayload): Response<Map<String, Any>>

    @POST("/api/friends/block")
    suspend fun blockFriend(@Body payload: PidPayload): Response<Map<String, Any>>

    @GET("/api/friends/history")
    suspend fun getFriendHistory(@Query("pid") pid: Long): Response<PlayHistoryResponse>

    @GET("/api/saves")
    suspend fun getSaves(): Response<SavesResponse>

    @DELETE("/api/save/{titleId}")
    suspend fun deleteSave(@Path("titleId") titleId: String): Response<Unit>

    @Streaming
    @GET("/api/save/{titleId}")
    suspend fun downloadSave(@Path("titleId") titleId: String): Response<ResponseBody>

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

    @GET("/")
    suspend fun pingServer(): Response<Void>
}
