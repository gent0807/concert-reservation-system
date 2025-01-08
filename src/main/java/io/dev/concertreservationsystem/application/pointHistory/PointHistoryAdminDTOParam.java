package io.dev.concertreservationsystem.application.pointHistory;

import lombok.Builder;

@Builder
public record PointHistoryAdminDTOParam(
    Long userId
) {

}
