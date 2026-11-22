package es.com.kete1987.sportmonks.library.core.model.type;

import com.google.gson.annotations.SerializedName;
import es.com.kete1987.sportmonks.library.common.util.ModelCollections;

import java.util.List;

/** The types of one entity, as listed by {@code core/types/entities}. */
public class TypeEntity {
    @SerializedName("updated_at")
    private String updatedAt;
    private List<Type> types;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<Type> getTypes() {
        return ModelCollections.unmodifiable(types);
    }
}