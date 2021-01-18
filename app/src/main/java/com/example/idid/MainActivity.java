package com.example.idid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final int REQUEST_READ_PHONE_STATE = 80;
    RecyclerView recyclerView;
    ArrayList<User> userList;
    String missionKey;
    FragmentManager fm;
    FragmentTransaction ft;
    RvUsers.IButtonClick click;
    LinearLayout lyt_mainActivity;
    boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_missions);


        lyt_mainActivity = findViewById(R.id.mainActivity_lyt);

        missionKey = Upload.getKey();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        MyInfo.myPhoneNumber = getMyNumber();
        MyInfo.myName = "Logdef";
        MyInfo.myPhoneNumber = "123456789";



        User myInfo = new User();
        myInfo.setName(MyInfo.myName);
        myInfo.setPhoneNumber(MyInfo.myPhoneNumber);
        //myInfo.setPhoneNumber( MyInfo.myPhoneNumber);

        Upload upload = new Upload();
        upload.uploadUser(myInfo);




       // isActivityFirstlyOpened();

         click = new RvUsers.IButtonClick() {
            @Override
            public void click(User user) {

             TypingMission typingMission = new TypingMission();
             Bundle bundle = new Bundle();
             bundle.putString("userName",user.getName());
             bundle.putString("phoneNumber",user.getPhoneNumber());
             bundle.putString("missionKey",missionKey);

             typingMission.setArguments(bundle);

             lyt_mainActivity.setVisibility(View.INVISIBLE);
             getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,typingMission).commit();

            }
        };

        Download.IUsers iUsers = new Download.IUsers(){

            @Override
            public void users(ArrayList<User> list)
            {
              setRv(list);
            }
        };
        Download download =  new Download();
        download.set_IUsers(iUsers);
        download.downloadUser("Users");
        listenToRequests();

    }
    void uploadUserInfo()
    {
        User myInfo = new User();
        myInfo.setName(MyInfo.myName);
        myInfo.setPhoneNumber(MyInfo.myPhoneNumber);
        //myInfo.setPhoneNumber( MyInfo.myPhoneNumber);

        Upload upload = new Upload();
        upload.uploadUser(myInfo);
    }
    void closeMyFragment(){

        lyt_mainActivity.setVisibility(View.VISIBLE);
        findViewById(R.id.frameLayout).setVisibility(View.INVISIBLE);

    }
    void isActivityFirstlyOpened()
    {
        if(isFirst){
            lyt_mainActivity.setVisibility(View.INVISIBLE);
            Inputs inputs = new Inputs();
            inputs.mainActivity = this;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,inputs).commit();
            isFirst = false;
        }
    }
    void setRv(ArrayList<User> userList)
    {
        RvUsers rvUsers = new RvUsers(userList,click,getApplicationContext());
        recyclerView.setAdapter(rvUsers);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
    }
    String getMyNumber()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            getMyNumber();
        } else {
            requestPermission();
        }

        String myNumber = "-";
        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            myNumber = tMgr.getLine1Number();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return myNumber;
    }
    private void listenToRequests()
    {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("Requested/"+MyInfo.myPhoneNumber);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String mr = ds.getValue(MissionInfo.class).getName();
                  //  notifyMessage(ds.getValue(MissionInfo.class));
                    Log.i("Listen","request yapıldı");
                    Log.i("Listen",mr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    /*
    void goToMissionRequest(String missionKey,String text,String subject)
    {
        Intent i = new Intent(this,MissionRequestActivity.class);
        i.putExtra("missionKey",missionKey);
        i.putExtra("missionText",text);
        i.putExtra("missionSubject",subject);

        startActivity(i);
    }

     */
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    public void notifyMessage(MissionInfo missionInfo) {

        if(missionInfo == null)
        {
            Log.e("","RequestInfo is null");
            return;
        }

        Intent notificationIntent = new Intent(this, MissionRequestActivity.class);
        notificationIntent.putExtra("missionKey",missionInfo.getMissionKey());
        notificationIntent.putExtra("missionText",missionInfo.getText());
        notificationIntent.putExtra("missionSubject",missionInfo.getSubject());
        // notificationIntent.putExtra("");
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        int NOTIFICATION_ID = 52;
        String channel_id = createNotificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, channel_id)
                    .setContentTitle(missionInfo.getSubject())
                    .setContentText(missionInfo.getText())
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, notification);
        }
        else
        {
            Notification notification = new Notification.Builder(this)
                    .setContentTitle(missionInfo.getSubject())
                    .setContentText(missionInfo.getText())
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, notification);

        }
    }
    private String createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String CHANNEL_ID = "ret";
            String CHANNEL_NAME = "Request";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH );
            channel.enableVibration(true);


            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            return CHANNEL_ID;
        }
        return "-1";
    }
}
