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
    private LocalDateTime localDateTime;

    public FileDTO() {}
    public FileDTO(@NonNull File file, LocalDateTime localDateTime) {
        this.file = file;
        this.localDateTime = localDateTime;
    }
}
