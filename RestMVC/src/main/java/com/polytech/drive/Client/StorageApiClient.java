package com.polytech.drive.Client;

import com.polytech.drive.Response.ListFileResponse;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StorageApiClient {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final String storageApiUrl = "http://storage-api";

    public StorageApiClient(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public ResponseEntity<Resource> downloadFileByName(String fileName){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(storageApiUrl + "/api/v1/files/get?fileName=" + fileName);
        return restTemplate.getForEntity(uriComponentsBuilder.toUriString(), Resource.class);
    }

    public ResponseEntity<ListFileResponse> findAllByPrefix(String prefix){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(storageApiUrl + "/api/v1/files?prefix=" + prefix);
        return restTemplate.getForEntity(uriComponentsBuilder.toUriString(), ListFileResponse.class);
    }

    public ResponseEntity<Object> findAll() {
        ServiceInstance instance = getServiceInstance();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/api/v1/files/all");
        return restTemplate.getForEntity(uriComponentsBuilder.toUriString(), Object.class);
    }

    private ServiceInstance getServiceInstance() {
        return discoveryClient.getInstances("storage-api")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("Storage-api service unavailable"));
    }
}
