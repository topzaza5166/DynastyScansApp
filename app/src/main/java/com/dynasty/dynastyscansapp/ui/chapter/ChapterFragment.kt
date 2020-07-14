package com.dynasty.dynastyscansapp.ui.chapter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.databinding.FragmentChapterBinding
import com.wada811.databinding.dataBinding
import java.util.*


class ChapterFragment : Fragment(R.layout.fragment_chapter) {

    private val binding: FragmentChapterBinding by dataBinding()

    private var chapter: ChapterModel? = null

    companion object {

        const val ARG_CHAPTER = "arg_chapter"

        fun getInstance(chapter: ChapterModel) = ChapterFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_CHAPTER, chapter)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapter = arguments?.getParcelable(ARG_CHAPTER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInstance()
    }

    private fun initInstance() {


        chapter?.pages?.let {
            binding.recyclerView.apply {
                adapter = PageAdapter(it) {
                    (activity as ChapterActivity).toggleToolbar()
                }
                addOnScrollListener(
                    RecyclerViewPreloader<ChapterModel>(
                        Glide.with(this),
                        preloadModelProvider,
                        ViewPreloadSizeProvider(),
                        10
                    )
                )
            }
        }
    }

    private val preloadModelProvider = object : ListPreloader.PreloadModelProvider<ChapterModel> {
        override fun getPreloadItems(position: Int): MutableList<ChapterModel> =
            chapter?.let {
                Collections.singletonList(it)
            } ?: Collections.emptyList()

        override fun getPreloadRequestBuilder(item: ChapterModel): RequestBuilder<*>? =
            Glide.with(requireContext())
                .load("${BuildConfig.BASE_URL}${item.pages?.get(0)?.url}")
    }
}