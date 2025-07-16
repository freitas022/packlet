package io.github.freitas022.packlet.catalog.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.github.freitas022.packlet.catalog.dto.OrderCreatedEvent;
import io.github.freitas022.packlet.catalog.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    InventoryService inventoryService;

    @SqsListener(value = "${app.queues.orderCreated}", messageVisibilitySeconds = "30", maxMessagesPerPoll = "10", acknowledgementMode = "ON_SUCCESS")
    public void receiveOrderCreated(String payload) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            log.info("Processando pedido... # {}", event.getId());
            inventoryService.reserveStock(event.getItems());
        } catch (Exception e) {
            log.error("Erro ao processar o pedido: {}", e.getMessage(), e);
        }
    }
}
