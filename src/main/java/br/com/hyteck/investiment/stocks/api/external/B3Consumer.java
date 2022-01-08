package br.com.hyteck.investiment.stocks.api.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.*;

@Component
public class B3Consumer {
    @Value("${url.b3.cert}")
    private String url;
    @Value("${client.id.b3.cert}")
    private String clientId;
    @Value("${secret.id.b3.cert}")
    private String secretId;
    @Value("${scope.id.b3.cert}")
    private String scope;
    @Value("${url.optin.b3.cert}")
    private String urlOptin;

    public void getPositions( String documentNumber, LocalDate referenceData){
        String url = this.url.concat("/api/position/v1/securities-lending/investors/%1$s& referenceData=%12s");
        String format = String.format(url, new String[]{documentNumber, referenceData.toString()});
        RestTemplate restTemplate = new RestTemplate();
        var auth = authenticate();
        var returned =restTemplate.getForEntity(format, String.class);

    }

    public void optin() throws IOException {
        RestTemplate template = new RestTemplate();
        var http = template.getRequestFactory().createRequest(URI.create(urlOptin), HttpMethod.GET);
        var response=http.execute();
        response.getBody();

    }

    private LinkedHashMap<String,String> authenticate() {
        MultiValueMap<String, String> eventData = new LinkedMultiValueMap<>();
        eventData.add("grant_type", "client_credentials");
        eventData.add("client_id", clientId);
        eventData.add("client_secret", secretId);
        eventData.add("scope", scope);

        //var vars = "grant_type=client_credentials&client_id=%1s&client_secret=%2s&scope=%3s";

       // vars = String.format(vars, clientId, secretId, scope);

        String url="https://login.microsoftonline.com/4bee639f-5388-44c7-bbac-cb92a93911e6/oauth2/v2.0/token";
        //url=url.concat("/4bee639f-5388-44c7-bbac-cb92a93911e6/oauth2/v2.0/token");
       // url = String.format(url, clientId, secretId,scope);
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        var entity = new HttpEntity<>(eventData,headers);

        RestTemplate restTemplate = new RestTemplate();
        var returned = restTemplate.postForObject(url, entity, LinkedHashMap.class);
        var token = (String) Objects.requireNonNull(returned).get("access_token");

        return null;
    }




}
