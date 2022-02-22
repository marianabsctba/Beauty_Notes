package br.infnet.marianabs.mybeautynotes.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import br.infnet.marianabs.mybeautynotes.R
import br.infnet.marianabs.mybeautynotes.viewmodel.NoteSaveViewModel
import br.infnet.marianabs.mybeautynotes.model.Notes
import br.infnet.marianabs.mybeautynotes.databinding.FragmentEditNoteBinding
import java.text.SimpleDateFormat
import java.util.*


class EditNoteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    private lateinit var notesSaveViewModel: NoteSaveViewModel
    private var fbind: FragmentEditNoteBinding? = null
    private var note: Notes? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEditNoteBinding.bind(view)
        fbind = binding
        notesSaveViewModel = ViewModelProvider(this).get(NoteSaveViewModel::class.java)

        arguments?.let {
            note = EditNoteFragmentArgs.fromBundle(it).updateNote
            binding.titleEdittext.setText(note?.title)
            binding.descEditText.setText(note?.description)
            if (note != null) {
                binding.lastEditText.text = setDate(note!!.date)
            }

        }

        binding.saveFAB.setOnClickListener {
            val title = binding.titleEdittext.text.toString()
            val desc = binding.descEditText.text.toString()
            if (title.isBlank() and desc.isBlank()) {
                Toast.makeText(activity, "Opss...preencha os campos...", Toast.LENGTH_SHORT).show()
            } else {
                val mNote = Notes(title = title, description = desc)
                if (note == null) {
                    notesSaveViewModel.insertNote(mNote)
                    Toast.makeText(activity, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    mNote.noteId = note!!.noteId
                    mNote.date = System.currentTimeMillis()

                    notesSaveViewModel.updateNote(mNote)
                    Toast.makeText(activity, "Editado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                activity?.let { it1 -> hideKeyboard(it1) }
                Navigation.findNavController(view).navigateUp()

            }
        }
    }

    private fun setDate(date: Long): CharSequence? {
        val currentMilli = System.currentTimeMillis()
        val result:String
        val sdf = SimpleDateFormat("MMM dd")
        val actualDate = Date(date)
        val currentDate = Date(currentMilli)
        result = if (sdf.format(currentDate).toString() != sdf.format(actualDate).toString())
            "Editado em ".plus(sdf.format(actualDate))
        else
            "Editado em ".plus(SimpleDateFormat("hh:mm a").format(actualDate))
        return result
    }

    private fun hideKeyboard(activity: Activity) {
        val inputManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fbind = null
    }
}
