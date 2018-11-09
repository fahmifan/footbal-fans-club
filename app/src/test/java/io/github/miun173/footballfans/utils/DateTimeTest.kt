package io.github.miun173.footballfans.utils

import org.junit.Test

class DateTimeTest {

    @Test
    fun getShortDate() {
        val shortDate = DateTime.getShortDate("2018-11-10")
        println("shortDate: $shortDate")

        assert(shortDate.equals("Mon, 10/11/2018"))
    }
}