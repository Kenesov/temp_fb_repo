package com.example.chatappwithfirebase.ui.groups

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chatappwithfirebase.R
import com.example.chatappwithfirebase.databinding.ScreenGroupsBinding
import com.example.chatappwithfirebase.presentation.groups.GroupsViewModel
import com.example.chatappwithfirebase.ui.adapters.GroupAdapter
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks

class GroupScreen : Fragment(R.layout.screen_groups) {

    private lateinit var viewModel: GroupsViewModel
    private var _adapter: GroupAdapter? = null
    private val adapter get() = _adapter!!

    private val binding by viewBinding(ScreenGroupsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[GroupsViewModel::class.java]


        initData()
        initObservers()
        initListeners()

    }

    private fun initListeners() {
        val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        adapter.setOnItemClickListener {
            findNavController().navigate(
                GroupScreenDirections.actionGroupScreenToChatScreen(
                    it.name, it.id
                )
            )
        }

        binding.icAddGroup.clicks().debounce(200).onEach {
            findNavController().navigate(GroupScreenDirections.actionGroupScreenToScreenAddGroup())
        }.launchIn(lifecycleScope)


        binding.icMenu.clicks().debounce(200).onEach {
            drawer.open()
        }.launchIn(lifecycleScope)

    }

    private fun initObservers() {
        viewModel.getGroupChatsFlow.onEach {
            adapter.models = it
        }.launchIn(lifecycleScope)
    }

    private fun initData() {
        _adapter = GroupAdapter()
        binding.rvGroups.adapter = adapter

        lifecycleScope.launchWhenResumed {
            viewModel.getGroupsChats()
        }
    }
}