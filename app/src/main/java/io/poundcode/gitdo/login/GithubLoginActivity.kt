package io.poundcode.gitdo.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import io.poundcode.gitdo.R
import kotlinx.android.synthetic.main.activity_github_login.*

class GithubLoginActivity : AppCompatActivity() {

    private var mFirebaseAuth:FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_login)
        placeHolder.text = ""
        mFirebaseAuth = FirebaseAuth.getInstance()

    }
}
