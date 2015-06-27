package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.AddressType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultAddressResource;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultAddressResourceBuilder {
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
    private boolean defaultAddress;

    private DefaultAddressResourceBuilder(){

    }

    public static DefaultAddressResourceBuilder anAddress(){
        return new DefaultAddressResourceBuilder();
    }

    public DefaultAddressResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultAddressResourceBuilder withBuilding(String building){
        this.building = building;
        return this;
    }

    public DefaultAddressResourceBuilder withStreet(String street){
        this.street = street;
        return this;
    }

    public DefaultAddressResourceBuilder withRegion(String region){
        this.region = region;
        return this;
    }

    public DefaultAddressResourceBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public DefaultAddressResourceBuilder withDistrict(String district){
        this.district = district;
        return this;
    }

    public DefaultAddressResourceBuilder withState(String state){
        this.state = state;
        return this;
    }

    public DefaultAddressResourceBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    public DefaultAddressResourceBuilder withZip(String zip){
        this.zip = zip;
        return this;
    }

    public DefaultAddressResourceBuilder withAddressType(AddressType addressType){
        this.addressType = addressType;
        return this;
    }

    public DefaultAddressResourceBuilder withDefaultAddressResource(boolean defaultAddress){
        this.defaultAddress = defaultAddress;
        return this;
    }

    public DefaultAddressResource build(){
        DefaultAddressResource address = new DefaultAddressResource();
        address.setName(name);
        address.setBuilding(building);
        address.setStreet(street);
        address.setRegion(region);
        //address.setCity(city);
        address.setDistrict(district);
        address.setState(state);
        address.setCountry(country);
        address.setZip(zip);
        address.setAddressType(addressType);
        address.setDefaultAddress(defaultAddress);
        return address;
    }
}