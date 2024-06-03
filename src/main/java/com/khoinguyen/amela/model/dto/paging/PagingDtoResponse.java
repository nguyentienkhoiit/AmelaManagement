package com.khoinguyen.amela.model.dto.paging;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Builder;

@Builder
public record PagingDtoResponse<T>(Long totalPage, Long totalElement, List<T> data) {
    public List<Integer> getTotalPageList(List<T> data) {
        if (data.isEmpty()) return new ArrayList<>();
        return IntStream.rangeClosed(1, Math.toIntExact(totalPage)).boxed().toList();
    }
}
