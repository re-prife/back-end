package kr.hs.mirim.family.entity.ingredient;

import kr.hs.mirim.family.entity.BaseEntity;
import kr.hs.mirim.family.entity.Group.Group;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private SaveType ingredientSaveType;

    @Column(length = 20, nullable = false, name = "ingredient_category")
    private Category ingredientCategory;

    @Column(nullable = false, name = "ingredient_purchase_date")
    private Date ingredientPurchaseDate;

    @Column(nullable = false, name = "ingredient_expiration_date")
    private Date ingredientExpirationDate;

    @Column(length = 100, nullable = false, name = "ingredient_memo")
    private String ingredientMemo;

    @Column(length = 10, name = "ingredient_image_name", nullable = false)
    private String ingredientImageName;

    @OneToOne
    @JoinColumn(name = "group_id", unique = true)
    private Group group;

    @Builder
    public Ingredient(String ingredientName, int ingredientCount, SaveType ingredientSaveType, Category ingredientCategory,
                      Date ingredientPurchaseDate, Date ingredientExpirationDate, String ingredientMemo, String ingredientImageName) {
        this.ingredientName = ingredientName;
        this.ingredientCount = ingredientCount;
        this.ingredientSaveType = ingredientSaveType;
        this.ingredientCategory = ingredientCategory;
        this.ingredientPurchaseDate = ingredientPurchaseDate;
        this.ingredientExpirationDate = ingredientExpirationDate;
        this.ingredientMemo = ingredientMemo;
        this.ingredientImageName = ingredientImageName;
    }
}
