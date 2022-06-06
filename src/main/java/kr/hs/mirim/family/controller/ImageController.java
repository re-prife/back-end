package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name="IMAGE",description = "이미지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
public class ImageController {

    private final ImageService imageService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 이미지 업데이트 성공"),
            @ApiResponse(responseCode = "404", description = "userId가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "이미지 파일이 없을 경우")
    })
    @Operation(tags = "IMAGE", summary = "유저 이미지 업데이트", description = "유저 이미지를 추가, 갱신하는 API")
    @PostMapping("/users/{userId}")
    public void userImageUpdate(
            @ApiParam(value = "이미지를 추가, 갱신할 유저의 ID") @PathVariable long userId,
            @ApiParam(value = "추가, 갱신할 이미지 파일") @RequestParam("file") MultipartFile file) {
        imageService.userImageUpdate(userId, file);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식재료 이미지 업데이트 성공"),
            @ApiResponse(responseCode = "404", description = "ingredientId가  존재하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "이미지 파일이 없을 경우")
    })
    @Operation(tags = "IMAGE", summary = "식재료 이미지 업데이트", description = "식재료 이미지를 추가, 갱신하는 API")
    @PostMapping("/ingredients/{ingredientId}")
    public void ingredientImageUpdate(@ApiParam(value = "이미지를 추가, 갱신할 식재료의 ID") @PathVariable long ingredientId,
                                      @ApiParam(value = "추가, 갱신할 이미지 파일") @RequestParam("file") MultipartFile file){
        imageService.ingredientImageUpdate(ingredientId, file);
    }

}
