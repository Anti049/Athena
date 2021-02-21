package com.anti049.athena.auth.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.anti049.athena.auth.LoginChoiceActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class GoogleAuthMethod extends BaseAuthMethod {

    private GoogleSignInClient mSignInClient;

    public GoogleAuthMethod(Context context, GoogleSignInOptions options, String token) {
        super(context);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(options)
                .requestIdToken(token)
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(context, gso);
        DEBUG_TAG = "GoogleAuthMethod";
    }

    @Override
    public void SignIn(boolean signInComplete, Intent data) {
        if (!signInComplete) {
            Intent signInIntent = mSignInClient.getSignInIntent();
            ((Activity)mContext).startActivityForResult(signInIntent, this.RESULT_CODE);
        } else {
            mCallback.SignInComplete(data);
        }
    }

    @Override
    public void SignOut() {
        mAuth.signOut();
        mSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mCallback.SignOutComplete();
            }
        });
    }

    @Override
    public void RevokeAccess() {
        mAuth.signOut();
        mSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { mCallback.RevokeAccessComplete();
            }
        });
    }

    @Override
    public void Authenticate(String ...parameters) {
        String idToken = parameters[0];
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mCallback.AuthenticationComplete(task);
            }
        });
    }
}
