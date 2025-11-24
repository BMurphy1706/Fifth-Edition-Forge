import com.example.ddraft.models.SRD.class_Elements.ClassListItem

data class ClassesListResponse(
    val count: Int,
    val results: List<ClassListItem>
)