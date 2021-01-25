package com.kugemi.poemwriter.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kugemi.poemwriter.R
import com.kugemi.poemwriter.databinding.PoemItemBinding
import com.kugemi.poemwriter.databinding.WritingFragmentBinding
import com.kugemi.poemwriter.model.local_dto.RhymeWord
import com.kugemi.poemwriter.model.local_dto.SavedPoem
import com.kugemi.poemwriter.viewmodels.PoemListViewModel
import com.kugemi.poemwriter.viewmodels.PoemViewModel
import com.kugemi.poemwriter.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.writing_fragment.*
import java.util.regex.Pattern

@AndroidEntryPoint
class WriteFragment : Fragment() {
    lateinit var text : String
    private var currentSyllablesInfo = ""
    private var currentLanguage = "en"
    private val myViewModel : PoemListViewModel by viewModels()
    private val args : WriteFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.writing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText.setText(args.poem.text)
        if (args.poem.syllables != "")
            currentSyllablesInfo = args.poem.syllables
        if (args.poem.language != "")
            currentLanguage = args.poem.language

        editText.setSyllableNumber(currentSyllablesInfo)
        editText.setLanguage(currentLanguage)

        rhyme_button.setOnClickListener {
            val action = WriteFragmentDirections.actionWriteFragmentToRhymeFragment(RhymeWord(rhymeText.text.toString(), currentLanguage))
            findNavController().navigate(action)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_item){
            if (args.poem.id == -1)
            {
                myViewModel.onSavePoem(SavedPoem().apply {
                    this.text = editText.text.toString()
                    this.syllablesInfo = currentSyllablesInfo
                    this.language = currentLanguage
                })
                findNavController().popBackStack()
            }
            else{
                myViewModel.onUpdatePoemText(editText.text.toString(), args.poem.id)
                findNavController().popBackStack()
            }
        }

        if (item.itemId == R.id.syllablesInfo){
            val dialogBuilder = AlertDialog.Builder(activity)
            val dialogLayout = layoutInflater.inflate(R.layout.syllables_alert_dialog, null)
            val syllableEdit = dialogLayout.findViewById<EditText>(R.id.syllable_edit)

            dialogBuilder.setView(dialogLayout)
            dialogBuilder.setTitle("Enter syllables information")
            dialogBuilder.setPositiveButton("OK") { dialog, which ->
                if (checkSyllablesNumbers(syllableEdit.text.toString())){
                    editText.setSyllableNumber(syllableEdit.text.toString())
                    currentSyllablesInfo = syllableEdit.text.toString()
                    if (args.poem.id != -1){
                        myViewModel.onUpdatePoemSyllables(currentSyllablesInfo, args.poem.id)
                    }
                    Toast.makeText(context, "Syllables information added", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, "Incorrect entered data", Toast.LENGTH_SHORT).show()
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }

        if (item.itemId == R.id.languageInfo){
            val arrayOfLanguages = arrayOf("English", "Russian")
            val dialogBuilder = AlertDialog.Builder(activity)

            dialogBuilder.setTitle("Choose language")
            dialogBuilder.setSingleChoiceItems(arrayOfLanguages, -1) { dialogInterface: DialogInterface, i: Int ->
                if (i == 0) currentLanguage = "en"
                if (i == 1) currentLanguage = "ru"
                if (args.poem.id != -1){
                    myViewModel.onUpdatePoemLanguage(currentLanguage, args.poem.id)
                }
                editText.setLanguage(currentLanguage)
                Toast.makeText(context, arrayOfLanguages[i] + " language was selected", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
            }

            val dialog = dialogBuilder.create()
            dialog.show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.write_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun checkSyllablesNumbers (syllablesInfo : String) : Boolean {
        val pattern = Pattern.compile("([0-9]+\\-{1})*[0-9]+$")
        val matcher = pattern.matcher(syllablesInfo)

        return (matcher.matches())
    }

}