package com.example.data.network.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class SafeApiRequestTest {
    @Mock
    lateinit var mockResponse: Response<Any>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `safeApiRequest returns data when response is successful`() = runTest {
        // Arrange
        val expectedResponse = "Expected response"
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(expectedResponse)

        val safeApiRequest = object : SafeApiRequest() {}

        // Act
        val result = safeApiRequest.safeApiRequest { mockResponse }

        // Assert
        assertEquals(expectedResponse, result)
    }
}
/*

    @Test(expected = ApiException::class)
    fun `safeApiRequest throws ApiException when response is unsuccessful`() = runTest {
        // Arrange
        val errorMessage = "error message"
        val errorBody =
            "{\"error\":\"$errorMessage\"}".toResponseBody("application/json".toMediaTypeOrNull())
        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(mockResponse.errorBody()).thenReturn(errorBody)

        val safeApiRequest = object : SafeApiRequest() {}

        // Act
      //  safeApiRequest.safeApiRequest { mockResponse }
    }
}*/
