package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.AddressType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultAddressResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultAddressResourceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultAddressResourceFixture {
    public static DefaultAddressResource standardAddress() {
        Set<AddressType> addressTypes = new HashSet<AddressType>();
        addressTypes.add(AddressType.HEAD_OFFICE);
        DefaultAddressResource address = DefaultAddressResourceBuilder.anAddress()
                .withName("MMP")
                .withBuilding("XYZ Complex")
                .withStreet("KR Road")
                .withRegion("Basavanagudi")
                .withCity("Bangalore")
                .withDistrict("Bangalore")
                .withState("Karnataka")
                .withCountry("India")
                .withZip("560004")
                .withAddressType(AddressType.BRANCH)
                .build();
        return address;
    }

    public static DefaultAddressResource headOffice() {
        Set<AddressType> addressTypes = new HashSet<AddressType>();
        addressTypes.add(AddressType.HEAD_OFFICE);
        DefaultAddressResource address = DefaultAddressResourceBuilder.anAddress()
                .withName("MMPBV")
                .withBuilding("Vlasveen")
                .withStreet("9301 PS")
                .withRegion("Roden")
                .withCity("Roden")
                .withDistrict("Roden")
                .withState("Roden")
                .withCountry("NL")
                .withZip("9301 PS")
                .withAddressType(AddressType.HEAD_OFFICE)
                .build();
        return address;
    }
}