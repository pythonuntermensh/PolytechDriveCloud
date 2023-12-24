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
    private LocalDateTime localDateTime;
    @NonNull
    private String email;

    public FileDTO() {}
    public FileDTO(@NonNull File file, @NonNull LocalDateTime localDateTime) {
        this.file = file;
        this.localDateTime = localDateTime;
    }
}
