package io.github.miun173.footballfans.repository

interface Fetch {
    fun doReq(url: String): String
}