package com.dynasty.dynastyscansapp.ui.chapter

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.TagType
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_chapter_detail.*

class ChapterDetailDialog : BottomSheetDialogFragment() {

    lateinit var clipboard: ClipboardManager

    companion object {

        const val ARG_CHAPTER = "arg_chapter"

        fun newInstance(chapter: ChapterModel) = ChapterDetailDialog().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_CHAPTER, chapter)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.skipCollapsed = true
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        return bottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_chapter_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<ChapterModel>(ARG_CHAPTER)?.let {
            tvTitle.text = it.title
            tvSeries.text = it.getTag(TagType.Series)?.name ?: ""
            tvAuthor.text = it.getTag(TagType.Author)?.name ?: ""
            tvScanlator.text = it.getTag(TagType.Scanlator)?.name ?: ""
            tvReleased.text = it.releasedOn
        }

        itemTitle.setOnLongClickListener {
            tvTitle.text.copyToClipBoard()
        }

        itemSeries.setOnLongClickListener {
            tvSeries.text.copyToClipBoard()
        }

        itemAuthor.setOnLongClickListener {
            tvTitle.text.copyToClipBoard()
        }

        itemScanlator.setOnLongClickListener {
            tvScanlator.text.copyToClipBoard()
        }

        itemReleased.setOnLongClickListener {
            tvReleased.text.copyToClipBoard()
        }
    }

    private fun CharSequence.copyToClipBoard(): Boolean {
        if (this.isNotEmpty()) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text", this))
            Toast.makeText(requireContext(), getString(R.string.copied), Toast.LENGTH_SHORT).show()
        }
        return true
    }
}