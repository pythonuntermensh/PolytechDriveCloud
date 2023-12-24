package com.polytech.drive.DTO;

import lombok.Data;
import lombok.NonNull;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FileDTO implements Serializable {
    @NonNull
    private File file;
    @NonNull
    private TimestampDTO timestamp;

    public FileDTO() {}
    public FileDTO(@NonNull File file, @NonNull TimestampDTO timestamp) {
        this.file = file;
        this.timestamp = timestamp;
    }
}
