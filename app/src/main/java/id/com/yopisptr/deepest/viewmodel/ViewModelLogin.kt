package id.com.yopisptr.deepest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.com.yopisptr.deepest.repository.RepositoryUser
import kotlinx.coroutines.launch

class ViewModelLogin(private val repositoryUser: RepositoryUser) : ViewModel() {

    fun setToken(token: String, isLogin: Boolean) {
        viewModelScope.launch {
            repositoryUser.setToken(token, isLogin)
        }
    }

    fun getToken(): LiveData<String> {
        return repositoryUser.getToken().asLiveData()
    }

    fun login(email: String, password: String) = repositoryUser.login(email, password)
}