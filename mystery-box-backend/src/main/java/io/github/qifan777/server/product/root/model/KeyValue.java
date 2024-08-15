package io.github.qifan777.server.product.root.model;

import lombok.Data;

import java.util.List;

@Data
public class KeyValue {

  private String name;
  private List<String> values;
}