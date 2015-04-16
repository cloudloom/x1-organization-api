package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.AddressType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultAddressBuilder {

    private String name;
    private String building;
    private String street;
    private String region;
    private String city;
    private String district;
    private String state;
    private String country;
    private String zip;
    private AddressType addressType;

    private DefaultAddressBuilder(){

    }

    public static DefaultAddressBuilder anAddress(){
        return new DefaultAddressBuilder();
    }

    public DefaultAddressBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultAddressBuilder withBuilding(String building){
        this.building = building;
        return this;
    }

    public DefaultAddressBuilder withStreet(String street){
        this.street = street;
        return this;
    }

    public DefaultAddressBuilder withRegion(String region){
        this.region = region;
        return this;
    }

    public DefaultAddressBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public DefaultAddressBuilder withDistrict(String district){
        this.district = district;
        return this;
    }

    public DefaultAddressBuilder withState(String state){
        this.state = state;
        return this;
    }

    public DefaultAddressBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    public DefaultAddressBuilder withZip(String zip){
        this.zip = zip;
        return this;
    }

    public DefaultAddressBuilder withAddressType(AddressType addressType){
        this.addressType = addressType;
        return this;
    }

    public DefaultAddress build(){
        DefaultAddress address = new DefaultAddress();
        address.setName(name);
        address.setBuilding(building);
        address.setStreet(street);
        address.setRegion(region);
        address.setCity(city);
        address.setDistrict(district);
        address.setState(state);
        address.setCountry(country);
        address.setZip(zip);
        address.setAddressType(addressType);
        return address;
    }
}
