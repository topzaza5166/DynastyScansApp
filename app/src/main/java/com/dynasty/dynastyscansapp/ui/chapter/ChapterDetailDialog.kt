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
import androidx.lifecycle.Observer
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.model.ChapterDetailModel
import com.dynasty.dynastyscansapp.databinding.DialogChapterDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChapterDetailDialog : BottomSheetDialogFragment() {

    private lateinit var clipboard: ClipboardManager

    private lateinit var binding: DialogChapterDetailBinding

    private val viewModel: ChapterViewModel by sharedViewModel()

    companion object {

        fun newInstance() = ChapterDetailDialog().apply {
            arguments = Bundle()
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

            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.skipCollapsed = true
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        return bottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogChapterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chapter.observe(viewLifecycleOwner, Observer {
            binding.chapter = it
        })

        binding.apply {
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
    }

    private fun CharSequence.copyToClipBoard(): Boolean {
        if (this.isNotEmpty()) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text", this))
            Toast.makeText(requireContext(), getString(R.string.copied), Toast.LENGTH_SHORT).show()
        }
        return true
    }
}