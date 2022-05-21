package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateIngredientRequest;
import kr.hs.mirim.family.dto.response.IngredientListResponse;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.ingredient.Ingredient;
import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;


@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final GroupRepository groupRepository;

    public void createIngredient(CreateIngredientRequest request, long groupId, BindingResult result){
        Group group = getGroup(groupId);
        formValidate(result);

        Ingredient ingredient = Ingredient.builder()
                .group(group)
                .ingredientName(request.getIngredientName())
                .ingredientCount(request.getIngredientCount())
                .ingredientCategory(request.getIngredientCategory())
                .ingredientPurchaseDate(request.getIngredientPurchaseDate())
                .ingredientExpirationDate(request.getIngredientExpirationDate())
                .ingredientMemo(request.getIngredientMemo())
                .ingredientSaveType(request.getIngredientSaveType())
                .ingredientImageName("")
                .build();

        ingredientRepository.save(ingredient);
    }

    public List<IngredientListResponse> ingredientSaveTypeList(long groupId, String saveType){
        if(!groupRepository.existsById(groupId)){
            throw new DataNotFoundException("존재하지 않는 그룹입니다,");
        }
        return ingredientRepository.ingredientSaveTypeList(groupId, saveType);
    }

    private void formValidate(BindingResult result){
        if(result.hasErrors()){
            throw new BadRequestException("유효하지 않은 형식입니다."+result.getAllErrors());
        }
    }

    private Group getGroup(long groupId){
        return groupRepository.findById(groupId).orElseThrow(()->{
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }
}
