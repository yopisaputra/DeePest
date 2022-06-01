package id.com.yopisptr.deepest.modelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.com.yopisptr.deepest.repository.RepositoryUser
import id.com.yopisptr.deepest.utility.InjectUser
import id.com.yopisptr.deepest.viewmodel.ViewModelLogin
import id.com.yopisptr.deepest.viewmodel.ViewModelRegister

class ModelFactoryAuth(private val repositoryUser: RepositoryUser): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ViewModelRegister::class.java) -> {
                ViewModelRegister(repositoryUser) as T
            }
            modelClass.isAssignableFrom(ViewModelLogin::class.java) -> {
                ViewModelLogin(repositoryUser) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ModelFactoryAuth? = null
        fun getInstance(context: Context): ModelFactoryAuth =
            instance ?: synchronized(this) {
                instance ?: ModelFactoryAuth(InjectUser.provideRepository(context))
            }.also { instance = it }
    }
}