package com.example.idid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MissionRequestActivity extends AppCompatActivity {

    Button btn_accept,btn_decline;
    String missionKey,missionSubject,missionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_request);

        missionKey =  getIntent().getStringExtra("missionKey");
        missionSubject  = getIntent().getStringExtra("missionSubject");
        missionText =   getIntent().getStringExtra("missionText");
        btn_decline = findViewById(R.id.decline_btn);
        btn_accept = findViewById(R.id.accept_btn);


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                uploadRespond();
                startMissionStation();

            }
        });
        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Request Declined",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
    }
    void startMissionStation()
    {
        Intent i = new Intent(this,MissionActivity.class);
        i.putExtra("missionKey",missionKey);
        startActivity(i);
    }

    void uploadRespond()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Missions/" + missionKey + "/Mission");
        databaseReference.setValue(setMission());

    }

    private MissionInfo setMission()
    {
        MissionInfo missionInfo = new MissionInfo();
        missionInfo.setMissionKey(missionKey);
        missionInfo.setText(missionText);
        missionInfo.setSubject(missionSubject);
        missionInfo.setName(MyInfo.myName);
        missionInfo.setPhoneNumber(MyInfo.myPhoneNumber);

        return missionInfo;
    }
}
