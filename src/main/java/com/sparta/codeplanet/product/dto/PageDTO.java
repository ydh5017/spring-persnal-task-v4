package com.sparta.codeplanet.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Builder
@AllArgsConstructor
public class PageDTO {

    private int page;
    private int size;
    private String sortBy;

    public Pageable toPageable() {
        if (Objects.isNull(sortBy)) {
            return PageRequest.of(page - 1, size);
        } else {
            return PageRequest.of(page - 1, size, Sort.by(sortBy).descending());
        }
    }

    public Pageable toPageable(String sortBy) {
        return PageRequest.of(page - 1, size, Sort.by(sortBy).descending());
    }
}
