package com.example.asuserdetailsapp

import com.example.asuserdetailsapp.view.LoginFragment
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun login_isValid() {
        val myObjectUnderTest = LoginFragment()
        val result = myObjectUnderTest.validate("demo@gmail.com", "123456")
        assertEquals(true, result)
    }
}