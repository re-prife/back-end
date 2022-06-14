package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.DeleteIngredientRequest;
import kr.hs.mirim.family.dto.request.IngredientRequest;
import kr.hs.mirim.family.dto.request.UpdateIngredientCountRequest;
import kr.hs.mirim.family.dto.response.IngredientListResponse;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.ingredient.Ingredient;
import kr.hs.mirim.family.entity.ingredient.repository.IngredientRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.ConflictException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import kr.hs.mirim.family.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public long createIngredient(IngredientRequest request, long groupId, BindingResult result){
        Group group = getGroup(groupId);
        formValidate(result);

        if (checkIngredientCount(request.getIngredientCount())) {
            throw new ConflictException("식재료의 수가 0입니다.");
        }

        if (request.getIngredientExpirationDate().isBefore(request.getIngredientPurchaseDate())) {
            throw new ConflictException("유통 기한이 구매 날짜보다 먼저입니다.");
        }

        Ingredient ingredient = Ingredient.builder()
                .group(group)
                .ingredientName(request.getIngredientName())
                .ingredientCount(request.getIngredientCount())
                .ingredientCategory(request.getIngredientCategory())
                .ingredientPurchaseDate(request.getIngredientPurchaseDate())
                .ingredientExpirationDate(request.getIngredientExpirationDate())
                .ingredientMemo(request.getIngredientMemo())
                .ingredientSaveType(request.getIngredientSaveType())
                .ingredientImagePath("")
                .build();

        return ingredientRepository.save(ingredient).getIngredientId();
    }

    public List<IngredientListResponse> ingredientSaveTypeList(long groupId, String saveType) {
        existGroup(groupId);
        List<IngredientListResponse> list = ingredientRepository.ingredientSaveTypeList(groupId, saveType);

        LocalDate today = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        for (IngredientListResponse response : list) {
            long remainDays = ChronoUnit.DAYS.between(today, response.getIngredientExpirationDate());

            if (remainDays < 0)
                response.setIngredientColor("black");
            else if (remainDays <= 3)
                response.setIngredientColor("red");
            else if (remainDays <= 7)
                response.setIngredientColor("yellow");
            else
                response.setIngredientColor("green");
        }
        return list;
    }

    @Transactional
    public void updateIngredient(long groupId, long ingredientId, IngredientRequest request, BindingResult result){
        formValidate(result);
        existGroup(groupId);
        existIngredient(ingredientId);

        Ingredient ingredient = getIngredient(ingredientId);
        existIngredientInGroup(groupId, ingredient);

        if(request.getIngredientExpirationDate().isBefore(request.getIngredientPurchaseDate())){
            throw new ConflictException("유통 기한이 구매 날짜보다 먼저입니다.");
        }

        if(checkIngredientCount(request.getIngredientCount())){
            deleteImageFile(ingredientId);
            return ;
        }

        ingredient.updateIngredient(request);
    }

    @Transactional
    public void deleteIngredient(long groupId, List<DeleteIngredientRequest> request, BindingResult result) {
        formValidate(result);
        existGroup(groupId);

        for (DeleteIngredientRequest ingredientRequest : request) {
            Ingredient ingredient = getIngredient(ingredientRequest.getIngredientId());
            existIngredientInGroup(groupId, ingredient);

            deleteImageFile(ingredient.getIngredientId());
        }
    }

    @Transactional
    public void updateIngredientCount(long groupId, List<UpdateIngredientCountRequest> request, BindingResult result) {
        formValidate(result);
        existGroup(groupId);

        for (UpdateIngredientCountRequest ingredientRequest : request) {
            Ingredient ingredient = getIngredient(ingredientRequest.getIngredientId());
            existIngredientInGroup(groupId, ingredient);

            String requestCount = ingredientRequest.getIngredientCount();
            long requestIngredientId = ingredientRequest.getIngredientId();

            if (checkIngredientCount(requestCount)) {
                deleteImageFile(requestIngredientId);
            } else {
                ingredientRepository.ingredientCountUpdate(groupId, requestIngredientId, requestCount);
            }
        }
    }

    private void deleteImageFile(long ingredientId){
        File file = new File("/home/ubuntu/family/upload/ingredient_"+ingredientId+".jpg");
        if(file.delete()){
            ingredientRepository.deleteById(ingredientId);
        }
        else {
            throw new InternalServerException("식재료를 삭제하지 못했습니다.");
        }
    }

    private boolean checkIngredientCount(String ingredientCount) {
        char[] arr = ingredientCount.toCharArray();
        int s = 0;
        for (char c : arr) {
            if (Character.isDigit(c)) s += Character.getNumericValue(c);
        }
        return s == 0;
    }

    private void formValidate(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }

    private Group getGroup(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }

    private void existGroup(long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
    }

    public Ingredient getIngredient(long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 식재료입니다.");
        });
    }

    private void existIngredient(long ingredientId) {
        if (!ingredientRepository.existsById(ingredientId)) {
            throw new DataNotFoundException("존재하지 않는 식재료입니다.");
        }
    }

    private void existIngredientInGroup(long groupId, Ingredient ingredient) {
        if (!(ingredient.getGroup().getGroupId() == groupId)) {
            throw new DataNotFoundException("해당 그룹에 식재료가 없습니다.");
        }
    }
}
