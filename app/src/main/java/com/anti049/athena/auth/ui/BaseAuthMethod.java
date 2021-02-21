package com.anti049.athena.auth.ui;

import android.content.Context;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;


public abstract class BaseAuthMethod {
    public interface CallbackFunction {
        void SignInComplete(Intent data);
        void SignOutComplete();
        void RevokeAccessComplete();
        void AuthenticationComplete(Object result);
    }

    protected FirebaseAuth mAuth;
    protected Context mContext;
    public int RESULT_CODE;
    public CallbackFunction mCallback;
    public String DEBUG_TAG;

    public BaseAuthMethod(Context context) {
        mContext = context;
        mCallback = null;
        mAuth = FirebaseAuth.getInstance();
        RESULT_CODE = new Random().nextInt(5000);
    }
    private void ThrowNotImplemented() { throw new UnsupportedOperationException("THIS FUNCTION HAS NOT BEEN IMPLEMENTED YET"); }

    public void SignIn() { SignIn(false, null); }
    public void SignIn(boolean signInComplete, Intent data) { ThrowNotImplemented(); }
    public void SignOut() { ThrowNotImplemented(); }
    public void RevokeAccess() { ThrowNotImplemented(); }
    public void Authenticate(String ...parameters) { ThrowNotImplemented(); }
}
