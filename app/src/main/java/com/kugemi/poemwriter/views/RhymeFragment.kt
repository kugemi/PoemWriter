package com.kugemi.poemwriter.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kugemi.poemwriter.R
import com.kugemi.poemwriter.databinding.RhymeItemBinding
import com.kugemi.poemwriter.databinding.RhymesFragmentBinding
import com.kugemi.poemwriter.viewmodels.RhymeListViewModel
import com.kugemi.poemwriter.viewmodels.RhymeViewModel
import com.kugemi.poemwriter.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.rhymes_fragment.*

@AndroidEntryPoint
class RhymeFragment : Fragment() {
    private val args : RhymeFragmentArgs by navArgs()
    private val myViewModel : RhymeListViewModel by viewModels()
    private val adapter = SimpleListAdapter(
        HolderCreator(::createHolder),
        HolderBinder(::bindHolder),
        RhymeDiffCallback()
    )

    private fun bindHolder(viewModel: RhymeViewModel, holder: Holder<RhymeItemBinding>) {
        holder.binding.viewModel = viewModel
    }

    private fun createHolder(parent: ViewGroup) : Holder<RhymeItemBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RhymeItemBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private lateinit var myBinding : RhymesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myViewModel.word = args.word.word
        myViewModel.language = args.word.language
        myBinding = DataBindingUtil.inflate(inflater, R.layout.rhymes_fragment, container, false)
        myBinding.lifecycleOwner = viewLifecycleOwner
        myBinding.rhymeList.adapter = adapter
        return myBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.rhymeList.observe(viewLifecycleOwner, Observer {list ->
            adapter.submitList(list.sortedBy { rhyme ->
                rhyme.syllables
            })
        })
    }


}