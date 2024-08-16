package io.github.qifan777.server.feedback.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/feedback")
@AllArgsConstructor
@DefaultFetcherOwner(FeedbackRepository.class)
@Transactional
public class FeedbackForFrontController {
    private final FeedbackRepository feedbackRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Feedback findById(@PathVariable String id) {
        return feedbackRepository.findById(id, FeedbackRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Feedback> query(@RequestBody QueryRequest<FeedbackSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return feedbackRepository.findPage(queryRequest, FeedbackRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated FeedbackInput feedbackInput) {
        if (StringUtils.hasText(feedbackInput.getId())) {
            Feedback feedback = feedbackRepository.findById(feedbackInput.getId(), FeedbackRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!feedback.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return feedbackRepository.save(feedbackInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        feedbackRepository.findByIds(ids, FeedbackRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(feedback -> {
            if (!feedback.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        feedbackRepository.deleteAllById(ids);
        return true;
    }
}
