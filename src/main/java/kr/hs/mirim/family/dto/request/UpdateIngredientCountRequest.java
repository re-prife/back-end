package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Schema(description = "식재료 수량 갱신 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIngredientCountRequest {
    List<UpdateIngredientCountDataRequest> data;
}
