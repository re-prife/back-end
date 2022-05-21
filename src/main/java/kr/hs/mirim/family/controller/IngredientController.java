package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateIngredientRequest;
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
    private final IngredientService service;

    @PostMapping
    public void createIngredient(@PathVariable long groupId, @Valid @RequestBody CreateIngredientRequest request, BindingResult bindingResult){
        service.createIngredient(request, groupId, bindingResult);
    }

    @GetMapping
    public List<IngredientListResponse> ingredientList(@PathVariable long groupId, @RequestParam(required = false, defaultValue = "ALL") String saveType){
        return service.ingredientSaveTypeList(groupId, saveType);
    }
}
