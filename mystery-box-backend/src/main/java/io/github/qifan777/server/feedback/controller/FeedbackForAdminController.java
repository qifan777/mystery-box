package io.github.qifan777.server.feedback.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.feedback.entity.Feedback;
import io.github.qifan777.server.feedback.entity.dto.FeedbackInput;
import io.github.qifan777.server.feedback.entity.dto.FeedbackSpec;
import io.github.qifan777.server.feedback.repository.FeedbackRepository;
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
@RequestMapping("admin/feedback")
@AllArgsConstructor
@DefaultFetcherOwner(FeedbackRepository.class)
@SaCheckPermission("/feedback")
@Transactional
public class FeedbackForAdminController {
    private final FeedbackRepository feedbackRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Feedback findById(@PathVariable String id) {
        return feedbackRepository.findById(id, FeedbackRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Feedback> query(@RequestBody QueryRequest<FeedbackSpec> queryRequest) {
        return feedbackRepository.findPage(queryRequest, FeedbackRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated FeedbackInput feedbackInput) {
        return feedbackRepository.save(feedbackInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        feedbackRepository.deleteAllById(ids);
        return true;
    }
}