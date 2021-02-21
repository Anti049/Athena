package com.anti049.athena.auth.ui;

import android.app.Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public interface AuthMethod {
    void SignIn(Activity activity, int returnCode);
    void SignOut(OnCompleteListener<Void> listener);
    void RevokeAccess(OnCompleteListener<Void> listener);
    void Authenticate(OnCompleteListener<Void> listener, String ...parameters);
}
