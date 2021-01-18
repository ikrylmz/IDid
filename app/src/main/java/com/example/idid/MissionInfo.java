package com.example.idid;

public class MissionInfo {

    private String phoneNumber,subject,text,name;
    private String missionKey;

    public String getMissionKey()
    {
        return missionKey;
    }

    public void setMissionKey(String missionKey)
    {
        this.missionKey = missionKey;
    }

    public String getText()
    {
        return  text;
    }
    public void setText(String text)
    {
        this.text = text;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getSubject()
    {
        return subject;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

}
