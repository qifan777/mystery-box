package io.github.qifan777.server.carriage.controller;

import io.github.qifan777.server.carriage.entity.CarriageTemplate;
import io.github.qifan777.server.carriage.entity.dto.CarriageTemplateSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.carriage.repository.CarriageTemplateRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/carriage-template")
@AllArgsConstructor
@DefaultFetcherOwner(CarriageTemplateRepository.class)
@Transactional
public class CarriageTemplateForFrontController {
    private final CarriageTemplateRepository carriageTemplateRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CarriageTemplate findById(@PathVariable String id) {
        return carriageTemplateRepository.findById(id, CarriageTemplateRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CarriageTemplate> query(@RequestBody QueryRequest<CarriageTemplateSpec> queryRequest) {
        return carriageTemplateRepository.findPage(queryRequest, CarriageTemplateRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

}
