package com.isoft.smarthomegardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ForgetStepThree extends AppCompatActivity {
    private static final int DATA_LOADING_TIME_OUT = 600;
    DatabaseReference reference3;

    Window window;

    EditText NP,Confirm;
    CheckBox SP;
    CardView Done;

    String id, uname, email, pass, etype;

    String UserName;
    String Ck;

    Boolean onetime = true;

    Animation middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_step_three);

        window = ForgetStepThree.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(0,46,43));
        }

        if(onetime){
            Intent intent = getIntent();
            if(intent.getStringExtra("fun") != null){
                UserName = intent.getStringExtra("fun");
            }
            onetime = false;
        }

        NP = findViewById(R.id.NPassword);
        Confirm = findViewById(R.id.rNPassword);
        SP = findViewById(R.id.checkBox2);
        Done = findViewById(R.id.donebtn);

        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.moving);

        NP.setAnimation(middleAnimation);
        Confirm.setAnimation(middleAnimation);
        SP.setAnimation(middleAnimation);
        Done.setAnimation(middleAnimation);

        NP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (! NP.getText().toString().isEmpty()){
                    NP.getBackground().mutate().setColorFilter(Color.rgb(255, 191, 0), PorterDuff.Mode.SRC_ATOP);
                }
                else {
                    NP.getBackground().mutate().setColorFilter(Color.rgb(255,255,255), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

        Confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (! Confirm.getText().toString().isEmpty()){
                    Confirm.getBackground().mutate().setColorFilter(Color.rgb(255, 191, 0), PorterDuff.Mode.SRC_ATOP);
                }
                else {
                    Confirm.getBackground().mutate().setColorFilter(Color.rgb(255,255,255), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
        NP.requestFocus();
    }

    public void show(View view) {
        if(SP.isChecked()){
            NP.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            NP.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void Done(View view) {
        try {
            String NPass = NP.getText().toString();
            String Con = Confirm.getText().toString();
            if(NPass.isEmpty() || Con.isEmpty()) {
                Toast.makeText(getBaseContext(), "Fill All", Toast.LENGTH_SHORT).show();
            } else {
                if (NPass.equals(Con)) {
                    Ck = "no";
                    reference3 = FirebaseDatabase.getInstance().getReference().child("Users").child(UserName);
                    reference3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (Ck.equals("no")) {
                                if (snapshot.child("uname").getValue() != null) {
                                    uname = snapshot.child("uname").getValue().toString();

                                    if (snapshot.child("id").getValue() != null) {
                                        id = snapshot.child("id").getValue().toString();
                                    }
                                    if (snapshot.child("email").getValue() != null) {
                                        email = snapshot.child("email").getValue().toString();
                                    }
                                    if (snapshot.child("pass").getValue() != null) {
                                        pass = NPass;
                                    }
                                    if (snapshot.child("etype").getValue() != null) {
                                        etype = snapshot.child("etype").getValue().toString();
                                    }

                                    new Handler().postDelayed(() -> {
                                        reference3.removeValue();
                                        UserProfile helperClass = new UserProfile(id, uname, email, pass, etype);
                                        reference3.setValue(helperClass);
                                        Ck = "yes";
                                        Toast.makeText(getBaseContext(), "New Password Uploaded", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgetStepThree.this, Login.class);
                                        startActivity(intent);
                                    },DATA_LOADING_TIME_OUT);
                                } else {
                                    Ck = "yes";
                                    Toast.makeText(getBaseContext(), "Data Distorted Please Try Again from Start", Toast.LENGTH_SHORT).show();
                                    NP.setText("");
                                    Confirm.setText("");
                                    NP.requestFocus();
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "Enter Passwords as Correct", Toast.LENGTH_SHORT).show();
                    NP.setText("");
                    Confirm.setText("");
                    NP.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    Confirm.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    NP.requestFocus();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}