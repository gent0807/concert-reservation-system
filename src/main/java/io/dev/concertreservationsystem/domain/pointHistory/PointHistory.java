package io.dev.concertreservationsystem.domain.pointHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PointHistory {

    @Id
    private Long pointHistoryId;
}
