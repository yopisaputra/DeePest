package id.com.yopisptr.deepest.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.com.yopisptr.deepest.response.ResponseLogin
import id.com.yopisptr.deepest.response.ResponseRegister
import id.com.yopisptr.deepest.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryUser private constructor(private val dataStore: DataStore<Preferences>,
                                         private val apiService: ApiService
){
    fun register(email: String, password: String):LiveData<Result<ResponseRegister>> = liveData{
        try {
            val result = apiService.register(email, password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }

    }

    fun login(email: String, password: String): LiveData<Result<ResponseLogin>> = liveData {
        try {
            val result = apiService.login(email, password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }
    fun isLogin(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }

    suspend fun setToken(token: String, isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[STATE_KEY] = isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RepositoryUser? = null

        private val TOKEN = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(
            dataStore: DataStore<Preferences>,
            apiService: ApiService
        ): RepositoryUser {
            return INSTANCE ?: synchronized(this) {
                val instance = RepositoryUser(dataStore, apiService)
                INSTANCE = instance
                instance
            }
        }
    }

}