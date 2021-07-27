package com.example.sampleprojectsetup

import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        emailValidate("z@abc.cp'")
    }
    fun emailValidate(email: String): Boolean {
        val test: Pattern =
            Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val matcher: Matcher = test.matcher(email)
        return matcher.matches()
    }
}