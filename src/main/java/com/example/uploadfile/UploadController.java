package com.example.uploadfile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UploadController {
    private final ProductUploadService productUploadService;

    @PostMapping(value = "/upload",consumes = "multipart/form-data")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("file must  not be empty");
        }
        String fileName = file.getOriginalFilename();
        Long filSize =file.getSize();

        productUploadService.uploadFile(file.getInputStream());


     return ResponseEntity.ok("A file " +fileName+" of "+filSize+
             "uploaded Successfully" +
             "Thanks alot");

    }


    @GetMapping("/home")
    public String home(){
        return "Spring is up and running health check";
    }
}
