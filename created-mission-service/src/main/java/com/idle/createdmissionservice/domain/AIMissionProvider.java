package com.idle.createdmissionservice.domain;

public interface AIMissionProvider {
    CreateMissionData createMission(double latitude , double longitude);
    boolean authMission(MissionAuth missionAuth);
}
