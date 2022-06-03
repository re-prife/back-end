package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @GetMapping(value="/images/users/{userId}", produces= MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getUserImage(@PathVariable long userId) {
        return imageService.getUserImage(userId);
    }

    @GetMapping(value="/images/ingredients/{ingredientId}", produces= MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getIngredientImage(@PathVariable long ingredientId) {
        return imageService.getIngredientImage(ingredientId);
    }
}
