package com.nuracell.bs.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuracell.bs.entity.Drone;
import com.nuracell.bs.entity.Player;
import com.nuracell.bs.exception.PlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;

    private final Random rand = new Random();

    public void testPlayerREST() throws URISyntaxException {
        URI url = new URI("http://localhost:8080/api/v1/players");

        //---------------- **For**() -------------\\
        try {
            restTemplate.getForEntity(url + "/1", Player.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            restTemplate.getForObject(url + "/1", Player.class);
        } catch (HttpClientErrorException e) {
            System.out.println(e);
        }

        Player playerToPost = new Player(
                new BigInteger(String.valueOf(rand.nextLong(Integer.MAX_VALUE, Long.MAX_VALUE))),
                "The Amonga " + UUID.randomUUID().toString().substring(0, 8)
                );
        HttpEntity<Player> playerHttpEntity = new HttpEntity<>(playerToPost);
        ResponseEntity<Player> playerPostResponseEntity = restTemplate.postForEntity(url, playerHttpEntity, Player.class);
        fancyPrint(playerPostResponseEntity);
        fancyPrint(restTemplate.getForObject(url + "/1", Player.class));

        List<Player> allPlayers = new ArrayList<>();
        fancyPrint(restTemplate.getForObject(url, (Class<List<Drone>>)allPlayers.getClass()));
        fancyPrint(restTemplate.getForEntity(url, (Class<List<Drone>>)allPlayers.getClass()));

        playerToPost.setName(playerToPost.getName().substring(11));
        playerToPost.setId(1L);
        playerHttpEntity = new HttpEntity<>(playerToPost);
        restTemplate.put(url + "/1", playerHttpEntity);
        fancyPrint(restTemplate.getForObject(url + "/1", Player.class));

        Player player = restTemplate.patchForObject(url + "/1/Amigo", playerHttpEntity, Player.class);
        fancyPrint(restTemplate.getForObject(url + "/1", Player.class));

        restTemplate.delete(url + "/1");
        try {
            fancyPrint(restTemplate.getForObject(url + "/1", Player.class));
        } catch (HttpClientErrorException e) {
            System.out.println(e);
        }


        //---------------- exchange() --------------\\

        playerToPost = new Player(
                new BigInteger(String.valueOf(rand.nextLong(Integer.MAX_VALUE, Long.MAX_VALUE))),
                UUID.randomUUID().toString().substring(0, 8)
        );
        playerHttpEntity = new HttpEntity<>(playerToPost);

        fancyPrint(restTemplate.exchange(url, HttpMethod.POST, playerHttpEntity, Player.class));
        fancyPrint(restTemplate.exchange(url + "/2", HttpMethod.GET, null, Player.class));
        fancyPrint(restTemplate.exchange(url, HttpMethod.GET, null, (Class<List<Player>>) allPlayers.getClass()));
        playerToPost.setName("Homun_Culus");
        fancyPrint(restTemplate.exchange(url + "/2", HttpMethod.PUT, playerHttpEntity, Player.class));
        fancyPrint(restTemplate.exchange(url + "/2/don", HttpMethod.PATCH, null, Player.class));
        fancyPrint(restTemplate.exchange(url + "/2", HttpMethod.DELETE, null, Map.class));
    }

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

    public void fancyPrint(Object o) {
        System.out.println("""
                ------------------------
                %s
                ------------------------
                """.formatted(o));
    }
}
