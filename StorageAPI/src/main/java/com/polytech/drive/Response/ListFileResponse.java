package com.polytech.drive.Response;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

@Data
public class ListFileResponse {
    private List<String> list;
    public ListFileResponse(List<String> list){
        this.list = list;
    }
}
