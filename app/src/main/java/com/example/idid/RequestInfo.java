package com.example.idid;

public class RequestInfo {

    private String phoneNumber;
    private Boolean accept;
    private String missionKey;

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getMissionKey()
    {
        return missionKey;
    }

    public void setMissionKey(String missionKey)
    {
        this.missionKey = missionKey;
    }
    public Boolean getAccept()
    {
        return accept;
    }
    public void setAccept(Boolean accept)
    {
        this.accept = accept;
    }

}
