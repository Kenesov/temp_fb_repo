package com.example.chatappwithfirebase.domain

import com.example.chatappwithfirebase.data.local.LocalStorage
import com.example.chatappwithfirebase.data.models.GroupData
import com.example.chatappwithfirebase.data.models.ResultData
import com.example.chatappwithfirebase.utils.toTime
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MainRepository(private val fb: FirebaseFirestore, private val rd: FirebaseDatabase) {
    suspend fun getGroupChatsFlow() = flow<ResultData<List<GroupData>>> {
        emit(
            ResultData.Success(fb.collection("groups").get().await().documents.mapNotNull {
                GroupData(
                    it.id, it.data!!["name"].toString()
                )
            })
        )
    }.catch {
        emit(ResultData.Error(it))
    }

    suspend fun addGroup(name: String) = flow {
        val data = mapOf(
            "name" to name
        )
        fb.collection("groups").document().set(data)
        emit(fb.collection("groups").whereEqualTo("name", name).get().await().documents.first().id)
    }.catch {
        it.printStackTrace()
    }

    suspend fun sendMessage(message: String, groupId: String) {
        val time = System.currentTimeMillis()
        val data = mapOf(
            "message" to message,
            "user" to LocalStorage().username,
            "time" to time.toString().toTime()
        )
        rd.getReference(groupId).child(time.toString()).setValue(data)
    }

    suspend fun addGroupToRealtimeDatabase(id: String) {
        rd.getReference(id).setValue(null)
    }

}