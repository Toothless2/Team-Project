package com.group7.unveil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author M Rose
 * Homepage activity for app, contains google login handlers
 */
class MainActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //startActivity(Intent(this, MainPage::class.java))



            val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
            signInButton.setSize(SignInButton.SIZE_STANDARD)

            signInButton.setOnClickListener { v ->
                when (v.id) {
                    R.id.sign_in_button -> signIn()
                }
            }


            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        }

      /*  @Override
        protected void onStart() {
            super.onStart();
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        }*/


        private fun signIn() {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }


        private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
            try {
                val account = completedTask.getResult<ApiException>(ApiException::class.java!!)


                val intent = Intent(this@MainActivity, MainPage::class.java)
                startActivity(intent)
            } catch (e: ApiException) {

                val TAG = "Error"
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)

            }

        }
    }


