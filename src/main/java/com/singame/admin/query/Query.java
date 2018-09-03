package com.singame.admin.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class Query<T> {
  protected T filter;
  @Singular
  protected List<Sorter> sorters = new ArrayList<>();
  protected Integer offset = 0;
  protected Integer size = 20;
}