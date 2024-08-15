package io.github.qifan777.server.box.product.model;

import io.github.qifan777.server.product.root.entity.dto.ProductSpec;
import lombok.Data;

@Data
public class MysteryBoxProductSpec {
    ProductSpec productSpec;
    String boxId;
    Boolean exist;
}
