package id.com.yopisptr.deepest.viewmodel

import androidx.lifecycle.ViewModel
import id.com.yopisptr.deepest.repository.RepositoryUser

class ViewModelRegister(private val repositoryUser: RepositoryUser) : ViewModel() {

    fun register(email: String, password: String) =
        repositoryUser.register(email, password)
}