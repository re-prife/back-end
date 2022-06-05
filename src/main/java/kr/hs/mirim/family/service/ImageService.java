package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
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
    private String filepath;

    public void userImageUpdate(long userId, MultipartFile file){
        User user = userRepository.findById(userId).orElseThrow(()->{
            throw new DataNotFoundException("해당하는 유저가 없습니다.");
        });
        filepath = filepath+"user_"+userId;
        imageUpload(file, filepath);

        userRepository.updateUserImage(userId, filepath.substring(7)+"."+FilenameUtils.getExtension(file.getOriginalFilename()));
    }

    public void ingredientImageUpdate(long ingredientId, MultipartFile file){
        if(!ingredientRepository.existsById(ingredientId)){
            throw new DataNotFoundException("해당하는 식재료가 없습니다.");
        }

        filepath = filepath+"ingre_"+ingredientId;
        imageUpload(file, filepath);
    }

    private void imageUpload(MultipartFile file, String filePath) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath +"."+ FilenameUtils.getExtension(file.getOriginalFilename()));
                InputStream is = file.getInputStream()) {

            int readCount = 0;
            byte[] buffer = new byte[1024];

            while ((readCount = is.read(buffer)) != -1) {

                fos.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
