package io.github.qifan777.server.carriage.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.carriage.entity.CarriageTemplate;
import io.github.qifan777.server.carriage.entity.dto.CarriageTemplateInput;
import io.github.qifan777.server.carriage.entity.dto.CarriageTemplateSpec;
import io.github.qifan777.server.carriage.repository.CarriageTemplateRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/carriage-template")
@AllArgsConstructor
@DefaultFetcherOwner(CarriageTemplateRepository.class)
@SaCheckPermission("/carriage-template")
@Transactional
public class CarriageTemplateForAdminController {
    private final CarriageTemplateRepository carriageTemplateRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CarriageTemplate findById(@PathVariable String id) {
        return carriageTemplateRepository.findById(id, CarriageTemplateRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CarriageTemplate> query(@RequestBody QueryRequest<CarriageTemplateSpec> queryRequest) {
        return carriageTemplateRepository.findPage(queryRequest, CarriageTemplateRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CarriageTemplateInput CarriageTemplateInput) {
        if (CarriageTemplateInput.isValid()) {
            carriageTemplateRepository.updateAllInvalid();
        }
        return carriageTemplateRepository.save(CarriageTemplateInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        carriageTemplateRepository.deleteAllById(ids);
        return true;
    }
}