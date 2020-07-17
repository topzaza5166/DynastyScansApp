package com.dynasty.dynastyscansapp.ui.chapterlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.data.repository.ServiceRepositoryImpl
import com.dynasty.dynastyscansapp.databinding.FragmentChapterListBinding
import com.dynasty.dynastyscansapp.ui.chapter.ChapterActivity
import com.wada811.databinding.dataBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChapterListFragment : Fragment(R.layout.fragment_chapter_list) {

    private val viewModel: ChapterListViewModel by viewModel()

    private val binding: FragmentChapterListBinding by dataBinding()

    private val repository: ServiceRepository by inject()

    private val chapterAdapter: ChapterListAdapter by lazy {
        ChapterListAdapter(this, repository) { chapter ->
            chapter?.let {
                startActivity(ChapterActivity.getIntent(requireContext(), chapter))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInstance()
    }

    private fun initInstance() {
        binding.model = viewModel.apply {
            chaptersList.observe(viewLifecycleOwner, Observer {
                chapterAdapter.submitList(it)
            })
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.dataSourceFactory.dataSource.apply {
                addInvalidatedCallback {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                invalidate()
            }
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            setItemViewCacheSize(10)
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            adapter = chapterAdapter
        }
    }

}