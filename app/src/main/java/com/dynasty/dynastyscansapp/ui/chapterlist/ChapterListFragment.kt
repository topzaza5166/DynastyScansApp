package com.dynasty.dynastyscansapp.ui.chapterlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DividerItemDecoration
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.databinding.FragmentChapterListBinding
import com.dynasty.dynastyscansapp.ui.chapter.ChapterActivity
import com.dynasty.dynastyscansapp.utils.toast
import com.wada811.databinding.dataBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChapterListFragment : Fragment(R.layout.fragment_chapter_list) {

    private val viewModel: ChapterListViewModel by viewModel()

    private val binding: FragmentChapterListBinding by dataBinding()

    private val api: ServiceApi by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInstance()
    }

    private fun initInstance() {
        binding.model = viewModel.apply {
            chapterList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    binding.recyclerView.apply {
                        setItemViewCacheSize(10)
                        addItemDecoration(
                            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                        )
                        adapter = ChapterListAdapter(
                            viewLifecycleOwner.lifecycleScope.coroutineContext, api, it
                        ) { chapter -> chapter?.let {
                                startActivity(ChapterActivity.getIntent(requireContext(), chapter))
                            }
                        }
                    }
                }
            })
        }
    }

}