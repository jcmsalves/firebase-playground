package com.jcmsalves.firebaseplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_authenticated_user.*

class AuthenticatedUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticated_user)

        button_logout.setOnClickListener { _ ->
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    Toast.makeText(this, "User signed out", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }

        button_delete_account.setOnClickListener { _ ->
            AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener {
                    Toast.makeText(this, "User account deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}
