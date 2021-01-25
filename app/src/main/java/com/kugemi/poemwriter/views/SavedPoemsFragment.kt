package com.kugemi.poemwriter.views

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kugemi.poemwriter.R
import com.kugemi.poemwriter.databinding.PoemItemBinding
import com.kugemi.poemwriter.databinding.PoemsFragmentBinding
import com.kugemi.poemwriter.databinding.RhymeItemBinding
import com.kugemi.poemwriter.model.local_dto.SavedPoem
import com.kugemi.poemwriter.viewmodels.PoemListViewModel
import com.kugemi.poemwriter.viewmodels.PoemViewModel
import com.kugemi.poemwriter.viewmodels.RhymeViewModel
import com.kugemi.poemwriter.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.poems_fragment.*
import kotlinx.android.synthetic.main.writing_fragment.*
import java.util.*

@AndroidEntryPoint
class SavedPoemsFragment : Fragment() {
    private val myViewModel : PoemListViewModel by viewModels()
    private lateinit var bindind : PoemsFragmentBinding
    private lateinit var archivedPoem : PoemViewModel
    private val adapter =
        SimpleListAdapter(
                HolderCreator(::createHolder),
                HolderBinder(::bindHolder),
                PoemDiffCallback()
        )

    private fun bindHolder(viewModel: PoemViewModel, holder: Holder<PoemItemBinding>) {
        holder.binding.poemModel = viewModel
        holder.binding.poemListModel = myViewModel
        holder.binding.poemInfo.setOnClickListener {
            val action = SavedPoemsFragmentDirections.actionSavedPoemsFragmentToWriteFragment(viewModel)
            findNavController().navigate(action)
        }


    }

    private fun createHolder(parent: ViewGroup) : Holder<PoemItemBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PoemItemBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        bindind = DataBindingUtil.inflate(inflater, R.layout.poems_fragment, container, false)
        bindind.viewModel = myViewModel
        bindind.lifecycleOwner = viewLifecycleOwner
        bindind.poemList.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT){
                    myViewModel.onDeletePoem(adapter.currentList[viewHolder.adapterPosition])
                    archivedPoem = adapter.currentList[viewHolder.adapterPosition]
                    Snackbar.make(bindind.poemList, "Poem was deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                myViewModel.onSavePoem(archivedPoem.poem)
                            }
                            .show()
                }
            }


            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(Objects.requireNonNull<FragmentActivity>(activity), R.color.red))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }).attachToRecyclerView(bindind.poemList)

        return bindind.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.poemList.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
        })


        bindind.newPoemButton.setOnClickListener {
            val action = SavedPoemsFragmentDirections.actionSavedPoemsFragmentToWriteFragment(PoemViewModel(SavedPoem().apply {
                this.id = -1
            }))
            findNavController().navigate(action)
        }
    }

}