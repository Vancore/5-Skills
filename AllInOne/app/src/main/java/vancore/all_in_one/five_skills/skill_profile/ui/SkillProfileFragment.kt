package vancore.all_in_one.five_skills.skill_profile.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.all_in_one.R
import com.example.all_in_one.databinding.FragmentSkillProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SkillProfileFragment : Fragment() {

    private var _binding: FragmentSkillProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    @Inject
    lateinit var viewModel: SkillProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        viewModel.isUserOnline.observe(viewLifecycleOwner, { user ->
            binding.bLogin.isVisible = user == null
            binding.bRegister.isVisible = user == null
            binding.bLogout.isVisible = user != null

            if (user != null) {
                // What is user.displayName?
                binding.tvLogin.text = getString(R.string.profile_login_logged_in, user.email)
                binding.tietAccountName.visibility = View.GONE
                binding.tilPassword.visibility = View.GONE
            } else {
                binding.tvLogin.text = getString(R.string.profile_login_logged_out)
                binding.tietAccountName.visibility = View.VISIBLE
                binding.tilPassword.visibility = View.VISIBLE
            }
        })

        binding.bLogin.setOnClickListener {
            signIn(binding.tietAccountName.text.toString(), binding.tietPassword.text.toString())
        }

        binding.bLogout.setOnClickListener {
            signOut()
        }

        binding.bRegister.setOnClickListener {
            createUserWithMailAndPassword(
                binding.tietAccountName.text.toString(),
                binding.tietPassword.text.toString()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.doSomething()
        viewModel.checkIfUserIsOnline(auth)
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(tag, "signInWithEmail:success")
                    val user = auth.currentUser
                    // Update from ViewModel
                    viewModel.onLoginSuccessful(user)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(tag, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Update from ViewModel
                    viewModel.onLoginFailed()
                    //updateUI(user)
                }
            }
    }

    private fun signOut() {
        auth.signOut()
        auth.addAuthStateListener {
            if (it.currentUser == null) viewModel.onSignOut()
        }
    }

    private fun createUserWithMailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    // Update from ViewModel
                    viewModel.onUserCreationSuccessFully(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Update from ViewModel
                    //updateUI(null)
                    viewModel.onUserCreationFailed()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}