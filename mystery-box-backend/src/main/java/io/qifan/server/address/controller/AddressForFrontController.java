
package io.qifan.server.address.controller;
import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/address")
@AllArgsConstructor
@DefaultFetcherOwner(AddressRepository.class)
@Transactional
public class AddressForFrontController {
    private final AddressRepository addressRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Address findById(@PathVariable String id) {
        return addressRepository.findById(id, AddressRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Address> query(@RequestBody QueryRequest<AddressSpec> queryRequest) {
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
        return addressRepository.save(addressInput.toEntity()).id();
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
}
