package com.shop.projectlion.domain.delivery.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<DeliveryDto> getDeliveryInfo(Member member) {
        List<DeliveryDto> result = new ArrayList<>();
        List<Delivery> deliveries = deliveryRepository.findByMember(member);

        deliveries.forEach(delivery -> {
            DeliveryDto deliveryDto = DeliveryDto.builder()
                    .deliveryId(delivery.getId())
                    .deliveryName(delivery.getDeliveryName())
                    .deliveryFee(delivery.getDeliveryFee())
                    .build();

            result.add(deliveryDto);
        });

        return result;
    }
}
