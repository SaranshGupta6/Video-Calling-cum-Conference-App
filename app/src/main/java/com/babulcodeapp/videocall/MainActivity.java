package com.babulcodeapp.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button  demoBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.codeBox);
        String text = editText.getText().toString();
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }

        // This is a demo app  You can add share button Functionality

        demoBtn= (Button) findViewById(R.id.demoBtn);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
            }
        });
    }

}