package com.khoinguyen.amela.model.dto.paging;

import lombok.Builder;

@Builder
public record ServiceResponse<T>(boolean status, String column, T data) {}
