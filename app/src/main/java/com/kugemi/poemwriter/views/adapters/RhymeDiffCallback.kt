package com.kugemi.poemwriter.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.kugemi.poemwriter.viewmodels.RhymeViewModel

class RhymeDiffCallback : DiffUtil.ItemCallback<RhymeViewModel>() {
    override fun areContentsTheSame(oldItem: RhymeViewModel, newItem: RhymeViewModel): Boolean {
        return oldItem.word == newItem.word && oldItem.syllables == newItem.syllables && oldItem.frequency == newItem.frequency
    }

    override fun areItemsTheSame(oldItem: RhymeViewModel, newItem: RhymeViewModel): Boolean {
        return oldItem.word == newItem.word
    }
}