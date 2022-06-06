package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    @Value("${image.path}")
    private String FILE_PATH;

    public void userImageUpdate(long userId, MultipartFile file){
        fileValid(file);
        if(!userRepository.existsById(userId)){
            throw new DataNotFoundException("해당하는 유저가 없습니다.");
        }
        String saveName = "user_"+userId+"."+FilenameUtils.getExtension(file.getOriginalFilename());
        saveFile(file, FILE_PATH, saveName);
        userRepository.updateUserImage(userId, "user_"+userId+"."+FilenameUtils.getExtension(file.getOriginalFilename()));
    }

    public void ingredientImageUpdate(long ingredientId, MultipartFile file){
        fileValid(file);
        if(!ingredientRepository.existsById(ingredientId)){
            throw new DataNotFoundException("해당하는 식재료가 없습니다.");
        }

        String saveName = "ingre_"+ingredientId+"."+FilenameUtils.getExtension(file.getOriginalFilename());
        saveFile(file, FILE_PATH, saveName);
        ingredientRepository.updateIngredientImage(ingredientId,saveName);
    }

    private void saveFile(MultipartFile file, String filePath, String saveName){
        File saveFile = new File(filePath,saveName);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.getCause();
        }

        saveFile.setWritable(true);
        saveFile.setReadable(true);
    }

    private void fileValid(MultipartFile file){
        if(file.isEmpty()){
            throw new BadRequestException("파일이 없습니다.");
        }
    }
}
