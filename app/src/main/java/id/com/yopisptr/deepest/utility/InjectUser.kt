package id.com.yopisptr.deepest.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import id.com.yopisptr.deepest.repository.RepositoryUser
import id.com.yopisptr.deepest.retrofit.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object InjectUser {
    fun provideRepository(context: Context): RepositoryUser {
        val apiService = ApiConfig.getApiService()
        return RepositoryUser.getInstance(context.dataStore, apiService)
    }
}