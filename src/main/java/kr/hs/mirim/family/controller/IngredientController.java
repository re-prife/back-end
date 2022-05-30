package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.DeleteIngredientRequest;
import kr.hs.mirim.family.dto.request.IngredientRequest;
import kr.hs.mirim.family.dto.request.UpdateIngredientCountRequest;
import kr.hs.mirim.family.dto.response.IngredientListResponse;
import kr.hs.mirim.family.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("groups/{groupId}/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public void createIngredient(@PathVariable long groupId, @Valid @RequestBody IngredientRequest request, BindingResult bindingResult){
        ingredientService.createIngredient(request, groupId, bindingResult);
    }

    @GetMapping
    public List<IngredientListResponse> ingredientList(@PathVariable long groupId, @RequestParam(required = false) String saveType){
        return ingredientService.ingredientSaveTypeList(groupId, saveType);
    }

    @PutMapping("/{ingredientId}")
    public void updateIngredient(@PathVariable long groupId,
                                 @PathVariable long ingredientId,
                                 @RequestBody @Valid IngredientRequest request,
                                 BindingResult result){
        ingredientService.updateIngredient(groupId, ingredientId, request, result);
    }

    @DeleteMapping
    public void deleteIngredient(@PathVariable long groupId,
                                 @RequestBody @Valid DeleteIngredientRequest request,
                                 BindingResult result){
        ingredientService.deleteIngredient(groupId, request, result);
    }

    @PutMapping
    public void updateIngredientCount(@PathVariable long groupId,
                                      @RequestBody @Valid UpdateIngredientCountRequest request,
                                      BindingResult result){
        ingredientService.updateIngredientCount(groupId, request, result);
    }

}