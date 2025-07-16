package io.github.freitas022.packlet.catalog.service;

import io.github.freitas022.packlet.catalog.dto.OrderCreatedEvent;
import io.github.freitas022.packlet.catalog.model.Inventory;
import io.github.freitas022.packlet.catalog.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public void reserveStock(List<OrderCreatedEvent.ItemEventDTO> items) {
        log.info("Reservando estoque...");
        items.forEach(item -> {
            var inv = findInventoryOrThrow(item.getProductId());

            if ((inv.getQuantity() - inv.getReservedStock()) < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Estoque insuficiente para reservar.");
            }

            inv.setReservedStock(inv.getReservedStock() + item.getQuantity());
            inventoryRepository.save(inv);
        });
    }

    public void confirmStockUsage(Long productId, int qty) {
        Inventory inv = findInventoryOrThrow(productId);

        if (qty > inv.getReservedStock() || qty > inv.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade inválida para confirmação.");
        }

        inv.setReservedStock(inv.getReservedStock() - qty);
        inv.setQuantity(inv.getQuantity() - qty);
        inventoryRepository.save(inv);
        log.info("Baixa efetuada. ID do produto: {}, quantidade: {}", productId, qty);
    }

    public void rollbackReservedStock(Long productId, int qty) {
        Inventory inv = findInventoryOrThrow(productId);

        if (qty > inv.getReservedStock()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível estornar mais do que o reservado.");
        }

        inv.setReservedStock(inv.getReservedStock() - qty);
        inventoryRepository.save(inv);
        log.info("Estorno de reserva efetuado. ID do produto: {}, quantidade: {}", productId, qty);
    }

    public boolean needsRestock(Long productId) {
        Inventory inv = findInventoryOrThrow(productId);
        return inv.getQuantity() < inv.getMinimumStock();
    }

    private Inventory findInventoryOrThrow(Long productId) {
        return Optional.ofNullable(inventoryRepository.findByProductId(productId))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto com ID " + productId + " não encontrado no estoque."));
    }
}
