package id.com.yopisptr.deepest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.com.yopisptr.deepest.repository.RepositoryUser
import kotlinx.coroutines.launch

class ViewModelMain(private val repositoryUser: RepositoryUser): ViewModel() {
    fun getToken(): LiveData<String> {
        return repositoryUser.getToken().asLiveData()
    }

    fun isLogin(): LiveData<Boolean> {
        return repositoryUser.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repositoryUser.logout()
        }
    }

}