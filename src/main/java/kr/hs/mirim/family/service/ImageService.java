package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    public void userImageUpdate(long userId, MultipartFile file){

        String filePath = "d:/temp/upload/users/"+userId;
        imageUpload(file, filePath);

        userRepository.updateUserImage(userId, filePath);
    }

    public void ingredientImageUpdate(long ingredientId, MultipartFile file){
        if(!ingredientRepository.existsById(ingredientId)){
            throw new DataNotFoundException("해당하는 식재료가 없습니다.");
        }
        
        String filePath = "d:/temp/upload/ingredients/"+ingredientId;
        imageUpload(file, filePath);

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
