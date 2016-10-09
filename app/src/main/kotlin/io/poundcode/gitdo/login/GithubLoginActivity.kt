package io.poundcode.gitdo.login

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import io.poundcode.gitdo.R
import kotlinx.android.synthetic.main.activity_github_login.*

class GithubLoginActivity : AppCompatActivity() {

    private var mFirebaseAuth:FirebaseAuth? = null
    private var mFirebaseAuthListener : FirebaseAuth.AuthStateListener? = null
    private val CLIENT_ID = "c48ac49f953ace86dbac"
    private val REDIRECT_UI= "https://gitdo-4d397.firebaseapp.com/__/auth/handler"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_login)
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            var user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                //TODO log into firebase
                Log.d("", "onAuthStateChanged:signed_in:" + user.uid);
            } else {
                // User is signed out
                Log.d("", "onAuthStateChanged:signed_out");
            }
        }
        button_githublogin_login.setOnClickListener {
            val githubLoginIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_UI"))
            startActivity(githubLoginIntent)

        }

    }

    override fun onStart() {
        super.onStart()
        mFirebaseAuth?.addAuthStateListener(mFirebaseAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mFirebaseAuthListener != null) {
            mFirebaseAuth?.removeAuthStateListener(mFirebaseAuthListener as FirebaseAuth.AuthStateListener)
        }
    }
}
