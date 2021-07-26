package vancore.all_in_one.five_skills.skill_profile.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.all_in_one.databinding.FragmentSkillProfileBinding
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
    lateinit var profileViewModel: SkillProfileViewModel

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

        profileViewModel.isUserOnline.observe(viewLifecycleOwner, {
            binding.bLogin.isVisible = !it
            binding.bRegister.isVisible = !it
            binding.bLogout.isVisible = it
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
        profileViewModel.doSomething()
        profileViewModel.checkIfUserIsOnline(auth)
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(tag, "signInWithEmail:success")
                    val user = auth.currentUser
                    // Update from ViewModel
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(tag, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Update from ViewModel
                    //updateUI(user)
                }
            }
    }

    private fun signOut() {
        auth.signOut()
    }

    private fun createUserWithMailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    // Update from ViewModel
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Update from ViewModel
                    //updateUI(null)
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