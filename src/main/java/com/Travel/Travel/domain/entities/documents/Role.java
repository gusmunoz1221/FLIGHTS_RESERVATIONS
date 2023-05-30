package com.Travel.Travel.domain.entities.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {
    @Field(name = "granted_authorities")//es equivalente a escribir @colum
    private Set<String> grantedAuthorities;
}
