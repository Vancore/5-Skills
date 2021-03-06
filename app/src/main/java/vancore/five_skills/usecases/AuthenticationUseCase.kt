package vancore.five_skills.usecases

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vancore.five_skills.data.FiveSkillsRepository
import javax.inject.Inject

data class AuthenticationState(
    val currentUser: FirebaseUser? = null,
    val loadingState: LoginState = LoginState.LoggedOut,
    val registrationState: RegistrationState = RegistrationState.Idle,
    val errorMessage: String = ""
)

class AuthenticationUseCase @Inject constructor(
    val repository: FiveSkillsRepository
) {
    private var auth = FirebaseAuth.getInstance()

    private val _authenticationState = MutableStateFlow(AuthenticationState())
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState.asStateFlow()

    init {
        _authenticationState.update {
            if (auth.currentUser != null) {
                it.copy(loadingState = LoginState.LoggedIn, currentUser = auth.currentUser)
            } else {
                it.copy(loadingState = LoginState.LoggedOut)
            }
        }
    }

    fun login(email: String, password: String) {
        _authenticationState.update {
            it.copy(loadingState = LoginState.LoggingIn)
        }
        if (validateInput(email, password) == InputValidation.Valid) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let { user ->
                            _authenticationState.update {
                                it.copy(currentUser = user, loadingState = LoginState.LoggedIn)
                            }
                        }
                    } else {
                        val errorMessage = task.exception?.localizedMessage ?: "Login Error"
                        _authenticationState.update {
                            it.copy(errorMessage = errorMessage, loadingState = LoginState.Error)
                        }
                    }
                }
        } else {
            _authenticationState.update {
                it.copy(errorMessage = "Generic input validation error message")
            }
        }
    }

    fun logout() {
        // Can the logout fail?
        auth.signOut()
        _authenticationState.update {
            it.copy(
                currentUser = null,
                loadingState = LoginState.LoggedOut,
                registrationState = RegistrationState.Idle
            )
        }
    }

    private fun validateInput(email: String, password: String): InputValidation {
        return when {
            email.isEmpty() && password.isEmpty() -> InputValidation.InvalidEmailAndPassword
            email.isEmpty() -> InputValidation.InvalidEmail
            password.isEmpty() -> InputValidation.InvalidPassword
            else -> InputValidation.Valid
        }
    }

    fun register(email: String, password: String) {
        _authenticationState.update { it.copy(registrationState = RegistrationState.RegistrationStarted) }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    _authenticationState.update {
                        it.copy(
                            currentUser = auth.currentUser,
                            registrationState = RegistrationState.RegistrationSuccess,
                            loadingState = LoginState.LoggedIn
                        )
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    _authenticationState.update { state ->
                        val errorMessage = task.exception?.localizedMessage ?: "Registration Error"
                        state.copy(
                            loadingState = LoginState.Error,
                            registrationState = RegistrationState.RegistrationFailed,
                            errorMessage = errorMessage
                        )
                    }
                }
            }
    }

    fun addUserToDatabase(firebaseUserId: String, email: String) {
        repository.addFirebaseUser(firebaseUserId = firebaseUserId, email = email)
    }

}

enum class InputValidation {
    InvalidPassword,
    InvalidEmail,
    InvalidEmailAndPassword,
    Valid
}

enum class LoginState {
    LoggedOut,
    LoggedIn,
    LoggingIn,
    Error
}

enum class RegistrationState {
    RegistrationStarted,
    RegistrationSuccess,
    RegistrationFailed,
    Idle
}