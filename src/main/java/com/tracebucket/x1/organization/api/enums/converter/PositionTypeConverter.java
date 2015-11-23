package com.tracebucket.x1.organization.api.enums.converter;

import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by sadath on 23-Nov-2015.
 */
@Converter(autoApply = true)
public class PositionTypeConverter implements AttributeConverter<PositionType, String> {
    @Override
    public String convertToDatabaseColumn(PositionType positionType) {
        switch (positionType) {
            case TOP_LEVEL_EXECUTIVES:
                return "TOP_LEVEL_EXECUTIVES";
            case MID_LEVEL_EXECUTIVES:
                return "MID_LEVEL_EXECUTIVES";
            case MANAGERIAL:
                return "MANAGERIAL";
            case FRONT_OFFICE:
                return "FRONT_OFFICE";
            case BACK_OFFICE:
                return "BACK_OFFICE";
            case FIELD_STAFF:
                return "FIELD_STAFF";
            case SUPPORT_STAFF:
                return "SUPPORT_STAFF";
            case CUSTOMER_SERVICE_AGENT:
                return "CUSTOMER_SERVICE_AGENT";
            default:
                throw new IllegalArgumentException("Unknown value: " + positionType);
        }
    }

    @Override
    public PositionType convertToEntityAttribute(String s) {
        switch (s) {
            case "TOP_LEVEL_EXECUTIVES":
                return PositionType.TOP_LEVEL_EXECUTIVES;
            case "MID_LEVEL_EXECUTIVES":
                return PositionType.MID_LEVEL_EXECUTIVES;
            case "MANAGERIAL":
                return PositionType.MANAGERIAL;
            case "FRONT_OFFICE":
                return PositionType.FRONT_OFFICE;
            case "BACK_OFFICE":
                return PositionType.BACK_OFFICE;
            case "FIELD_STAFF":
                return PositionType.FIELD_STAFF;
            case "SUPPORT_STAFF":
                return PositionType.SUPPORT_STAFF;
            case "CUSTOMER_SERVICE_AGENT":
                return PositionType.CUSTOMER_SERVICE_AGENT;
            default:
                throw new IllegalArgumentException("Unknown value: " + s);
        }
    }
}
