package com.example.chatappwithfirebase.ui.chat

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chatappwithfirebase.R
import com.example.chatappwithfirebase.data.models.MessageData
import com.example.chatappwithfirebase.databinding.ScreenChatBinding
import com.example.chatappwithfirebase.presentation.chats.ChatViewModel
import com.example.chatappwithfirebase.ui.adapters.ChatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import kotlin.random.Random

class ChatScreen : Fragment(R.layout.screen_chat) {

    private val binding by viewBinding(ScreenChatBinding::bind)
    private val args: ChatScreenArgs by navArgs()
    private var _adapter: ChatAdapter? = null
    private val adapter get() = _adapter!!

    private lateinit var viewModel: ChatViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        FirebaseDatabase.getInstance().getReference(args.groupId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val list = mutableListOf<MessageData>()
                        snapshot.children.forEach {
                            val item = it.value as HashMap<*, *>
                            list.add(
                                MessageData(
                                    item["message"].toString(),
                                    item["user"].toString(),
                                    item["time"].toString()
                                )
                            )
                        }
                        adapter.submitList(list)
                        if (list.isNotEmpty()) {
                            binding.rvChat.smoothScrollToPosition(list.lastIndex)
                            val audio =
                                MediaPlayer.create(requireContext(), R.raw.new_message_sound)
                            audio.start()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun initListeners() {
        binding.icBack.clicks().debounce(200).onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)

        binding.etMessage.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                binding.icAttach.visibility = View.GONE
                binding.icVideo.visibility = View.GONE
                binding.icSend.visibility = View.VISIBLE
            } else {
                binding.icAttach.visibility = View.VISIBLE
                binding.icVideo.visibility = View.VISIBLE
                binding.icSend.visibility = View.GONE
            }
        }

        binding.icSend.clicks().debounce(200).onEach {
            val message = binding.etMessage.text.toString()
            if (it.toString().isNotEmpty()) {
                viewModel.sendMessage(message, args.groupId)
                binding.etMessage.setText("")
            } else {
                binding.icAttach.visibility = View.VISIBLE
                binding.icVideo.visibility = View.VISIBLE
                binding.icSend.visibility = View.GONE
            }
        }.launchIn(lifecycleScope)


    }

    private fun initData() {
        binding.tvGroupName.text = args.groupName
        binding.tvGroupUserCount.text = "${(Random.nextInt(1, 10000))} участников"
        _adapter = ChatAdapter()
        binding.rvChat.adapter = adapter

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ChatViewModel::class.java)
    }
}