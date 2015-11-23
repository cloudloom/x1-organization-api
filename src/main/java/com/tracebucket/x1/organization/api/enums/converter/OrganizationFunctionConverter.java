package com.tracebucket.x1.organization.api.enums.converter;

import com.tracebucket.x1.organization.api.domain.OrganizationFunction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by sadath on 23-Nov-2015.
 */
@Converter(autoApply = true)
public class OrganizationFunctionConverter implements AttributeConverter<OrganizationFunction, String> {

    @Override
    public String convertToDatabaseColumn(OrganizationFunction organizationFunction) {
        switch (organizationFunction) {
            case SALES:
                return "SALES";
            case PURCHASE:
                return "PURCHASE";
            default:
                throw new IllegalArgumentException("Unknown value: " + organizationFunction);
        }
    }

    @Override
    public OrganizationFunction convertToEntityAttribute(String s) {
        switch (s) {
            case "SALES":
                return OrganizationFunction.SALES;
            case "PURCHASE":
                return OrganizationFunction.PURCHASE;
            default:
                throw new IllegalArgumentException("Unknown value: " + s);
        }
    }
}