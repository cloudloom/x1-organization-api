package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author ffazil
 * @since 17/05/15
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PositionType {
    TOP_LEVEL_EXECUTIVES("Top Level Executives", "TOP LEVEL EXECUTIVES"),
    MID_LEVEL_EXECUTIVES("Mid Level Executives", "MID LEVEL EXECUTIVES"),
    MANAGERIAL("Managerial", "MANAGERIAL"),
    FRONT_OFFICE("Front Office", "FRONT OFFICE"),
    BACK_OFFICE("Back Office", "Back Office"),
    FIELD_STAFF("Field Staff", "FIELD STAFF"),
    SUPPORT_STAFF("Support Staff", "SUPPORT STAFF");

    private final String positionType;
    private final String abbreviation;

    PositionType(String positionType, String abbreviation){
        this.positionType = positionType;
        this.abbreviation = abbreviation;
    }

    public String getPositionType() {
        return positionType;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}