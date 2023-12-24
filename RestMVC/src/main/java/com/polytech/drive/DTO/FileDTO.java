package com.polytech.drive.DTO;

import lombok.Data;
import lombok.NonNull;
import org.apache.kafka.common.protocol.types.Field;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FileDTO implements Serializable {
    @NonNull
    private File file;
    @NonNull
    private TimestampDTO timestamp;
    @NonNull
    private String email;

    public FileDTO() {}
    public FileDTO(@NonNull File file, @NonNull TimestampDTO timestamp, @NonNull String email) {
        this.email = email;
        this.file = file;
        this.timestamp = timestamp;
    }
}
