package com.example.asuserdetailsapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.asuserdetailsapp.view.LoginFragment

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.asuserdetailsapp", appContext.packageName)
    }
    @Test
    fun login_isValid() {
        val myObjectUnderTest = LoginFragment()
        val result = myObjectUnderTest.validate("demo@gmail.com", "123456")
        assertEquals(true, result)
    }
}