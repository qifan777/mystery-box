package io.github.qifan777.server.address.entity.dto;

import io.github.qifan777.server.address.entity.Address;
import io.github.qifan777.server.address.entity.AddressDraft;
import io.github.qifan777.server.address.entity.AddressFetcher;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.internal.GeneratedBy;
import org.babyfish.jimmer.sql.fetcher.DtoMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@GeneratedBy(
        file = "<mystery-box-backend>/src/main/dto/Address.dto"
)
@GenEntity
public class AddressView implements View<Address> {
    public static final DtoMetadata<Address, AddressView> METADATA =
        new DtoMetadata<Address, AddressView>(
            AddressFetcher.$
                .latitude()
                .longitude()
                .details()
                .province()
                .city()
                .district()
                .phoneNumber()
                .realName()
                .houseNumber()
                .top(),
            AddressView::new
    );

    private Double latitude;

    private Double longitude;

    private String details;

    private String province;

    private String city;

    private String district;

    private String phoneNumber;

    private String realName;

    private String houseNumber;

    private Boolean top;

    public AddressView() {
    }

    public AddressView(@NotNull Address base) {
        this.latitude = base.latitude();
        this.longitude = base.longitude();
        this.details = base.details();
        this.province = base.province();
        this.city = base.city();
        this.district = base.district();
        this.phoneNumber = base.phoneNumber();
        this.realName = base.realName();
        this.houseNumber = base.houseNumber();
        this.top = base.top();
    }

    public double getLatitude() {
        if (latitude == null) {
            throw new IllegalStateException("The property \"latitude\" is not specified");
        }
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        if (longitude == null) {
            throw new IllegalStateException("The property \"longitude\" is not specified");
        }
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NotNull
    public String getDetails() {
        if (details == null) {
            throw new IllegalStateException("The property \"details\" is not specified");
        }
        return details;
    }

    public void setDetails(@NotNull String details) {
        this.details = details;
    }

    @NotNull
    public String getProvince() {
        if (province == null) {
            throw new IllegalStateException("The property \"province\" is not specified");
        }
        return province;
    }

    public void setProvince(@NotNull String province) {
        this.province = province;
    }

    @NotNull
    public String getCity() {
        if (city == null) {
            throw new IllegalStateException("The property \"city\" is not specified");
        }
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    @NotNull
    public String getDistrict() {
        if (district == null) {
            throw new IllegalStateException("The property \"district\" is not specified");
        }
        return district;
    }

    public void setDistrict(@NotNull String district) {
        this.district = district;
    }

    @NotNull
    public String getPhoneNumber() {
        if (phoneNumber == null) {
            throw new IllegalStateException("The property \"phoneNumber\" is not specified");
        }
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotNull
    public String getRealName() {
        if (realName == null) {
            throw new IllegalStateException("The property \"realName\" is not specified");
        }
        return realName;
    }

    public void setRealName(@NotNull String realName) {
        this.realName = realName;
    }

    @NotNull
    public String getHouseNumber() {
        if (houseNumber == null) {
            throw new IllegalStateException("The property \"houseNumber\" is not specified");
        }
        return houseNumber;
    }

    public void setHouseNumber(@NotNull String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public boolean isTop() {
        if (top == null) {
            throw new IllegalStateException("The property \"top\" is not specified");
        }
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    @Override
    public Address toEntity() {
        return AddressDraft.$.produce(__draft -> {
            __draft.setLatitude(latitude);
            __draft.setLongitude(longitude);
            __draft.setDetails(details);
            __draft.setProvince(province);
            __draft.setCity(city);
            __draft.setDistrict(district);
            __draft.setPhoneNumber(phoneNumber);
            __draft.setRealName(realName);
            __draft.setHouseNumber(houseNumber);
            __draft.setTop(top);
        });
    }

    @Override
    public int hashCode() {
        int hash = Double.hashCode(latitude);
        hash = hash * 31 + Double.hashCode(longitude);
        hash = hash * 31 + Objects.hashCode(details);
        hash = hash * 31 + Objects.hashCode(province);
        hash = hash * 31 + Objects.hashCode(city);
        hash = hash * 31 + Objects.hashCode(district);
        hash = hash * 31 + Objects.hashCode(phoneNumber);
        hash = hash * 31 + Objects.hashCode(realName);
        hash = hash * 31 + Objects.hashCode(houseNumber);
        hash = hash * 31 + Boolean.hashCode(top);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AddressView other = (AddressView) o;
        if (latitude != other.latitude) {
            return false;
        }
        if (longitude != other.longitude) {
            return false;
        }
        if (!Objects.equals(details, other.details)) {
            return false;
        }
        if (!Objects.equals(province, other.province)) {
            return false;
        }
        if (!Objects.equals(city, other.city)) {
            return false;
        }
        if (!Objects.equals(district, other.district)) {
            return false;
        }
        if (!Objects.equals(phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(realName, other.realName)) {
            return false;
        }
        if (!Objects.equals(houseNumber, other.houseNumber)) {
            return false;
        }
        if (top != other.top) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AdressView").append('(');
        builder.append("latitude=").append(latitude);
        builder.append(", longitude=").append(longitude);
        builder.append(", details=").append(details);
        builder.append(", province=").append(province);
        builder.append(", city=").append(city);
        builder.append(", district=").append(district);
        builder.append(", phoneNumber=").append(phoneNumber);
        builder.append(", realName=").append(realName);
        builder.append(", houseNumber=").append(houseNumber);
        builder.append(", top=").append(top);
        builder.append(')');
        return builder.toString();
    }
}
