package kr.hs.mirim.family.entity.ingredient;

import kr.hs.mirim.family.entity.BaseEntity;
import kr.hs.mirim.family.entity.group.Group;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    @Enumerated(value = EnumType.STRING)
    private IngredientSaveType ingredientSaveType;

    @Column(nullable = false, name = "ingredient_category", columnDefinition = "CHAR(15)")
    @Enumerated(value = EnumType.STRING)
    private IngredientCategory ingredientCategory;

    @Column(nullable = false, name = "ingredient_purchase_date")
    private LocalDate ingredientPurchaseDate;

    @Column(nullable = false, name = "ingredient_expiration_date")
    private LocalDate ingredientExpirationDate;

    @Column(length = 100, nullable = false, name = "ingredient_memo")
    private String ingredientMemo;

    @Column(length = 10, name = "ingredient_image_name", nullable = false)
    private String ingredientImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Builder
    public Ingredient(String ingredientName, int ingredientCount, IngredientSaveType ingredientSaveType, IngredientCategory ingredientCategory,
                      LocalDate ingredientPurchaseDate, LocalDate ingredientExpirationDate, String ingredientMemo, String ingredientImageName, Group group) {
        this.ingredientName = ingredientName;
        this.ingredientCount = ingredientCount;
        this.ingredientSaveType = ingredientSaveType;
        this.ingredientCategory = ingredientCategory;
        this.ingredientPurchaseDate = ingredientPurchaseDate;
        this.ingredientExpirationDate = ingredientExpirationDate;
        this.ingredientMemo = ingredientMemo;
        this.ingredientImageName = ingredientImageName;
        this.group = group;
    }
}
