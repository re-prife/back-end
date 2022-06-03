package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
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

    public void userImageUpdate(long userId, MultipartFile file){
        String filePath = "c:/temp/upload/users/"+userId;
        imageUpload(file, filePath);

        userRepository.updateUserImage(userId, filePath+"."+FilenameUtils.getExtension(file.getOriginalFilename()));
    }

    public void ingredientImageUpdate(long ingredientId, MultipartFile file){
        if(!ingredientRepository.existsById(ingredientId)){
            throw new DataNotFoundException("해당하는 식재료가 없습니다.");
        }
        
        String filePath = "c:/temp/upload/ingredients/"+ingredientId;
        imageUpload(file, filePath);
    }

    public byte[] getUserImage(long userId){
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{
            fis = new FileInputStream("c:/temp/upload/users/"+userId+".png");
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return getBytes(fis, baos);
    }

    public byte[] getIngredientImage(long ingredientId){
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{
            fis = new FileInputStream("c:/temp/upload/ingredients/"+ingredientId+".png");
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return getBytes(fis, baos);
    }

    private byte[] getBytes(FileInputStream fis, ByteArrayOutputStream baos) {
        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = fis.read(buffer)) != -1){
                baos.write(buffer, 0, readCount);
            }
            fileArray = baos.toByteArray();
            fis.close();
            baos.close();
        } catch(IOException e){
            throw new RuntimeException("File Error");
        }
        return fileArray;
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
