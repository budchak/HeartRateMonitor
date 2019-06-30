package com.java.yaroshevich.heartRateMonitor.UI.signin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.java.yaroshevich.heartRateMonitor.App;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.navigation.Navigation;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    private EditText emailET, passwordET;
    private TextInputLayout emailTIL, passwordITL;
    private Button signinButton, withoutSigninButton;
    private TextView startRegTV;

    private ProgressDialog signProgressIndicator;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private String email, password;

    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(R.layout.view_progress);
        dialog = builder.create();

        emailET = findViewById(R.id.input_login_email);
        passwordET = findViewById(R.id.input_login_password);

        emailTIL = findViewById(R.id.input_layout_login_email);
        passwordITL = findViewById(R.id.input_layout_login_password);

        signinButton = findViewById(R.id.sign_in_button);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailET.clearFocus();
                passwordET.clearFocus();

                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                signIn(email, password);
                hideKeybord(v);
                setDialog(true);
            }
        });

        startRegTV = findViewById(R.id.start_registration_text_view);
        startRegTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterView();
            }
        });

        withoutSigninButton = findViewById(R.id.withoutRegistrationButton);
        withoutSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainView();
            }
        });
        initFirebase();
        checkIfSigned();



    }

    private void hideKeybord(View v){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void clearPasswordScreen() {
        passwordET.setText("");
    }

    private void setDialog(boolean show){

        dialog.setCanceledOnTouchOutside(false);
        if (show){
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }



    //Firebase........................................

    void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
    }

    private void checkIfSigned(){
        currentUser = mAuth.getCurrentUser();
        //TODO update ui
    }

    private void signIn(String user, String password){

        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setDialog(false);
                            ((App)getApplicationContext()).setUser(user);
                            startMainView();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            clearPasswordScreen();
                            setDialog(false);
                        }

                        // ...
                    }
                });

    }

    ////..............................................
    private void startRegisterView() {
        Navigation.navigateToRegisterScreen(this);
    }



    private void startMainView() {
        Navigation.navigateToMainScreen(this);
    }
}
