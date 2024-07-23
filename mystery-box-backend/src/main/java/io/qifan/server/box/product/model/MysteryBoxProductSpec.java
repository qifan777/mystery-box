package io.qifan.server.box.product.model;

import io.qifan.server.product.root.entity.dto.ProductSpec;
import lombok.Data;

@Data
public class MysteryBoxProductSpec {
    ProductSpec productSpec;
    String boxId;
    Boolean exist;
}
