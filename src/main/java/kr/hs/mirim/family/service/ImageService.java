package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.ingredient.Ingredient;
import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    private String FILE_PATH = "/home/ubuntu/family/upload/";

    public void userImageUpdate(long userId, MultipartFile file) {
        fileValid(file);
        User user = userRepository.findById(userId).orElseThrow(()->{
            throw  new DataNotFoundException("해당하는 유저가 없습니다.");
        });

        String saveName = "user_" + userId + ".jpg";
        String savePath = "/upload/" + saveName;

        saveFile(file, FILE_PATH, saveName);
        if(user.getUserImagePath().equals(""))
            userRepository.updateUserImage(userId, savePath);
    }

    public void ingredientImageUpdate(long ingredientId, MultipartFile file) {
        fileValid(file);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(()-> {
            throw new DataNotFoundException("해당하는 식재료가 없습니다.");
        });

        String saveName = "ingredient_" + ingredientId + ".jpg";
        String savePath = "/upload/" + saveName;

        saveFile(file, FILE_PATH, saveName);
        if(ingredient.getIngredientImagePath().equals(""))
            ingredientRepository.updateIngredientImage(ingredientId, savePath);
    }

    private void saveFile(MultipartFile file, String filePath, String saveName) {
        File saveFile = new File(filePath, saveName);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.getCause();
        }

        saveFile.setWritable(true);
        saveFile.setReadable(true);
    }

    private void fileValid(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("파일이 없습니다.");
        }
    }
}