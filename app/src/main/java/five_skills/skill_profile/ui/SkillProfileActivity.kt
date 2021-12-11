package five_skills.skill_profile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.example.all_in_one.R
import com.example.all_in_one.databinding.ActivitySkillProfileBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import five_skills.extensions.hideKeyboard
import five_skills.shared.models.SkillItem
import five_skills.skill_detail.ui.SkillDetailActivity
import five_skills.skill_profile.data.models.LoginValidation
import shared.view.CircularProgressDrawable
import javax.inject.Inject


@AndroidEntryPoint
class SkillProfileActivity : AppCompatActivity(), SkillItemClickListener {

    private lateinit var binding: ActivitySkillProfileBinding

    private var currentSnackbar: Snackbar? = null

    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var viewModel: SkillProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySkillProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupClickListeners() {
        binding.bLogin.setOnClickListener {
            signIn(binding.tietAccountName.text.toString(), binding.tietPassword.text.toString())
            hideKeyboard()
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

        binding.bSaveSkill.setOnClickListener {
            // Add some real skills here! ;)
            viewModel.saveUserSkills(
                listOf(
                    SkillItem("SkillTitle1", "SkillDescription1", 1),
                    SkillItem("SkillTitle2", "SkillDescription2", 2),
                    SkillItem("SkillTitle3", "SkillDescription3", 3),
                    SkillItem("SkillTitle4", "SkillDescription4", 4),
                    SkillItem("SkillTitle5", "SkillDescription5", 5)
                )
            )
        }
    }

    private fun observe() {
        viewModel.isUserOnline.observe(this, { user ->
            // User logged in
            binding.bLogout.isVisible = user != null
            binding.bSaveSkill.isVisible = user != null
            binding.rvUserSkills.isVisible = user != null

            // User not logged in
            binding.bLogin.isVisible = user == null
            binding.bRegister.isVisible = user == null
            binding.tietAccountName.isVisible = user == null
            binding.tilPassword.isVisible = user == null

            if (user != null) {
                // What is user.displayName?
                binding.tvLogin.text = getString(R.string.profile_login_logged_in, user.email)
                viewModel.fetchUserSkills()
            } else {
                binding.tvLogin.text = getString(R.string.profile_login_logged_out)
            }
        })

        viewModel.userSkills.observe(this, { list ->
            binding.rvUserSkills.adapter = SkillsAdapter(list, this)
            binding.rvUserSkills.visibility = View.VISIBLE
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkIfUserIsOnline(auth)
    }

    private fun signIn(email: String, password: String) {
        val tag = "SkillProfileActivity"
        val drawable = showProgressOnButton(binding.bLogin)
        viewModel.validateInput(email, password)
        viewModel.inputValidation.observe(this, { validation ->
            if (validation == LoginValidation.Valid) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        hideProgressButton(binding.bLogin, drawable)
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
                            showSnackBar(task.exception?.localizedMessage)
                            // Update from ViewModel
                            viewModel.onLoginFailed()
                            //updateUI(user)
                        }
                    }
            } else {
                showSnackBar(validation.name)
                hideProgressButton(binding.bLogin, drawable)
            }
        })
    }

    private fun showProgressOnButton(button: ExtendedFloatingActionButton): CircularProgressDrawable {
        val drawable = CircularProgressDrawable(this)
        // With normal MaterialButton it's currently not working, because it does not redraw
        // https://github.com/material-components/material-components-android/issues/1209
        // binding.bLogin.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        button.icon = drawable
        drawable.start()
        return drawable
    }

    private fun hideProgressButton(
        button: ExtendedFloatingActionButton,
        drawable: CircularProgressDrawable
    ) {
        button.icon = null
        drawable.stop()
    }

    private fun signOut() {
        val drawable = showProgressOnButton(binding.bLogout)
        auth.signOut()
        auth.addAuthStateListener {
            if (it.currentUser == null) {
                viewModel.onSignOut()
                hideProgressButton(binding.bLogout, drawable)
            }
        }
    }

    private fun createUserWithMailAndPassword(email: String, password: String) {
        val drawable = showProgressOnButton(binding.bRegister)
        viewModel.validateInput(email, password)
        viewModel.inputValidation.observe(this, { validation ->
            if (validation == LoginValidation.Valid) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        hideProgressButton(binding.bRegister, drawable)
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success")
                            val user = auth.currentUser
                            // Update from ViewModel
                            viewModel.onUserCreationSuccessFully(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            //Update from ViewModel
                            //updateUI(null)
                            viewModel.onUserCreationFailed()
                        }
                    }
            } else {
                showSnackBar(validation.name)
                hideProgressButton(binding.bRegister, drawable)
            }
        })
    }

    private fun showSnackBar(text: String?) {
        dismissCurrentErrorSnackbar()

        findViewById<CoordinatorLayout>(R.id.snackbar_container).let { container ->
            val backgroundColor = ResourcesCompat.getColor(
                resources,
                R.color.snackbar_error,
                theme
            )
            val snackbar = Snackbar.make(container, text ?: "Error", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(backgroundColor)

            val snackbarView = snackbar.view
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 3
            snackbar.show()
            currentSnackbar = snackbar
        }
    }

    private fun dismissCurrentErrorSnackbar() {
        if (currentSnackbar?.isShownOrQueued == true) {
            currentSnackbar?.dismiss()
        }
    }

    override fun onSkillItemClicked(item: SkillItem) {
        showSnackBar("SkillItem clicked: ${item.skillTitle}")
        val intent = Intent(this, SkillDetailActivity::class.java)
        startActivity(intent)
    }

}