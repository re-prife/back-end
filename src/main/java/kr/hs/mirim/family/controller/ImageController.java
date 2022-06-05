package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/uploads/users/{userId}")
    public void userImageUpdate(@PathVariable long userId, @RequestParam("file") MultipartFile file) {
        imageService.userImageUpdate(userId, file);
    }

    @PostMapping("/uploads/ingredients/{ingredientId}")
    public void ingredientImageUpdate(@PathVariable long ingredientId, @RequestParam("file") MultipartFile file){
        imageService.ingredientImageUpdate(ingredientId, file);
    }

}
