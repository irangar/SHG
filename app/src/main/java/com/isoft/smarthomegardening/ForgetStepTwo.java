package com.isoft.smarthomegardening;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetStepTwo extends AppCompatActivity {
    Window window;

    Animation middleAnimation;

    ProgressBar progressBar2;
    TextView OTP_txt, do_thing, MailAddress, question, Rsend;
    EditText OTP1, OTP2, OTP3, OTP4;
    ImageView mail;
    CardView Verify;

    String Code, email, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_step_two);

        window = ForgetStepTwo.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.rgb(0,46,43));
        }

        progressBar2=findViewById(R.id.progressBar2);
        mail =findViewById(R.id.mail);
        OTP_txt=findViewById(R.id.OTP_very_txt);
        do_thing=findViewById(R.id.do_thing);
        MailAddress =findViewById(R.id.mailview);
        OTP1 =findViewById(R.id.f);
        OTP2 =findViewById(R.id.s);
        OTP3 =findViewById(R.id.t);
        OTP4 =findViewById(R.id.fr);
        question =findViewById(R.id.question);
        Rsend =findViewById(R.id.resend);
        Verify =findViewById(R.id.verybtn);

        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.moving);

        mail.setAnimation(middleAnimation);
        OTP_txt.setAnimation(middleAnimation);
        do_thing.setAnimation(middleAnimation);
        MailAddress.setAnimation(middleAnimation);
        OTP1.setAnimation(middleAnimation);
        OTP2.setAnimation(middleAnimation);
        OTP3.setAnimation(middleAnimation);
        OTP4.setAnimation(middleAnimation);
        question.setAnimation(middleAnimation);
        Rsend.setAnimation(middleAnimation);
        Verify.setAnimation(middleAnimation);

        Intent intent = getIntent();
        if(intent.getStringExtra("cd") != null){
            Code = intent.getStringExtra("cd");
            Toast.makeText(getBaseContext(), Code, Toast.LENGTH_SHORT).show();
        }
        if(intent.getStringExtra("em") != null){
            email = intent.getStringExtra("em");
            MailAddress.setText(String.format("%s***%s@gmail.com",email.charAt(0), email.substring(Integer.parseInt(String.valueOf(email.length()))-2, Integer.parseInt(String.valueOf(email.length())))));
        }
        if(intent.getStringExtra("un") != null){
            user = intent.getStringExtra("un");
        }

        OTP1.requestFocus();
        setupOTPInputs();
    }

    private void setupOTPInputs(){
        OTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    OTP2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        OTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    OTP3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        OTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    OTP4.requestFocus();
                }

            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void Rsend(View view) {
        progressBar2.setVisibility(View.VISIBLE);
        Verify.setVisibility(View.INVISIBLE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        PasswordReset();
    }

    @SuppressLint("DefaultLocale")
    private void PasswordReset(){
        if(email != null){
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

                progressBar2.setVisibility(View.INVISIBLE);
                Verify.setVisibility(View.VISIBLE);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            OTP1.setText("");
            OTP2.setText("");
            OTP3.setText("");
            OTP4.setText("");
            OTP1.requestFocus();
            progressBar2.setVisibility(View.INVISIBLE);
            Verify.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), "Data Distorted Please Try Again from Start", Toast.LENGTH_SHORT).show();
        }
    }

    public void Verify(View view) {
        if (OTP1.getText().toString().trim().isEmpty()
                || OTP2.getText().toString().trim().isEmpty()
                || OTP3.getText().toString().trim().isEmpty()
                || OTP4.getText().toString().trim().isEmpty()){

            if (OTP1.getText().toString().trim().isEmpty()
                    && OTP2.getText().toString().trim().isEmpty()
                    && OTP3.getText().toString().trim().isEmpty()
                    && OTP4.getText().toString().trim().isEmpty()){

                Toast.makeText(getBaseContext(), "Please input OTP", Toast.LENGTH_SHORT).show();
                OTP1.requestFocus();

            } else {
                Toast.makeText(getBaseContext(), "Please input valid OTP", Toast.LENGTH_SHORT).show();
                OTP1.requestFocus();
                OTP1.setText("");
                OTP2.setText("");
                OTP3.setText("");
                OTP4.setText("");
            }
        } else {
            String OTP_Code = String.format("%s%s%s%s", OTP1.getText().toString(),
                    OTP2.getText().toString(),
                    OTP3.getText().toString(),
                    OTP4.getText().toString());

            if(Code.equals(OTP_Code)){
                Intent intent = new Intent(ForgetStepTwo.this, ForgetStepThree.class);
                intent.putExtra("fun", user);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "OTP was invalid", Toast.LENGTH_SHORT).show();
                OTP1.requestFocus();
                OTP1.setText("");
                OTP2.setText("");
                OTP3.setText("");
                OTP4.setText("");
            }
        }
    }
}