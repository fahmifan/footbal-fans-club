package io.github.miun173.footballfans.repository

import java.net.URL

class ApiRepoImpl : ApiRepo {
    override fun doReq(url: String): String {
        return URL(url).readText()
    }
}