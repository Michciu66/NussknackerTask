package com.chudzik.enums;

import java.math.BigDecimal;

public enum TicketType {
    ADULT(BigDecimal.valueOf(25)),
    STUDENT(BigDecimal.valueOf(18)),
    CHILD(BigDecimal.valueOf(12.5));

    private final BigDecimal cost;

    TicketType(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
