package com.anti049.athena.auth;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.anti049.athena.R;
import com.anti049.athena.auth.ui.BaseAuthMethod;
import com.anti049.athena.auth.ui.GoogleAuthMethod;
import com.anti049.athena.auth.ui.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.*;

public class LoginChoiceActivity extends AppCompatActivity {

    private final String TAG = "LoginChoice";
    private enum LoginType { EMAIL, GOOGLE, FACEBOOK, ANONYMOUS }
    private LoginButton loginEmail;
    private LoginButton loginFacebook;
    private LoginButton loginAnonymous;
    private FirebaseAuth mAuth;
    // Google Authentication
    private LoginButton loginGoogle;
    private GoogleAuthMethod authGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);

        mAuth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.loginEmail);
        loginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(LoginType.EMAIL);
            }
        });
        // Google Authentication Setup
        loginGoogle = findViewById(R.id.loginGoogle);
        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authGoogle.SignIn();
            }
        });
        authGoogle = new GoogleAuthMethod(
                this,
                GoogleSignInOptions.DEFAULT_SIGN_IN,
                getString(R.string.default_web_client_id));
        authGoogle.mCallback = new BaseAuthMethod.CallbackFunction() {
            @Override
            public void SignInComplete(Intent data) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    assert account != null;
                    Log.d(authGoogle.DEBUG_TAG, "Sign In:" + account.getId());
                    authGoogle.Authenticate(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(authGoogle.DEBUG_TAG, "Google sign in failed", e);
                }
            }

            @Override
            public void SignOutComplete() {
                Log.d(authGoogle.DEBUG_TAG, "Sign Out");
            }

            @Override
            public void RevokeAccessComplete() {
                Log.d(authGoogle.DEBUG_TAG, "Revoke Access");
            }

            @Override
            public void AuthenticationComplete(Object result) {
                Task<AuthResult> task = (Task<AuthResult>) result;
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(authGoogle.DEBUG_TAG, "Authentication:success");
                    updateUI(mAuth.getCurrentUser());
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(authGoogle.DEBUG_TAG, "Authentication:failure", task.getException());
                    Toast.makeText(LoginChoiceActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        };

        loginFacebook = findViewById(R.id.loginFacebook);
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(LoginType.FACEBOOK);
            }
        });
        loginAnonymous = findViewById(R.id.loginAnonymous);
        loginAnonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(LoginType.ANONYMOUS);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void Login(LoginType loginType) {
        switch (loginType) {
            case EMAIL:
                Log.d(TAG, "Login: EMAIL");
                return;
            case FACEBOOK:
                Log.d(TAG, "Login: FACEBOOK");
                return;
            case ANONYMOUS:
                Log.d(TAG, "Login: ANONYMOUS");
                return;
            default:
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == authGoogle.RESULT_CODE) {
            authGoogle.SignIn(true, data);
        }
    }

    private void updateUI(FirebaseUser account) {
        if (account != null) {
            supportFinishAfterTransition();
        }
    }
}