
package io.github.qifan777.server.address.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.qifan777.server.address.entity.Address;
import io.github.qifan777.server.address.entity.AddressDraft;
import io.github.qifan777.server.address.entity.AddressTable;
import io.github.qifan777.server.address.entity.dto.AddressInput;
import io.github.qifan777.server.address.entity.dto.AddressSpec;
import io.github.qifan777.server.address.repository.AddressRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.infrastructure.model.TenantMapProperty;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("front/address")
@AllArgsConstructor
@DefaultFetcherOwner(AddressRepository.class)
@Transactional
public class AddressForFrontController {
    private final AddressRepository addressRepository;
    private final TenantMapProperty tenantMapProperty;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Address findById(@PathVariable String id) {
        return addressRepository.findById(id, AddressRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Address> query(@RequestBody QueryRequest<AddressSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return addressRepository.findPage(queryRequest, AddressRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated AddressInput addressInput) {
        if (StringUtils.hasText(addressInput.getId())) {
            Address address = addressRepository.findById(addressInput.getId(), AddressRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!address.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        // 首个地址设为默认
        if (addressRepository.findUserAll(StpUtil.getLoginIdAsString()).isEmpty()) {
            addressInput.setTop(true);
        }
        return addressRepository.save(AddressDraft.$.produce(addressInput.toEntity(), draft -> {
            GeoCoderResponse geoCoderResponse = new RestTemplate()
                    .getForObject("https://apis.map.qq.com/ws/geocoder/v1/?address=" + addressInput.getDetails() + "&key=" + tenantMapProperty.getKey(),
                            GeoCoderResponse.class);
            GeoCoderResponse.Address addressComponents = geoCoderResponse.getResult().getAddressComponents();
            draft.setCity(addressComponents.getCity());
            draft.setProvince(addressComponents.getProvince());
            draft.setDistrict(addressComponents.getDistrict());
        })).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        addressRepository.findByIds(ids, AddressRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(address -> {
            if (!address.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        addressRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("top")
    public Boolean top(@RequestParam String id) {
        AddressTable t = AddressTable.$;
        // 设置该用户的其他地址为非默认
        addressRepository.sql().createUpdate(t)
                .set(t.top(), false)
                .where(t.creator().id().eq(StpUtil.getLoginIdAsString()))
                .execute();
        // 设置传入的地址为默认
        addressRepository.update(AddressDraft.$.produce(draft -> draft.setId(id)
                .setTop(true)));
        return true;

    }

    @Data
    public static class GeoCoderResponse {
        private Result result;

        @Data
        public static class Result {
            private Location location;
            @JsonProperty("address_components")
            private Address addressComponents;
        }

        @Data
        public static class Location {
            private Double lat;
            private Double lng;
        }

        @Data
        public static class Address {
            private String province;
            private String city;
            private String district;
            private String street;
        }
    }
}
