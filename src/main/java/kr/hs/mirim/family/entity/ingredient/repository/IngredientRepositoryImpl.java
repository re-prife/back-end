package kr.hs.mirim.family.entity.ingredient.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.IngredientListResponse;
import kr.hs.mirim.family.entity.ingredient.Ingredient;
import kr.hs.mirim.family.entity.ingredient.IngredientSaveType;
import kr.hs.mirim.family.entity.ingredient.QIngredient;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static kr.hs.mirim.family.entity.ingredient.QIngredient.*;

public class IngredientRepositoryImpl extends QuerydslRepositorySupport implements IngredientRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public IngredientRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Ingredient.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<IngredientListResponse> ingredientSaveTypeList(long groupId, String saveType) {
        return queryFactory.select(Projections.constructor(
                IngredientListResponse.class,
                        ingredient.ingredientName,
                        ingredient.ingredientSaveType,
                        ingredient.ingredientExpirationDate,
                        ingredient.ingredientCategory,
                        ingredient.ingredientCount,
                        ingredient.ingredientImageName
                )
        )
                .from(ingredient)
                .where(ingredient.group.groupId.eq(groupId), eqSaveType(saveType))
                .fetch();
    }

    private BooleanExpression eqSaveType(String saveType){
        if(StringUtils.isNullOrEmpty(saveType)){
            return null;
        }
        return ingredient.ingredientSaveType.eq(IngredientSaveType.valueOf(saveType));
    }
}