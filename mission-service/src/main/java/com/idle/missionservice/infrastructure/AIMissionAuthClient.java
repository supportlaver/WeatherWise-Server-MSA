package com.idle.missionservice.infrastructure;


import com.idle.missionservice.domain.AIMissionAuthProvider;

public class AIMissionAuthClient implements AIMissionAuthProvider {
    @Override
    public boolean missionAuthentication() {
        return false;
    }
}
