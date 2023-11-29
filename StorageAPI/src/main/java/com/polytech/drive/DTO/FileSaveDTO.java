package com.polytech.drive.DTO;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Data
public class FileSaveDTO {
    @NonNull
    private File file;
    private LocalDateTime localDateTime;

    public FileSaveDTO() {}
    public FileSaveDTO(@NonNull File file, LocalDateTime localDateTime) {
        this.file = file;
        this.localDateTime = localDateTime;
    }
}
