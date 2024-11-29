package com.idle.createdmissionservice.infrastructure;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.createdmissionservice.domain.AIMissionProvider;
import com.idle.createdmissionservice.domain.CreateMissionData;
import com.idle.createdmissionservice.domain.MissionAuth;
import com.idle.createdmissionservice.infrastructure.dto.CreateMissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AIMissionClient implements AIMissionProvider {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ai.endpoints.weather}")
    private String aiWeatherEndpoints;

    @Value("${ai.endpoints.mission-auth}")
    private String aiMissionAuthEndpoints;

    private static final String AI_LOCAL_ENDPOINT = "http://localhost:8000/";

    @Override
    public CreateMissionData createMission(double latitude, double longitude) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CreateMissionRequest req = CreateMissionRequest.of(latitude, longitude);
        HttpEntity<CreateMissionRequest> request = new HttpEntity<>(req, headers);
        return new CreateMissionData();
    }

    @Override
    public boolean authMission(MissionAuth missionAuth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MissionAuth> request = new HttpEntity<>(missionAuth, headers);
        String response = restTemplate.postForObject(AI_LOCAL_ENDPOINT + "verification", request, String.class);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new BaseException(ErrorCode.CONVERT_JSON_ERROR);
        }
        return jsonNode.get("certified").asBoolean();
    }
}
