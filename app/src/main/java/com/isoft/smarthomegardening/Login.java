package com.isoft.smarthomegardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT_LOGIN = 1000;
    DatabaseReference reference;

    Window window;
    Dialog dialog;

    CardView Login;
    ProgressBar LoginWait;

    EditText Uname, Password;
    ImageView eye;

    String Opass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!isNetworkAvailable()) {
            dialog = new Dialog(com.isoft.smarthomegardening.Login.this);
            dialog.setContentView(R.layout.no_internet);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
            }

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            final float width = metrics.widthPixels;

            dialog.getWindow().setLayout((int) width - 50, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

            TextView txt_ok = dialog.findViewById(R.id.tv_ok);

            txt_ok.setOnClickListener(v -> {
                finish();
                System.exit(0);
                dialog.dismiss();
            });
            dialog.show();
        }

        window = com.isoft.smarthomegardening.Login.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(0, 46, 43));
        }

        Uname = findViewById(R.id.PhoneNo_or_Email);
        Password = findViewById(R.id.Password);
        eye = findViewById(R.id.toggle);

        Login = findViewById(R.id.Log_In_Btn);
        LoginWait = findViewById(R.id.login_wait_pro_bar);

        Uname.requestFocus();

        Uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Uname.getText().toString().isEmpty()) {
                    Uname.setBackgroundResource(R.drawable.navy_outline);
                }
            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Password.getText().toString().isEmpty()) {
                    Password.setBackgroundResource(R.drawable.navy_outline);
                }
            }
        });

        Uname.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (Uname.getText().toString().isEmpty()) {
                    Uname.setBackgroundResource(R.drawable.red_outline);
                }
            }
        });

        Password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (Password.getText().toString().isEmpty()) {
                    Password.setBackgroundResource(R.drawable.red_outline);
                }
            }
        });

        eye.setOnClickListener(v -> {
            if (eye.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.togglenn).getConstantState())) {
                eye.setImageResource(R.drawable.visibilitynn);
                Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                eye.setImageResource(R.drawable.togglenn);
                Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

        });
    }

    public boolean isNetworkAvailable() {
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(com.isoft.smarthomegardening.Login.this);
        View mView = getLayoutInflater().inflate(R.layout.exit_layout, null);

        CardView btn_yes = mView.findViewById(R.id.btn_yes);
        TextView tv_yes = mView.findViewById(R.id.tv_yes);
        CardView btn_no = mView.findViewById(R.id.btn_no);
        TextView tv_no = mView.findViewById(R.id.tv_no);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        tv_no.setOnClickListener(v -> alertDialog.dismiss());

        btn_no.setOnClickListener(v -> alertDialog.dismiss());

        tv_yes.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

        btn_yes.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
        alertDialog.show();
    }

    public void log(View view) {

        if (Uname.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Fill All", Toast.LENGTH_SHORT).show();
        } else {
            Login.setVisibility(View.INVISIBLE);
            LoginWait.setVisibility(View.VISIBLE);

            String Name = Uname.getText().toString();
            String Pass = Password.getText().toString();

            try {
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(Name);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("uname").getValue() != null ){
                            Opass = snapshot.child("pass").getValue().toString();
                            new Handler().postDelayed(() -> {
                                if(Opass.equals(Pass)){
                                    Uname.setBackgroundResource(R.drawable.navy_outline);
                                    Password.setBackgroundResource(R.drawable.navy_outline);
                                    Login.setVisibility(View.VISIBLE);
                                    LoginWait.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(Login.this, ControlView.class);
                                    intent.putExtra("LUser",Name);
                                    startActivity(intent);
                                    Uname.requestFocus();
                                    Uname.setText("");
                                    Password.setText("");
                                } else {
                                    Login.setVisibility(View.VISIBLE);
                                    LoginWait.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getBaseContext(), "Wrong User name or Password", Toast.LENGTH_SHORT).show();
                                    Uname.setText("");
                                    Password.setText("");
                                    Uname.setBackgroundResource(R.drawable.red_outline);
                                    Password.setBackgroundResource(R.drawable.red_outline);
                                    Uname.requestFocus();
                                }
                            },SPLASH_TIME_OUT_LOGIN);
                        } else{
                            Login.setVisibility(View.VISIBLE);
                            LoginWait.setVisibility(View.INVISIBLE);
                            Toast.makeText(getBaseContext(), "Wrong User name or Password", Toast.LENGTH_SHORT).show();
                            Uname.setText("");
                            Password.setText("");
                            Uname.setBackgroundResource(R.drawable.red_outline);
                            Password.setBackgroundResource(R.drawable.red_outline);
                            Uname.requestFocus();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
            } catch (Exception e) {
                Login.setVisibility(View.VISIBLE);
                LoginWait.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "Login Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void forget(View view) {
        Intent intent = new Intent(Login.this, ForgetStepOne.class);
        startActivity(intent);
    }

    public void createAC(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

}