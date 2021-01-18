package com.example.idid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MissionActivity extends AppCompatActivity {

    String missionKey;
    RecyclerView missions_rv;
    RvMissions rvMissions;
    ArrayList<MissionInfo> missionList = new ArrayList<>();
    Button btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        missions_rv = findViewById(R.id.rv_missions);

        btn_done = (Button)findViewById(R.id.done_btn);

        missionKey = getIntent().getExtras().getString("missionKey");
        listenToDownload();

        RvMissions.IButtonClick click = new RvMissions.IButtonClick() {
            @Override
            public void click(MissionInfo missionInfo) {
            Bundle bundle =  new Bundle();
            bundle.putString("name",missionInfo.getName());
            bundle.putString("subject",missionInfo.getSubject());
            bundle.putString("text",missionInfo.getText());

            MissionShower shower = new MissionShower();
            shower.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,shower).commit();

            }
        };

        rvMissions = new RvMissions(missionList,click,this);
        missions_rv.setAdapter(rvMissions);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        missions_rv.setLayoutManager(llm);
        listenToCompleted();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadCompleted();
            }
        });


    }
    void listenToDownload()
    {
        Download.IMissions missions = new Download.IMissions() {
            @Override
            public void missions(MissionInfo mission) {

                missionList.add(mission);
                rvMissions.notify();

            }
        };

        Download download = new Download();
        download.set_IMissions(missions);
        download.downloadMission("Missions/" + missionKey);
    }
    void listenToCompleted()
    {
        FirebaseDatabase.getInstance().getReference("Completed/"+missionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds : dataSnapshot.getChildren())
                if( ds.getValue(IsCompleted.class).getCompleted()) {
                    String completedUser = ds.getValue(IsCompleted.class).getPhoneNumber();
                    setImageViews(completedUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void setImageViews(String completedUser)
    {
        for(int i = 0;i<missions_rv.getChildCount();i++) {

                View view = missions_rv.findViewHolderForLayoutPosition(i).itemView;
                String phoneNumber = ((TextView)view.findViewById(R.id.phoneNumber_txt)).getText().toString();
                 if (phoneNumber.equals(completedUser)) {

                 ImageView iv_blackLight = view.findViewById(R.id.blackLight_iv);
                 ImageView iv_greenLight = view.findViewById(R.id.greenLight_iv);
                 setVisibles(iv_greenLight,iv_blackLight);

            }
        }
    }
    void setVisibles(ImageView green,ImageView black)
    {
        green.setVisibility(View.VISIBLE);
        black.setVisibility(View.GONE);
    }
    void uploadCompleted()
    {
        IsCompleted isCompleted = new IsCompleted();
        isCompleted.setCompleted(true);
        isCompleted.setPhoneNumber(MyInfo.myPhoneNumber);
        FirebaseDatabase.getInstance().getReference("Completed/"+missionKey+"/Completed").setValue(isCompleted);
    }

}
