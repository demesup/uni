package com.demesup.domain.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(
        name = "seminar",
        value = Seminar.class
    ),
    @JsonSubTypes.Type(
        name = "lab",
        value = Lab.class
    ),
    @JsonSubTypes.Type(
        name = "course",
        value = Course.class
    )
})
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor
public abstract class ItemInfo implements Serializable {
}
