package com.sap.hanesbrand.controller;


import cds.gen.outbounddeliveryservice.OutboundDelivery;
import com.sap.hanesbrand.client.dto.ConfirmShipmentDto;
import com.sap.hanesbrand.dao.OutboundDeliveryDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OutboundDeliveryController {

    private final OutboundDeliveryDao outboundDeliveryRepository;
    private static final String SHIPMENT_CONFIRM = "2";
    private static final String STATUS_DESCRIPTION = "Shipment Confirmed";

    @PostMapping("/confirmShipment")
    public void confirmShipment(@RequestBody ConfirmShipmentDto shipmentDto) {
        log.info("OutboundDeliveryController: confirmShipment : {} ", shipmentDto.toString());
        OutboundDelivery outboundDelivery = outboundDeliveryRepository.selectOutboundDeliveryById(shipmentDto.getDeliveryDocument());
        outboundDeliveryRepository.updateStatus(outboundDelivery.getDeliveryDocument(), SHIPMENT_CONFIRM, STATUS_DESCRIPTION);
    }


}
