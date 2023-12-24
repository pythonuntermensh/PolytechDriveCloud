package com.polytech.drive.Service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class StorageApiClient {
    private RestTemplate restTemplate;
    private DiscoveryClient discoveryClient;

    public StorageApiClient(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public ResponseEntity<Object> findAll() {
        ServiceInstance instance = discoveryClient.getInstances("storage-api")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("Storage-api service unavailable"));
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/api/v1/files/all");
        return restTemplate.getForEntity(uriComponentsBuilder.toUriString(), Object.class);
    }
}
