package io.hmheng.grading.utils;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by pabonaj on 6/28/17.
 */
public class PageUtils {

  public static <T> List<T> getPageableChunk(Pageable pageable, List<T> inputList, int totalSize) {
    if(pageable!=null && totalSize > pageable.getPageSize()) {
      int start = pageable.getPageNumber() * pageable.getPageSize();
      int end = Math.min(inputList.size(), start + pageable.getPageSize());
      inputList = inputList.subList(start, end);
    }
    return inputList;
  }
}