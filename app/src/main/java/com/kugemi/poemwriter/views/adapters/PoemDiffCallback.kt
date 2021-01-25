package com.kugemi.poemwriter.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.kugemi.poemwriter.viewmodels.PoemViewModel

class PoemDiffCallback : DiffUtil.ItemCallback<PoemViewModel>() {
    override fun areItemsTheSame(oldItem: PoemViewModel, newItem: PoemViewModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PoemViewModel, newItem: PoemViewModel): Boolean {
        return oldItem.text == newItem.text && oldItem.syllables == newItem.syllables
    }
}