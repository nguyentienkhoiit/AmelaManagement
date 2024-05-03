package com.khoinguyen.amela.model.dto.paging;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static com.khoinguyen.amela.util.Constant.PAGE_INDEX;
import static com.khoinguyen.amela.util.Constant.PAGE_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagingDtoRequest {
    String pageIndex = PAGE_INDEX.toString();
    String pageSize = PAGE_SIZE.toString();
    String text;
    String sortName = "id";
    String sort = "asc";

    public long getPageIndex() {
        return parseLongOrDefault(pageIndex, PAGE_INDEX);
    }

    public long getPageSize() {
        return parseLongOrDefault(pageSize, PAGE_SIZE);
    }

    private long parseLongOrDefault(String value, long defaultValue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getSortName() {
        return switch (sortName) {
            case "fn" -> "firstName";
            case "ln" -> "lastName";
            case "e" -> "email";
            default -> "id";
        };
    }

    public String getSort() {
        if ("asc".equalsIgnoreCase(sort)) return "asc";
        else if ("desc".equalsIgnoreCase(sort)) return "desc";
        else return "asc";
    }

    public String getText() {
        return text == null ? "" : text;
    }
}
