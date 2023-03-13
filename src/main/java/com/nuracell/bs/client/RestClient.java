package com.nuracell.bs.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuracell.bs.entity.Drone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;

    public void test() throws URISyntaxException, JsonProcessingException {
        URI url = new URI("http://localhost:8080/api/v1/drones");

////////////////////// getForEntity \\\\\\\\\\\\\\\\\\\\\\
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class
        );
        System.out.println("response.getStatusCode() = " + response.getStatusCode());

////////////////////// getForObject \\\\\\\\\\\\\\\\\\\\\\
        Drone droneFromRT = restTemplate.getForObject(
                url + "/1",
                Drone.class
        );
        System.out.println(droneFromRT);

////////////////////////// exchange GET \\\\\\\\\\\\\\\\\\\\\\\\\\\
        ResponseEntity<Drone> responseEntityOneDrone = restTemplate
                .exchange(url + "/1", HttpMethod.GET, null, Drone.class);
        System.out.println("responseEntity = " + responseEntityOneDrone);

        List<Drone> result = new ArrayList<>();
        ResponseEntity<List<Drone>> responseEntityDronesList = restTemplate
                .exchange(url, HttpMethod.GET, null, (Class<List<Drone>>)result.getClass() );
        System.out.println(responseEntityDronesList);

 //--------------- Use POST to Create a Resource ----------------\\
////////////////////////// postForObject \\\\\\\\\\\\\\\\\\\\\\\\\\\
        Drone dummyDrone = new Drone(UUID.randomUUID().toString().substring(0,8));
        HttpEntity<Drone> httpEntityDrone = new HttpEntity<>(dummyDrone);
        System.out.println("restTemplate.postForObject(\nurl, httpEntityDrone, Integer.class\n) = "
                + restTemplate.postForObject(
                url, httpEntityDrone, Integer.class
        ));

////////////////////////// postForLocation \\\\\\\\\\\\\\\\\\\\\\\\\\\
        HttpEntity<Drone> forLocationEntity = new HttpEntity<>(new Drone(UUID.randomUUID().toString().substring(0,8)));
        System.out.println(restTemplate.postForLocation(url, forLocationEntity));

////////////////////////// exchange POST \\\\\\\\\\\\\\\\\\\\\\\\\\\
        ResponseEntity<Integer> exchangePostResponseEntity = restTemplate
                .exchange(url, HttpMethod.POST, httpEntityDrone, Integer.class);
        System.out.println("exchangePostResponseEntity = " + exchangePostResponseEntity);
    }
}
