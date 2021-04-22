package com.ummi.recipe_hu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ummi.recipe_hu.databinding.ActivitySignInBinding


class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signinBinding: ActivitySignInBinding

    companion object {
        fun getLaunchService(from: Context) = Intent(
            from,
            SignInActivity::class.java
        ).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signinBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signinBinding.root)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        signinBinding.tvForget.setOnClickListener(this)
        signinBinding.tvSign.setOnClickListener(this)
        signinBinding.tvSignup.setOnClickListener(this)
    }

    private fun signIn() {
        val email = signinBinding.tvEmail.text.toString()
        val password = signinBinding.tvPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.not_Empty),
                Toast.LENGTH_SHORT
            ).show()
        }

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this, "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(MainActivity.getLaunchService(this))
                    return@addOnCompleteListener
                } else {
                    Toast.makeText(
                        this, "Login Gagal",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Login Gagal",
                    Toast.LENGTH_SHORT).show()
            }


    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            startActivity(MainActivity.getLaunchService(this))
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_signin -> signIn()
            R.id.tv_signup -> startActivity(Intent(SignUpActivity.getLaunchService(this )))
            R.id.tv_forget -> startActivity(Intent(ForgotActivity.getLaunchService(this)))
        }
    }
}