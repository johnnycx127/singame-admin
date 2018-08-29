package com.singame.admin.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Query<T> {
  protected T filter;
  protected List<Sorter> sorters = new ArrayList<>();
  protected Integer offset = 0;
  protected Integer size = 20;
}