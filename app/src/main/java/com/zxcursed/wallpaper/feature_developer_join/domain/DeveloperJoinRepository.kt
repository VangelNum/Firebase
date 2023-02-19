package com.zxcursed.wallpaper.feature_developer_join.domain

interface DeveloperJoinRepository {
    suspend fun putBoolean(value: Boolean)
    suspend fun getBoolean(): Boolean?
}