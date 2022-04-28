package kr.hs.mirim.family.entity.ingredient;

import kr.hs.mirim.family.entity.BaseEntity;
import kr.hs.mirim.family.entity.group.Group;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ingredient_tb")
public class Ingredient extends BaseEntity {
    @Id
    @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @Column(length = 30, nullable = false, name = "ingredient_name")
    private String ingredientName;

    @Column(nullable = false, name="ingredient_count")
    private int ingredientCount;

    @Column(columnDefinition = "CHAR(10)", nullable = false, name = "ingredient_save_type")
    private IngredientSaveType ingredientSaveType;

    @Column(nullable = false, name = "ingredient_category", columnDefinition = "CHAR(15)")
    private IngredientCategory ingredientCategory;

    @Column(nullable = false, name = "ingredient_purchase_date")
    private Date ingredientPurchaseDate;

    @Column(nullable = false, name = "ingredient_expiration_date")
    private Date ingredientExpirationDate;

    @Column(length = 100, nullable = false, name = "ingredient_memo")
    private String ingredientMemo;

    @Column(length = 10, name = "ingredient_image_name", nullable = false)
    private String ingredientImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public Ingredient(String ingredientName, int ingredientCount, IngredientSaveType ingredientSaveType, IngredientCategory ingredientCategory,
                      Date ingredientPurchaseDate, Date ingredientExpirationDate, String ingredientMemo) {
        this.ingredientName = ingredientName;
        this.ingredientCount = ingredientCount;
        this.ingredientSaveType = ingredientSaveType;
        this.ingredientCategory = ingredientCategory;
        this.ingredientPurchaseDate = ingredientPurchaseDate;
        this.ingredientExpirationDate = ingredientExpirationDate;
        this.ingredientMemo = ingredientMemo;
    }
}
