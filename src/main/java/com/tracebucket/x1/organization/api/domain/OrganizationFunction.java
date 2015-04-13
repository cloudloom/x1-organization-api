package com.tracebucket.x1.organization.api.domain;

/**
 * Created by ffl on 16-04-2014.
 */
public enum OrganizationFunction {
    SALES("Sales","SALES"),
    PURCHASE("Purchase","PURCHASE");

    private final String organizationFunction;
    private final String abbreviation;

    OrganizationFunction(String organizationFunction, String abbreviation){
        this.organizationFunction = organizationFunction;
        this.abbreviation = abbreviation;
    }

    public String getOrganizationFunction(){
        return organizationFunction;
    }

    public String getAbbreviation(){
        return abbreviation;
    }
}
