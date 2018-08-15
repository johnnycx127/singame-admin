package com.singame.admin.query;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Query<T> {
  protected T filter;
  protected List<Sorter> sorters;
  protected Integer offset;
  protected Integer size;
}