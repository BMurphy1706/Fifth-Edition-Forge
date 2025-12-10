import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.api.responses.ApiRef

data class ClassLevel(
    val level: Int,
    val ability_score_bonuses: Int,
    val prof_bonus: Int,
    val features: List<ApiListItem>,
    val index: String,
    val `class`: ApiRef? = null
)
