package us.kikin.apps.android.bgplayer.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import java.lang.UnsupportedOperationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.kikin.apps.android.bgplayer.databinding.FragmentSearchBinding
import us.kikin.apps.android.bgplayer.models.ShowModel
import us.kikin.apps.android.bgplayer.ui.videos.VideoItemClickListener
import us.kikin.apps.android.bgplayer.ui.videos.VideoLoadStateAdapter

@AndroidEntryPoint
class SearchFragment : Fragment(), VideoItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var debouncePeriod: Long = 500
    private val viewModel: SearchViewModel by viewModels()
    private val adapter = SearchAdapter(this)
    private var searchJob: Job? = null
    private val searchTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            getVideosByQuery(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            searchEditText.addTextChangedListener(searchTextWatcher)
            // init adapter
            searchResults.adapter = adapter.withLoadStateHeaderAndFooter(
                header = VideoLoadStateAdapter(),
                footer = VideoLoadStateAdapter()
            )
            adapter.addLoadStateListener { loadState ->
                searchResults.isVisible = loadState.source.refresh is LoadState.NotLoading
                listProgress.isVisible = loadState.source.refresh is LoadState.Loading

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Whoops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        return binding.root
    }

    private fun getVideosByQuery(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(debouncePeriod)
            if (query.length > 3) {
                viewModel.getVideosWithQuery(query).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onVideoClicked(videoId: Long) {
        val action = SearchFragmentDirections.videoDetailAction(videoId)
        findNavController().navigate(action)
    }

    override fun onVideoShowClicked(showModel: ShowModel) {
        throw UnsupportedOperationException("Show clicked not allowed here")
    }
}
