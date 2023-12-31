package com.sap.hanesbrand.service;


import com.sap.cloud.servicesdk.xbem.extension.sapcp.jms.MessagingServiceJmsConnectionFactory;
import com.sap.hanesbrand.dao.OutboundDeliveryDao;
import com.sap.hanesbrand.mapper.OutboundDeliveryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Service
@Slf4j
public class SAPEventMeshMessagingService {

    private final String QUEUE_PREFIX = "queue:";
    private final String QUEUE_NAME = "com/sap/hanesbrand/s4";

    private MessagingServiceJmsConnectionFactory connectionFactory;
    private S4Service s4Service;
    private OutboundDeliveryDao outboundDeliveryDao;
    private OutboundDeliveryMapper mapper;

    public SAPEventMeshMessagingService(MessagingServiceJmsConnectionFactory connectionFactory, S4Service s4Service,
                                        OutboundDeliveryDao outboundDeliveryDao, OutboundDeliveryMapper mapper) {
        this.connectionFactory = connectionFactory;
        this.s4Service = s4Service;
        this.outboundDeliveryDao = outboundDeliveryDao;
        this.mapper = mapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consume() {
        JMSContext context = connectionFactory.createContext();
        Queue queue = context.createQueue(QUEUE_PREFIX + QUEUE_NAME);
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(new DDMessageListener(s4Service, mapper, outboundDeliveryDao));
    }


}
