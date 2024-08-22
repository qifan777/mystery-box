package io.github.qifan777.server.carriage.service;

import io.github.qifan777.server.address.entity.Address;
import io.github.qifan777.server.address.repository.AddressRepository;
import io.github.qifan777.server.carriage.repository.CarriageTemplateRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CarriageTemplateService {
    private final CarriageTemplateRepository carriageTemplateRepository;
    private final AddressRepository addressRepository;

    public BigDecimal calculate(String addressId, BigDecimal productAmount) {
        Optional<Address> userAddressOpt = addressRepository.findUserAddressById(addressId);
        if (userAddressOpt.isEmpty()) return BigDecimal.ZERO;
        Address address = userAddressOpt.get();
        return carriageTemplateRepository.findValid()
                .configs()
                .stream()
                .filter(config -> String.join(";", config.getProvince()).contains((address.province())))
                .findFirst()
                .orElseThrow(() -> new BusinessException("当前省份不支持发货请联系客服"))
                .getPriceRanges()
                .stream()
                .filter(priceRange -> productAmount.compareTo(priceRange.getMinPrice()) >= 0 &&
                                      productAmount.compareTo(priceRange.getMaxPrice()) <= 0)
                .findFirst()
                .orElseThrow(() -> new BusinessException("运费模板不适应与该订单，请联系客服"))
                .getCarriage();
    }

}