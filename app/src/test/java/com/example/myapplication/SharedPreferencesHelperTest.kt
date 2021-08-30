package com.example.myapplication

import android.content.SharedPreferences
import com.example.myapplication.utils.SharedPreferenceEntry
import com.example.myapplication.utils.SharedPreferencesHelper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.eq
import org.mockito.runners.MockitoJUnitRunner
import java.util.*


/**
 * Unit tests for the {@link SharedPreferencesHelper} that mocks {@link SharedPreferences}.
 * RefLink
 * 1) https://medium.com/mindorks/learn-unit-testing-in-android-by-building-a-sample-application-23ec2f6340e8#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjgxOWQxZTYxNDI5ZGQzZDNjYWVmMTI5YzBhYzJiYWU4YzZkNDZmYmMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2MzAyNTMwMzQsImF1ZCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExMjIzNjkxODI4MzA2NTEzNDY4NiIsImVtYWlsIjoicHJhdGlrdnlhczEzMDMxOTkxQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiIyMTYyOTYwMzU4MzQtazFrNnFlMDYwczJ0cDJhMmphbTRsamRjbXMwMHN0dGcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiUHJhdGlrIFZ5YXMiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2hhcERjNUNnZHk2SF8tYkMzUm5teVlTVVRWSjF4YUkwa1ZsUzR6X3c9czk2LWMiLCJnaXZlbl9uYW1lIjoiUHJhdGlrIiwiZmFtaWx5X25hbWUiOiJWeWFzIiwiaWF0IjoxNjMwMjUzMzM0LCJleHAiOjE2MzAyNTY5MzQsImp0aSI6IjEwZWJiMGJhNzYzZTQxZmRhNTNlNzkwNzYxOGIwNDJhZTNhYjE5MWQifQ.n9ELnFNjLmeFJCJ9umYeeVRnsyQsvr68zSteV1xlYBo-9ElSvjEjeWRv6pi7uwB_dyMchwp6zVmEA4gQuLyHQVdH0KbqfPdj0I62WOAtyUEIGxAyQ1FGrezvFgjBV_uLrfQZYOYSWLergp1oxP39fSiVsDXq2RrzYmJ6yAnXvJd0D90KBJR8T0DDte0k2ta599iX0Eu9jlCyETdUidxgeSPG6qL5ZCkqNtIEhAqD0Jxkb9cPcTrSlZ6x9H9Er6gn26mYcejHOqkA4TaL_xkkP_ulVq_roFVyDKmRlmckymDRA4i2J9PKq1cfMtNxbrFMdk29ehdpBBHltIxozA0-Mw
 * 2) https://kotlintesting.com/using-mockito-in-kotlin-projects/
 */

@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesHelperTest {
    private val TEST_NAME = "Test name"
    private val TEST_EMAIL = "test@email.com"
    private val TEST_DATE_OF_BIRTH: Calendar = Calendar.getInstance()
    private var mSharedPreferenceEntry: SharedPreferenceEntry? = null
    private var mMockSharedPreferencesHelper: SharedPreferencesHelper? = null
    private var mMockBrokenSharedPreferencesHelper: SharedPreferencesHelper? = null

    @Mock
    var mMockSharedPreferences: SharedPreferences? = null

    @Mock
    var mMockBrokenSharedPreferences: SharedPreferences? = null

    @Mock
    var mMockEditor: SharedPreferences.Editor? = null

    @Mock
    var mMockBrokenEditor: SharedPreferences.Editor? = null

    @Before
    fun initMocks() {
        // Create SharedPreferenceEntry to persist.
        mSharedPreferenceEntry = SharedPreferenceEntry(
            TEST_NAME, TEST_DATE_OF_BIRTH,
            TEST_EMAIL
        )
        // Create a mocked SharedPreferences.
        mMockSharedPreferencesHelper = createMockSharedPreference()
        // Create a mocked SharedPreferences that fails at saving data.
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference()
    }

    @Test
    fun sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        // Save the personal information to SharedPreferences
        val success = mMockSharedPreferencesHelper!!.savePersonalInfo(mSharedPreferenceEntry!!)
        assertThat(
            "Checking that SharedPreferenceEntry.save... returns true",
            success, `is`(true)
        )
        // Read personal information from SharedPreferences
        val savedSharedPreferenceEntry = mMockSharedPreferencesHelper!!.personalInfo
        // Make sure both written and retrieved personal information are equal.
        assertThat(
            "Checking that SharedPreferenceEntry.name has been persisted and read correctly",
            mSharedPreferenceEntry!!.name,
            `is`(equalTo(savedSharedPreferenceEntry.name))
        )
        assertThat(
            "Checking that SharedPreferenceEntry.dateOfBirth has been persisted and read "
                    + "correctly",
            mSharedPreferenceEntry!!.dateOfBirth,
            `is`(equalTo(savedSharedPreferenceEntry.dateOfBirth))
        )
        assertThat(
            ("Checking that SharedPreferenceEntry.email has been persisted and read "
                    + "correctly"),
            mSharedPreferenceEntry!!.email,
            `is`(equalTo(savedSharedPreferenceEntry.email))
        )
    }

    @Test
    fun sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // Read personal information from a broken SharedPreferencesHelper
        val success =
            mMockBrokenSharedPreferencesHelper!!.savePersonalInfo((mSharedPreferenceEntry)!!)
        assertThat(
            "Makes sure writing to a broken SharedPreferencesHelper returns false", success,
            `is`(false)
        )
    }



    /**
     * Creates a mocked SharedPreferences.
     */
    private fun createMockSharedPreference(): SharedPreferencesHelper? {
        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        `when`(
            mMockSharedPreferences!!.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString())
        ).thenReturn(mSharedPreferenceEntry!!.name)

        `when`(
            mMockSharedPreferences!!.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString())
        ).thenReturn(mSharedPreferenceEntry!!.email)

        `when`(
            mMockSharedPreferences!!.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong())
        ).thenReturn(mSharedPreferenceEntry!!.dateOfBirth.timeInMillis)

        // Mocking a successful commit.
        `when`(mMockEditor!!.commit()).thenReturn(true)
        // Return the MockEditor when requesting it.
        `when`(mMockSharedPreferences!!.edit()).thenReturn(mMockEditor)
        return SharedPreferencesHelper(mMockSharedPreferences!!)
    }

    /**
     * Creates a mocked SharedPreferences that fails when writing.
     */
    private fun createBrokenMockSharedPreference(): SharedPreferencesHelper? {
        // Mocking a commit that fails.
        `when`(mMockBrokenEditor!!.commit()).thenReturn(false)
        // Return the broken MockEditor when requesting it.
        `when`(mMockBrokenSharedPreferences!!.edit()).thenReturn(mMockBrokenEditor)
        return SharedPreferencesHelper(mMockBrokenSharedPreferences!!)
    }

}