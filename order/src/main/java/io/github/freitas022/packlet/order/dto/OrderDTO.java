package io.github.freitas022.packlet.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private String id;
    private List<ItemDTO> items;
}
