package com.uyghar.lazyloaddemo.model

data class User(
    val id: Int?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val avatar: String?
)

data class Support(
    val url: String?,
    val text: String?
)
data class UserDB(
    val id: Int?,
    val page: Int?,
    val total_page: Int?,
    val per_page: Int?,
    val data: List<User>?,
    val support: Support?
)