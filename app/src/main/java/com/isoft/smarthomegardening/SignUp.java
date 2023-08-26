package com.isoft.smarthomegardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUp extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT_UNPACK = 2000;
    private static final int SPLASH_TIME_OUT_SIGNUP = 2000;

    FirebaseDatabase rootNode1;
    DatabaseReference reference1;
    DatabaseReference referenceid;

    Window window;

    ImageView Right;
    ProgressBar signProBar,veryProBar;
    CardView SignUp, E_verify;

    LinearLayout email_Linear;
    EditText id, Uname, email, Password, rPassword;

    String  SignId, SignName, SignEmail, Sign_Pass, Sign_rPass, Etype;

    Boolean very_email=false, SignPass=false, CheckingUserName=false;

    String JustNowEnterUName="null", idkey = "null", idcount, idvalue;

    Integer FormLoad=0;
    String UserEpin, Code, FirstTime = "false";
    String isUnpack="null";

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    CheckBox showp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        window = SignUp.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(0,46,43));
        }

        E_verify = findViewById(R.id.E_verify);
        signProBar = findViewById(R.id.signProBar);
        veryProBar = findViewById(R.id.veryProBar);
        Right = findViewById(R.id.right);
        SignUp = findViewById(R.id.signbtn);
        id = findViewById(R.id.potID);
        Uname = findViewById(R.id.Uname);
        email = findViewById(R.id.Email);
        email_Linear = findViewById(R.id.email_Linear);
        Password = findViewById(R.id.Password);
        rPassword = findViewById(R.id.rPassword);
        showp = findViewById(R.id.checkBox1);

        // Form Load Time
        if(FormLoad==0){
            id.requestFocus();
            FormLoad++;
        }

        id.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (id.getText().toString().isEmpty()) {
                    id.setBackgroundResource(R.drawable.signup_data_outline_red);
                    Toast.makeText(getBaseContext(), "Enter Pot ID", Toast.LENGTH_SHORT).show();
                } else {
                    reference1 = FirebaseDatabase.getInstance().getReference().child("ID_Collection");
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (int i = 1; i < 6; i++) {
                                idcount = String.valueOf(i);
                                if (snapshot.child(idcount).getValue() != null) {
                                    if(! snapshot.child(idcount).getValue().toString().equals("null")) {
                                        if(! snapshot.child(idcount).getValue().toString().equals(String.valueOf(id.getText()))) {
                                            idkey = "null";
                                        } else {
                                            idkey = idcount;
                                            break;
                                        }
                                    }
                                }
                            }
                            if(idkey.equals("null")){
                                Toast.makeText(getBaseContext(), "Invalid or Expire Id", Toast.LENGTH_SHORT).show();
                                id.setText("");
                                id.requestFocus();
                                id.setBackgroundResource(R.drawable.signup_data_outline_red);
                            } else {
                                idvalue = snapshot.child(idkey).getValue().toString();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });
                }
            }
        });

        Uname.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (Uname.getText().toString().isEmpty()) {
                    Uname.setBackgroundResource(R.drawable.signup_data_outline_red);
                    Toast.makeText(getBaseContext(), "Enter User Name", Toast.LENGTH_SHORT).show();
                    CheckingUserName = true;
                } else {
                    veryProBar.setVisibility(View.VISIBLE);
                    String Check_Name = Uname.getText().toString();

                    reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Check_Name);
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("uname").getValue() != null) {
                                if(!Check_Name.equals(JustNowEnterUName)) {
                                    Toast.makeText(getBaseContext(), "User Name Already Exit", Toast.LENGTH_SHORT).show();
                                    Uname.setText("");
                                    Uname.requestFocus();
                                    Uname.setBackgroundResource(R.drawable.signup_data_outline_red);
                                    email_Linear.setBackgroundResource(R.drawable.signup_data_outline);
                                    Password.setBackgroundResource(R.drawable.signup_data_outline);
                                    rPassword.setBackgroundResource(R.drawable.signup_data_outline);
                                    showp.setChecked(false);
                                }
                            } else {
                                CheckingUserName = true;
                                JustNowEnterUName = Check_Name;
                            }
                            veryProBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });
                }
            }
        });

        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (very_email.equals(false)) {
                    veryProBar.setVisibility(View.VISIBLE);
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    EmailVerification();
                }
            }
        });

        Password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (Password.getText().toString().isEmpty()) {
                    Password.setBackgroundResource(R.drawable.signup_data_outline_red);
                    Toast.makeText(getBaseContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (rPassword.getText().toString().isEmpty()) {
                    rPassword.setBackgroundResource(R.drawable.signup_data_outline_red);
                    Toast.makeText(getBaseContext(), "Confirm Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!id.getText().toString().isEmpty()) {
                    id.setBackgroundResource(R.drawable.signup_data_outline);
                }
            }
        });

        Uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Uname.getText().toString().isEmpty()){
                    CheckingUserName = false;
                    Uname.setBackgroundResource(R.drawable.signup_data_outline);
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!email.getText().toString().isEmpty()){
                    email_Linear.setBackgroundResource(R.drawable.signup_data_outline);
                }
                E_verify.setVisibility(View.INVISIBLE);
                Right.setVisibility(View.INVISIBLE);
                very_email = false;
            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Password.getText().toString().isEmpty()){
                    Password.setBackgroundResource(R.drawable.signup_data_outline);
                }
            }
        });

        rPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!rPassword.getText().toString().isEmpty()){
                    rPassword.setBackgroundResource(R.drawable.signup_data_outline);
                }
            }
        });
    }

    public Boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void EmailVerification(){
        if (email.getText().toString().isEmpty()){
            veryProBar.setVisibility(View.INVISIBLE);
            email_Linear.setBackgroundResource(R.drawable.signup_data_outline_red);
            Toast.makeText(getBaseContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
        } else {
            email.setText(String.format("%s@",email.getText().toString()));
            int Position = email.getText().toString().indexOf("@");
            String SubEmail = email.getText().toString().substring(0, Position);
            email.setText(SubEmail);

            Random random = new Random();
            Code = String.format("%04d", random.nextInt(10000));

            final String agentMail = "smartplantpot1@gmail.com";
            final String agentPassword = "lixewjjdpdojpgsa";
            String Message_Code = String.format("Email Verification Code is %s", Code);

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(agentMail, agentPassword);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(agentMail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.format("%s@gmail.com",email.getText().toString())));
                message.setSubject("Email Verification");
                message.setText(Message_Code);
                Transport.send(message);
                Toast.makeText(getBaseContext(), Code, Toast.LENGTH_SHORT).show();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }


            final AlertDialog.Builder alert = new AlertDialog.Builder(SignUp.this);
            View mView = getLayoutInflater().inflate(R.layout.verify, null);

            CardView btn_verify = mView.findViewById(R.id.btn_verify);
            TextView tv_verify = mView.findViewById(R.id.tv_verify);
            CardView btn_cancel = mView.findViewById(R.id.btn_cancel);
            TextView tv_cancel = mView.findViewById(R.id.tv_cancel);
            TextView Title = mView.findViewById(R.id.title_verify);
            EditText P1 = mView.findViewById(R.id.Pf);
            EditText P2 = mView.findViewById(R.id.Ps);
            EditText P3 = mView.findViewById(R.id.Pt);
            EditText P4 = mView.findViewById(R.id.Pfr);

            P1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.toString().trim().isEmpty()) {
                        P2.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            P2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.toString().trim().isEmpty()) {
                        P3.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            P3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.toString().trim().isEmpty()) {
                        P4.requestFocus();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            P1.requestFocus();
            Title.setText("Email Verification");

            alert.setView(mView);

            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);

            tv_cancel.setOnClickListener(v -> {
                veryProBar.setVisibility(View.INVISIBLE);
                Right.setVisibility(View.INVISIBLE);
                very_email = false;
                E_verify.setVisibility(View.VISIBLE);
                P1.setText("");
                P2.setText("");
                P3.setText("");
                P4.setText("");
                alertDialog.dismiss();
            });

            btn_cancel.setOnClickListener(v -> {
                veryProBar.setVisibility(View.INVISIBLE);
                Right.setVisibility(View.INVISIBLE);
                very_email = false;
                E_verify.setVisibility(View.VISIBLE);
                P1.setText("");
                P2.setText("");
                P3.setText("");
                P4.setText("");
                alertDialog.dismiss();
            });

            tv_verify.setOnClickListener(v -> {
                veryProBar.setVisibility(View.INVISIBLE);
                if (P1.getText().toString().isEmpty()
                        || P2.getText().toString().isEmpty()
                        || P3.getText().toString().isEmpty()
                        || P4.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Enter Complete PIN", Toast.LENGTH_SHORT).show();
                } else {
                    UserEpin = P1.getText().toString() + P2.getText().toString() + P3.getText().toString() + P4.getText().toString();
                    if (UserEpin.equals(Code)) {
                        Right.setVisibility(View.VISIBLE);
                        very_email = true;
                        E_verify.setVisibility(View.INVISIBLE);
                        P1.setText("");
                        P2.setText("");
                        P3.setText("");
                        P4.setText("");
                        alertDialog.hide();
                    } else {
                        P1.setText("");
                        P2.setText("");
                        P3.setText("");
                        P4.setText("");
                        P1.requestFocus();
                        Toast.makeText(getBaseContext(), "Invalid PIN Try Again| If not receive PIN, Check your mobile balance", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_verify.setOnClickListener(v -> {
                veryProBar.setVisibility(View.INVISIBLE);
                if (P1.getText().toString().isEmpty()
                        || P2.getText().toString().isEmpty()
                        || P3.getText().toString().isEmpty()
                        || P4.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Enter Complete PIN", Toast.LENGTH_SHORT).show();
                } else {
                    UserEpin = P1.getText().toString() + P2.getText().toString() + P3.getText().toString() + P4.getText().toString();
                    if (UserEpin.equals(Code)) {
                        Right.setVisibility(View.VISIBLE);
                        very_email = true;
                        E_verify.setVisibility(View.INVISIBLE);
                        P1.setText("");
                        P2.setText("");
                        P3.setText("");
                        P4.setText("");
                        alertDialog.hide();
                    } else {
                        P1.setText("");
                        P2.setText("");
                        P3.setText("");
                        P4.setText("");
                        P1.requestFocus();
                        Toast.makeText(getBaseContext(), "Invalid PIN Try Again| If not receive PIN, Check your mobile balance", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.show();
        }
    }

    public void E_verify(View view) {
        EmailVerification();
    }

    public void show(View view) {
        if(showp.isChecked()){
            Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            rPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            rPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void sign(View view) {
        signProBar.setVisibility(View.VISIBLE);
        SignUp.setVisibility(View.INVISIBLE);
        if (id.getText().toString().isEmpty()
                || Uname.getText().toString().isEmpty()
                || email.getText().toString().isEmpty()
                || Password.getText().toString().isEmpty()
                || rPassword.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Fill All", Toast.LENGTH_SHORT).show();
            signProBar.setVisibility(View.INVISIBLE);
            SignUp.setVisibility(View.VISIBLE);
        } else {
            SignId = id.getText().toString();
            SignName = Uname.getText().toString();
            SignEmail = email.getText().toString();
            Sign_Pass = Password.getText().toString();
            Sign_rPass = rPassword.getText().toString();

            if (very_email.equals(false)) {
                Toast.makeText(getBaseContext(), "Verify your Email from RED VERIFY BUTTONS", Toast.LENGTH_SHORT).show();
                signProBar.setVisibility(View.INVISIBLE);
                SignUp.setVisibility(View.VISIBLE);
            } else {
                SignPass = true;
            }

            UnpackAPK();

            new Handler().postDelayed(() -> {
                rootNode1 = FirebaseDatabase.getInstance();
                if(isUnpack.equals("pack")){
                    Etype = "Admin";
                    reference1 = rootNode1.getReference("Pack");
                    PackStatus status = new PackStatus("unpack");
                    reference1.setValue(status);
                    FirstTime = "true";
                } else {
                    Etype = "Employee";
                }
                if (SignPass) {
                    try {
                        reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(SignName);
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("uname").getValue() == null) {
                                    if (Sign_Pass.equals(Sign_rPass)) {
                                        if (id.length() == 10) {
                                            reference1 = rootNode1.getReference("Users");
                                            UserProfile helperClass = new UserProfile(SignId, SignName, SignEmail, Sign_Pass, Etype);
                                            reference1.child(SignName).setValue(helperClass);
                                            Toast.makeText(getBaseContext(), "Registration Success!", Toast.LENGTH_SHORT).show();
                                            new Handler().postDelayed(() -> {
                                                signProBar.setVisibility(View.INVISIBLE);
                                                SignUp.setVisibility(View.VISIBLE);
                                                SignPass=false;
                                                very_email = false;
                                                E_verify.setVisibility(View.INVISIBLE);
                                                Right.setVisibility(View.INVISIBLE);
                                                id.setBackgroundResource(R.drawable.signup_data_outline);
                                                Uname.setBackgroundResource(R.drawable.signup_data_outline);
                                                email_Linear.setBackgroundResource(R.drawable.signup_data_outline);
                                                Password.setBackgroundResource(R.drawable.signup_data_outline);
                                                rPassword.setBackgroundResource(R.drawable.signup_data_outline);
                                                id.setText("");
                                                Uname.setText("");
                                                email.setText("");
                                                Password.setText("");
                                                rPassword.setText("");
                                                showp.setChecked(false);
                                                referenceid = FirebaseDatabase.getInstance().getReference().child("ID_Collection");
                                                referenceid.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        referenceid.child(idkey).setValue("null");
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                    }

                                                });
                                                Intent intent = new Intent(SignUp.this, ControlView.class);
                                                intent.putExtra("SUser", SignName);
                                                intent.putExtra("UsingTime", FirstTime);
                                                startActivity(intent);
                                            },SPLASH_TIME_OUT_SIGNUP);
                                        } else {
                                            Toast.makeText(getBaseContext(), "Invalid Id", Toast.LENGTH_SHORT).show();
                                            id.setBackgroundResource(R.drawable.signup_data_outline_red);
                                            id.requestFocus();
                                            id.setText("");
                                            SignUp.setVisibility(View.VISIBLE);
                                            signProBar.setVisibility(View.INVISIBLE);
                                        }

                                    } else {
                                        Toast.makeText(getBaseContext(), "ReEnter Password Incorrect", Toast.LENGTH_SHORT).show();
                                        rPassword.setText("");
                                        rPassword.requestFocus();
                                        rPassword.setBackgroundResource(R.drawable.signup_data_outline_red);
                                        SignUp.setVisibility(View.VISIBLE);
                                        signProBar.setVisibility(View.INVISIBLE);
                                    }

                                } else {
                                    Toast.makeText(getBaseContext(), "This Username Already Exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                }

            },SPLASH_TIME_OUT_UNPACK);
        }
    }

    private void UnpackAPK(){
        reference1 = FirebaseDatabase.getInstance().getReference().child("Pack");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isUnpack = snapshot.child("status").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Login(View view) {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getBaseContext(), "Use the Blue Button", Toast.LENGTH_SHORT).show();
    }
}