package com.isoft.smarthomegardening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class ForgetStepOne extends AppCompatActivity {
    DatabaseReference reference2;
    Window window;

    ProgressBar progressBar1;
    EditText Uname;
    CardView send;

    String Code;
    public String email;
    public String Ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_step_one);

        window = ForgetStepOne.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(0,46,43));
        }

        progressBar1=findViewById(R.id.progressBar1);
        Uname = findViewById(R.id.Uname);
        send = findViewById(R.id.nextbtn);

        Uname.requestFocus();

        Uname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Uname.getText().toString().isEmpty()) {
                    Uname.getBackground().mutate().setColorFilter(Color.rgb(255, 191, 0), PorterDuff.Mode.SRC_ATOP);
                } else {
                    Uname.getBackground().mutate().setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    public void sendOTP(View view) {
        progressBar1.setVisibility(View.VISIBLE);
        send.setVisibility(View.INVISIBLE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        PasswordReset();
    }

    @SuppressLint("DefaultLocale")
    private void PasswordReset(){
        if (Uname.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Enter User Name", Toast.LENGTH_SHORT).show();
        } else {
            String Name = Uname.getText().toString();
            try {
                Ck = "no";
                reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(Name);
                reference2.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("email").getValue() != null ){
                            email = snapshot.child("email").getValue().toString();
                            if (Ck.equals("no")){
                                Random random = new Random();
                                Code = String.format("%04d", random.nextInt(10000));

                                final String agentMail = "smartplantpot1@gmail.com";
                                final String agentPassword = "lixewjjdpdojpgsa";
                                String Message_Code = String.format("Reset Password OTP is %s", Code);

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
                                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.format("%s@gmail.com",email)));
                                    message.setSubject("Forget OTP");
                                    message.setText(Message_Code);
                                    Transport.send(message);

                                    progressBar1.setVisibility(View.INVISIBLE);
                                    send.setVisibility(View.VISIBLE);

                                    Intent intent = new Intent(ForgetStepOne.this, ForgetStepTwo.class);
                                    intent.putExtra("cd", Code);
                                    intent.putExtra("em", email);
                                    intent.putExtra("un", Name);
                                    startActivity(intent);
                                    finish();
                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else{
                            Ck = "yes";
                            Uname.setText("");
                            Uname.requestFocus();
                            progressBar1.setVisibility(View.INVISIBLE);
                            send.setVisibility(View.VISIBLE);
                            //Toast.makeText(getBaseContext(), "Data Loss", Toast.LENGTH_SHORT).show();
                            Uname.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
            } catch (Exception e) {
                progressBar1.setVisibility(View.INVISIBLE);
                send.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), "Wrong User name", Toast.LENGTH_SHORT).show();
            }
        }
    }
}