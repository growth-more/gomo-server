package com.gomo.app.common;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public abstract class LogicalDeleteBaseAudit extends BaseAudit {

    @Column(name = "deleted")
    protected LocalDateTime deletedAt;

    protected void delete() {
        deletedAt = LocalDateTime.now();
    }
}
