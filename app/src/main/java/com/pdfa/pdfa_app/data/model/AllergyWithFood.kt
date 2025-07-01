import androidx.room.Embedded
import androidx.room.Relation
 
data class AllergyWithFood(
    @Embedded
    val allergy: Allergy,
 
    @Relation(
        parentColumn = "food_id",
        entityColumn = "id"
    )
    val food: Food
)