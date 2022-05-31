package id.com.yopisptr.deepest.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResult (
    @field:SerializedName("token")
    val token: String
    )
