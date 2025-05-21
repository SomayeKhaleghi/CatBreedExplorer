import app.cash.turbine.test
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import com.challenge.catbreedexplorer.testutils.MainDispatcherRule
import com.challenge.catbreedexplorer.ui.catlist.*
import com.challenge.catbreedexplorer.utils.NetworkChecker
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: CatBreedRepository
    private lateinit var networkChecker: NetworkChecker
    private lateinit var viewModel: CatListViewModel

    @Before
    fun setup() {
        repository = mockk()
        networkChecker = mockk()

        every { networkChecker.isOnline() } returns true
    }

    @Test
    fun `refreshCatBreeds loads data and updates state`() = runTest {
        // Arrange
        val fakeCats = listOf(
            CatBreed("abys", "Abyssinian", "", "Egypt", "", "", "", ""),
            CatBreed("aege", "Aegean", "", "Greece", "", "", "", "")
        )

        coEvery { repository.getCatBreeds() } returns flowOf(fakeCats)

        // When
        viewModel = CatListViewModel(repository, networkChecker)

        // Then
        viewModel.state.test {
            val loading = awaitItem()
            Assert.assertTrue(loading is CatListState.Loading)

            val success = awaitItem()
            Assert.assertTrue(success is CatListState.Success)
            Assert.assertTrue((success as CatListState.Success).cats.size == 2)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
