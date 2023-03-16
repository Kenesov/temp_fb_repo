package com.example.chatappwithfirebase.ui.groups

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chatappwithfirebase.R
import com.example.chatappwithfirebase.databinding.ScreenAddGroupBinding
import com.example.chatappwithfirebase.presentation.groups.AddGroupViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ldralighieri.corbind.view.clicks

class ScreenAddGroup : Fragment(R.layout.screen_add_group) {
    private val binding by viewBinding(ScreenAddGroupBinding::bind)
    private val viewModel by viewModel<AddGroupViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.addGroupSuccessFlow.onEach {
            Snackbar.make(
                requireView(), "Group was succesfully created.", Snackbar.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }

    private fun initListeners() {
        binding.icDone.clicks().debounce(200).onEach {
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                viewModel.addGroup(name)
            } else {
                Snackbar.make(
                    requireView(), "Please enter name of your group!", Snackbar.LENGTH_SHORT
                ).show()
            }
        }.launchIn(lifecycleScope)

        binding.icBack.clicks().debounce(200).onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }
}