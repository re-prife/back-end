package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/users/{userId}")
    public void userImageUpdate(@PathVariable long userId, @RequestParam("file") MultipartFile file) {
        imageService.userImageUpdate(userId, file);

    }

    @PostMapping("/ingredients/{ingredientId}")
    public void ingredientImageUpdate(@PathVariable long ingredientId, @RequestParam("file") MultipartFile file){
        imageService.ingredientImageUpdate(ingredientId, file);
    }

}
