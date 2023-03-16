package com.example.chatappwithfirebase.presentation.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappwithfirebase.domain.MainRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatViewModel(private val repo: MainRepository) : ViewModel() {
    suspend fun sendMessage(message: String, groupId: String) {
        repo.sendMessage(message, groupId)
    }

}