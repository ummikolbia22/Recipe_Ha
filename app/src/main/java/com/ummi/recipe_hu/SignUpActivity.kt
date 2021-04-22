package com.ummi.recipe_hu

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ummi.recipe_hu.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var signupBinding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: DatabaseReference
    private var firebaseUid: String = ""

    companion object {
        fun getLaunchService(from: Context) = Intent(
            from,
            SignUpActivity::class.java
        ).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)
        supportActionBar?.hide()
        signupBinding.ivBack.setOnClickListener(this)
        signupBinding.tvSignIn.setOnClickListener(this)
        signupBinding.btnSignUp.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()

    }

    private fun signUp() {
        val username: String = signupBinding.etUsername.text.toString()
        val email: String = signupBinding.etEmailSignUp.text.toString()
        val password: String = signupBinding.etPassSignUp.text.toString()
        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.not_Empty), Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Toast.makeText(this, getString(R.string.not_Empty), Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.not_Empty), Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUid = mAuth.currentUser!!.uid
                    refUser = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUid)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUid
                    userHashMap["name"] = username
                    userHashMap["email"] = email
                    userHashMap["photo"] = ""

                    refUser.updateChildren(userHashMap).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(MainActivity.getLaunchService(this)))
                            finish()
                        }
                    }
                } else {
                    val progress = ProgressDialog(this, R.style.ThemeOverlay_AppCompat_Dialog)
                    progress.hide()
                    Toast.makeText(
                        this,
                        "Register Failed" + it.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> startActivity(Intent(SignInActivity.getLaunchService(this)))
            R.id.tv_sign_in -> startActivity(Intent(SignInActivity.getLaunchService(this)))
            R.id.btn_sign_up -> signUp()

        }
    }
}