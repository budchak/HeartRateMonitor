package com.java.yaroshevich.heartRateMonitor.UI.signin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.base.BaseActivity;
import com.java.yaroshevich.heartRateMonitor.UI.navigation.Navigation;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegActivity";


    private Toolbar toolbar;
    private FirebaseUser currentUser;

    private EditText emailET, passwordET, confirmPasswordET;
    private TextInputLayout emailTIL, passwordITL, confirmPasswordITL;
    private Button signupButton;

    private FirebaseAuth mAuth;

    private String email, password, confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailET = findViewById(R.id.input_registration_email);
        passwordET = findViewById(R.id.input_register_password);
        confirmPasswordET = findViewById(R.id.input_register_confirm_password);


        emailTIL = findViewById(R.id.input_layout_registration_email);
        passwordITL = findViewById(R.id.input_layout_register_password);
        emailTIL = findViewById(R.id.input_layout_register_confirm_password);


        signupButton = findViewById(R.id.sign_up_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailET.clearFocus();
                passwordET.clearFocus();
                confirmPasswordET.clearFocus();



                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                confirmPassword = confirmPasswordET.getText().toString();

                if (password.equals(confirmPassword)){
                    hideKeyboard(v);
                    createNewUser(email, password);

                }else {
                    showToast("пороли не совпадают");
                    clearPasswordScreen();
                    hideKeyboard(v);
                }
            }
        });


        configToolbar();
        initFirebase();
        checkIfSigned();

    }

    private void hideKeyboard(View v){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void clearPasswordScreen() {
        passwordET.setText("");
        confirmPasswordET.setText("");
    }


    public void configToolbar() {
        toolbar = findViewById(R.id.toolbar_actionbar);
        setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginScreen();
            }
        });
    }


    public void startLoginScreen() {
        Navigation.navigateToLoginScreen(this);
    }

    private void startMainScreen() {
       Navigation.navigateToMainScreen(this);
    }


    //Firebase........................................

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void checkIfSigned() {
        currentUser = mAuth.getCurrentUser();
        //TODO update ui
    }

    private void createNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            showToast("успешная регистрация");
                            startMainScreen();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }


    ////..............................................

}


