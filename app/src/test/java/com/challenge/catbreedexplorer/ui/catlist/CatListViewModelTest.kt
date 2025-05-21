import app.cash.turbine.test
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import com.challenge.catbreedexplorer.testutils.MainDispatcherRule
import com.challenge.catbreedexplorer.ui.catlist.CatListIntent
import com.challenge.catbreedexplorer.ui.catlist.CatListState
import com.challenge.catbreedexplorer.ui.catlist.CatListViewModel
import com.challenge.catbreedexplorer.utils.NetworkChecker
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CatListViewModelTest {

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
        viewModel = CatListViewModel(repository, networkChecker) // if needed
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshCatBreeds loads data and updates state`() = mainDispatcherRule.testScope.runTest {
        val fakeCats = listOf(
            CatBreed("abys", "Abyssinian", "", "Egypt", "", "", "", "")/*,
            CatBreed("aege", "Aegean", "", "Greece", "", "", "", "")*/
        )

        // Simulate repository responses
        coEvery { repository.refreshCatBreeds() } returns Unit
        coEvery { repository.getCatBreeds() } returns flowOf(fakeCats)

        viewModel.handleIntent(CatListIntent.RefreshCats)

        // Then
        viewModel.state.test {
            Assert.assertTrue(awaitItem() is CatListState.Loading)
            val successState = awaitItem()
            Assert.assertTrue(successState is CatListState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
