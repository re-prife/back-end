package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Schema(description = "식재료 삭제 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIngredientRequest {
    List<DeleteIngredientDataRequest> data;
}
