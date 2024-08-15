package io.github.qifan777.server.slideshow.service;

import io.github.qifan777.server.slideshow.repository.SlideshowRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SlideshowService {
    private final SlideshowRepository slideshowRepository;

}