package io.github.qifan777.server.address.service;

import io.github.qifan777.server.address.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

}