package org.fed333.ticket.booking.app.config.jsondateparsing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public interface EventMixin {
    @JsonDeserialize(using = CustomDateDeserializer.class)
    Date getDate();
}