package com.anti049.athena.auth.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.anti049.athena.R;

public class LoginButton extends ConstraintLayout {
    private ImageView loginIcon;
    private TextView loginText;
    private ConstraintLayout loginContents;

    public LoginButton(Context context) {
        super(context);
        Initialize(context, null, 0);
    }
    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Initialize(context, attrs, 0);
    }
    public LoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Initialize(context, attrs, defStyle);
    }

    private void Initialize(Context context, AttributeSet attrs, int defStyle) {
        inflate(context, R.layout.login_button, this);

        loginIcon = findViewById(R.id.loginIcon);
        loginText = findViewById(R.id.loginText);
        loginContents = findViewById(R.id.loginContents);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoginButton, defStyle, 0);

        if (a.hasValue(R.styleable.LoginButton_loginIcon))
            loginIcon.setImageResource(a.getResourceId(R.styleable.LoginButton_loginIcon, R.drawable.icon_user));
        else
            loginIcon.setImageResource(R.drawable.icon_user);
        if (a.hasValue(R.styleable.LoginButton_loginText))
            loginText.setText(a.getString(R.styleable.LoginButton_loginText));
        else
            loginText.setText(R.string.login_default);
        if (a.hasValue(R.styleable.LoginButton_loginColor))
            loginContents.setBackgroundTintList(ColorStateList.valueOf(a.getColor(R.styleable.LoginButton_loginColor, R.color.colorForeground)));
        else
            loginContents.setBackgroundTintList(ColorStateList.valueOf(R.color.colorForeground));
        if (a.hasValue(R.styleable.LoginButton_loginTextColor))
            loginText.setTextColor(a.getColor(R.styleable.LoginButton_loginTextColor, R.color.colorBackgroundBase));
        else
            loginText.setTextColor(R.color.colorBackgroundBase);
        if (a.hasValue(R.styleable.LoginButton_loginTint))
            loginIcon.setImageTintList(ColorStateList.valueOf(a.getColor(R.styleable.LoginButton_loginTint, 0)));

        setClipToOutline(true);
    }
}
