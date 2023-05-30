package com.Travel.Travel.domain.entities.documents;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "app_users")
public class AppUserDocument {
    @Id
    private String id;
    private String username;
    private String dni;
    private boolean enabled;
    private String password;
    private Role role;
}
