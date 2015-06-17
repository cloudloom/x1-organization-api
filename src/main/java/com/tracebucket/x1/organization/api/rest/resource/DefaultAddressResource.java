package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.AddressType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultAddressResource extends BaseResource {

    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[a-zA-Z \\- \\/ \\@]*$")
    private String name;

    @Size(min = 1, max = 250)
    private String building;

    @Size(min = 1, max = 250)
    private String street;

    @Size(min = 1, max = 250)
    private String region;

    /*@Size(min = 1, max = 250)
    private String city;
*/
    @Size(min = 1, max = 250)
    private String district;


    private String state;
    private String country;

    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z \\- \\/]*$")
    private String zip;

    private AddressType addressType;
    private boolean defaultAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /*public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
*/
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}