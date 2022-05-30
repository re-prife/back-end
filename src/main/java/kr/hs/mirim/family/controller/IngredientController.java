package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.dto.request.DeleteIngredientRequest;
import kr.hs.mirim.family.dto.request.IngredientRequest;
import kr.hs.mirim.family.dto.request.UpdateIngredientCountRequest;
import kr.hs.mirim.family.dto.response.IngredientListResponse;
import kr.hs.mirim.family.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "INGREDIENT", description = "식재료 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "식재료 생성 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "식재료의 수량이 0인 경우")
    })
    @Operation(tags = "INGREDIENT", summary = "식재료 생성", description = "식재료를 생성하는 API")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createIngredient(@ApiParam(value = "식재료를 생성할 그룹의 ID") @PathVariable long groupId,
                                 @Valid @RequestBody IngredientRequest request,
                                 BindingResult bindingResult
    ) {
        ingredientService.createIngredient(request, groupId, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식재료 조회 성공"),
            @ApiResponse(responseCode = "500", description = "saveType이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "INGREDIENT", summary = "식재료 조회", description = "식재료를 전체 조회하거나 저장 방법 별로 조회하는 API")
    @GetMapping
    public List<IngredientListResponse> ingredientList(@ApiParam(value = "식재료를 조회할 그룹의 ID") @PathVariable long groupId,
                                                       @ApiParam(value = "식재료를 조회할 저장 방법") @RequestParam(required = false) String saveType) {
        return ingredientService.ingredientSaveTypeList(groupId, saveType);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "식재료 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId 또는 ingredientId가 존재하지 않을 경우\n\n식재료가 그룹에 속하지 않을 경우"),
    })
    @Operation(tags = "INGREDIENT", summary = "식재료 갱신", description = "식재료 상세 정보를 갱신하는 API")
    @PutMapping("/{ingredientId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateIngredient(@ApiParam(value = "식재료가 속한 그룹의 ID") @PathVariable long groupId,
                                 @ApiParam(value = "갱신할 식재료의 ID") @PathVariable long ingredientId,
                                 @RequestBody @Valid IngredientRequest request,
                                 BindingResult result) {
        ingredientService.updateIngredient(groupId, ingredientId, request, result);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "식재료 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId 또는 식재료가 존재하지 않을 경우\n\n식재료가 그룹에 속하지 않을 경우"),
    })
    @Operation(tags = "INGREDIENT", summary = "식재료 삭제", description = "식재료를 삭제하는 API")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteIngredient(@ApiParam(value = "식재료가 속한 그룹의 ID") @PathVariable long groupId,
                                 @RequestBody @Valid DeleteIngredientRequest request,
                                 BindingResult result) {
        ingredientService.deleteIngredient(groupId, request, result);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "식재료 수량 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId 또는 식재료가 존재하지 않을 경우\n\n식재료가 그룹에 속하지 않을 경우"),
    })
    @Operation(tags = "INGREDIENT", summary = "식재료 일괄 수량 갱신", description = "식재료의 수량을 일괄로 갱신하는 API")
    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateIngredientCount(@ApiParam(value = "식재료가 속한 그룹의 ID") @PathVariable long groupId,
                                      @RequestBody @Valid UpdateIngredientCountRequest request,
                                      BindingResult result) {
        ingredientService.updateIngredientCount(groupId, request, result);
    }

}