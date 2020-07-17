package com.dynasty.dynastyscansapp.ui.chapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.entity.Chapter
import com.dynasty.dynastyscansapp.data.model.PageModel
import com.dynasty.dynastyscansapp.databinding.FragmentChapterBinding
import com.dynasty.dynastyscansapp.ui.chapter.ChapterViewModel.*
import com.wada811.databinding.dataBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class ChapterFragment : Fragment(R.layout.fragment_chapter) {

    private val binding: FragmentChapterBinding by dataBinding()

    private val viewModel: ChapterViewModel by sharedViewModel()

    private val snapHelper = PagerSnapHelper()

    private var currentPosition: Int = 0

    companion object {
        fun getInstance() = ChapterFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInstance()
    }

    private fun initInstance() {
        viewModel.apply {
            mode.observe(viewLifecycleOwner, Observer {
                chapter.value?.pages?.let { pages ->
                    getCurrentPosition()
                    when (it) {
                        ViewMode.HORIZONTAL -> {
                            initRecyclerViewHorizontal(pages)
                        }
                        ViewMode.VERTICAL -> {
                            initRecyclerViewVertical(pages)
                        }
                        else -> {
                        }
                    }
                }
            })
        }

        binding.recyclerView.addOnScrollListener(
            RecyclerViewPreloader<Chapter>(
                Glide.with(this),
                preloadModelProvider,
                ViewPreloadSizeProvider(),
                10
            )
        )
    }

    private fun getCurrentPosition() {
        (binding.recyclerView.layoutManager as? LinearLayoutManager)?.let {
            currentPosition = it.findFirstVisibleItemPosition()
        }
    }

    private fun initRecyclerViewHorizontal(list: List<PageModel>) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = PageAdapter(list, R.layout.view_list_page_horizontal)

            snapHelper.attachToRecyclerView(this)

            if (currentPosition != 0)
                scrollToPosition(currentPosition)
        }
    }

    private fun initRecyclerViewVertical(list: List<PageModel>) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = PageAdapter(list, R.layout.view_list_page)

            snapHelper.attachToRecyclerView(null)

            if (currentPosition != 0)
                scrollToPosition(currentPosition)
        }
    }

    private val preloadModelProvider = object : ListPreloader.PreloadModelProvider<Chapter> {
        override fun getPreloadItems(position: Int): MutableList<Chapter> =
            viewModel.chapter.value?.let {
                Collections.singletonList(it)
            } ?: Collections.emptyList()

        override fun getPreloadRequestBuilder(item: Chapter): RequestBuilder<*>? =
            Glide.with(requireContext())
                .load("${BuildConfig.BASE_URL}${item.pages?.get(0)?.url}")
    }
}