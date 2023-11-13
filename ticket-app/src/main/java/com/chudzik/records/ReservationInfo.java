package com.chudzik.records;

import java.util.List;

public record ReservationInfo(
    String clientName,
    String clientSurname,
    Long screeningId,
    List<TicketInfo> tickets
) {
    
}
