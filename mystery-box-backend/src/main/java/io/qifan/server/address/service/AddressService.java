package io.qifan.server.address.service;

    import io.qifan.server.address.entity.Address;
    import io.qifan.server.address.repository.AddressRepository;
    import lombok.AllArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

}