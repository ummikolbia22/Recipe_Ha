package com.ummi.recipe_hu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ummi.recipe_hu.databinding.ActivityForgotBinding

class ForgotActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var forgotBinding: ActivityForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotBinding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(forgotBinding.root)
        mAuth = FirebaseAuth.getInstance()
        forgotBinding.btnForgot.setOnClickListener(this)
        forgotBinding.backForgot.setOnClickListener(this)
    }
    companion object {
        fun getLaunchService(from: Context) = Intent(
            from,
            ForgotActivity::class.java
        ).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }
    }


    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_forgot -> forgotPass()
            R.id.back_forgot -> startActivity(Intent(SignInActivity.getLaunchService(this)))
        }


    }

    private fun forgotPass() {
        val email = forgotBinding.etEmailForgot.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getString(R.string.not_Empty), Toast.LENGTH_SHORT).show()
        } else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(SignInActivity.getLaunchService(this)))
                } else {
                    Toast.makeText(
                        this, "Failed reset pass",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}