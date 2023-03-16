package com.example.chatappwithfirebase.di

import com.example.chatappwithfirebase.data.remote.TodoApi
import com.example.chatappwithfirebase.domain.MainRepository
import com.example.chatappwithfirebase.presentation.chats.ChatViewModel
import com.example.chatappwithfirebase.presentation.groups.AddGroupViewModel
import com.example.chatappwithfirebase.presentation.groups.GroupsViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single<MainRepository> {
        MainRepository(fb = get(), rd = get(), api = get())
    }


    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }


    single<FirebaseDatabase> {
        FirebaseDatabase.getInstance()
    }

    single<TodoInterceptor> {
        TodoInterceptor()
    }


    single<TodoApi> {
        provideApi(retrofit = get())
    }


    single<Retrofit> {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor = get()).build()

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://todo.paydali.uz").client(client).build()
        retrofit
    }
}

val viewModelModule = module {
    viewModel<ChatViewModel> {
        ChatViewModel(repo = get())
    }

    viewModel<AddGroupViewModel> {
        AddGroupViewModel(repo = get())
    }


    viewModel<GroupsViewModel> {
        GroupsViewModel(repo = get())
    }

}


val remoteModule = module {

}

fun provideApi(retrofit: Retrofit): TodoApi {
    return retrofit.create(TodoApi::class.java)
}