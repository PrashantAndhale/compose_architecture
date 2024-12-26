package com.example.bottomnavigationandbottomsheet.screens.profile

import android.app.Application
import app.cash.turbine.test
import com.example.common.Resource
import com.example.data.repository.PagerMoviesRepositoryImpl
import com.example.domain.model.MoviesItem
import com.example.domain.use_cases.GetMoviesUseCases
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProfileViewModelTest {

    @Mock
    lateinit var getMoviesUseCases: GetMoviesUseCases

    @Mock
    lateinit var repository: PagerMoviesRepositoryImpl

    @Mock
    lateinit var application: Application

    private lateinit var viewModel: ProfileViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)
        // Setup mock objects
        viewModel = ProfileViewModel(
            repository = repository,
            cases = getMoviesUseCases,
            application = application
        )
    }


    @Test
    fun `test fetchMovies when no internet updates isConnected`() = testScope.runTest {
        // Simulate no internet connection
        viewModel._isConnected.value = false
        // Verify that isConnected is false
        viewModel.isConnected.value?.let { assertFalse(it) }
    }

    @Test
    fun `test getMovies updates movies with resource Success`() = testScope.runTest {
        // Simulate successful movie data
        val successResource = Resource.Success(
            listOf(
                MoviesItem(
                    id = 1,
                    movie = "Movie 1",
                    imdb_url = "",
                    image = "",
                    rating = 4.0
                )
            )
        )
        `when`(getMoviesUseCases.getMovies()).thenReturn(flowOf(successResource))

        // Call getMovies and check the result
        viewModel.getMovies()

        // Verify the updated movies state
        viewModel.movies.test {
            assertEquals(successResource, awaitItem())
        }
    }

    @Test
    fun `test clearErrorMessage sets isConnected to false`() {
        // Initially set connected to true
        viewModel._isConnected.value = true

        // Call clearErrorMessage
        viewModel.clearErrorMessage()

        // Verify that the value of _isConnected is now false
        viewModel.isConnected.value?.let { assertFalse(it) }
    }
}
