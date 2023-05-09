package com.Travel.Travel.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourDtoResponse {
    private Long id;
    private Set<UUID> ticketIds;
    private Set<UUID> reservationIds;
}
