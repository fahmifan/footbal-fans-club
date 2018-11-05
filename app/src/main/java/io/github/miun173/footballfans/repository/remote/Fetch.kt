package io.github.miun173.footballfans.repository.remote

interface Fetch {
    fun doReq(url: String): String
}