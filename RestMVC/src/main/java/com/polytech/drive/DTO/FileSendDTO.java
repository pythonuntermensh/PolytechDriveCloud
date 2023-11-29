package com.polytech.drive.DTO;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;

@Data
public class FileSendDTO {
    @NonNull
    private File file;
    private LocalDateTime localDateTime;

    public FileSendDTO() {}
    public FileSendDTO(@NonNull File file, LocalDateTime localDateTime) {
        this.file = file;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        try {
            return localDateTime + ":::" + file.getName() + ":::" + Arrays.toString(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
