package io.qifan.server.address.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.address.entity.Address;
import io.qifan.server.address.entity.dto.AddressInput;
import io.qifan.server.address.entity.dto.AddressSpec;
import io.qifan.server.address.repository.AddressRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/address")
@AllArgsConstructor
@DefaultFetcherOwner(AddressRepository.class)
@SaCheckPermission("/address")
@Transactional
public class AddressForAdminController {
    private final AddressRepository addressRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Address findById(@PathVariable String id) {
        return addressRepository.findById(id, AddressRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Address> query(@RequestBody QueryRequest<AddressSpec> queryRequest) {
        return addressRepository.findPage(queryRequest, AddressRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated AddressInput addressInput) {
        return addressRepository.save(addressInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        addressRepository.deleteAllById(ids);
        return true;
    }
}