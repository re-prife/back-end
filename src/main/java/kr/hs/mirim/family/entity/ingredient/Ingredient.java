package kr.hs.mirim.family.entity.ingredient;

import kr.hs.mirim.family.entity.Group.Group;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ingredient_tb")
public class Ingredient {
    @Id
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Column(length = 30, nullable = false, name = "ingredient_name")
    private String ingredientName;

    @Column(nullable = false, name="ingredient_count")
    private int ingredientCount;

    @Column(columnDefinition = "CHAR(10)", nullable = false, name = "ingredient_save_type")
    private String ingredientSaveType;

    @Column(length = 20, nullable = false, name = "ingredient_category")
    private String ingredientCategory;

    @Column(nullable = false, name = "ingredient_purchase_date")
    private Date ingredientPurchaseDate;

    @Column(nullable = false, name = "ingredient_expiration_date")
    private Date ingredientExpirationDate;

    @Column(length = 100, nullable = false, name = "ingredient_memo")
    private String ingredientMemo;

    @Column(length = 10, name = "ingredient_image_name")
    private String ingredientImageName;

    @OneToOne
    @JoinColumn(name = "group_id", unique = true)
    private Group group;
}
