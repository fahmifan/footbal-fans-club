package io.github.miun173.footballfans.repository

interface ApiRepo {
    fun doReq(url: String): String
}