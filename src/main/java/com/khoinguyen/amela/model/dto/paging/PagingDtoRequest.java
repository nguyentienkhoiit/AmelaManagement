package com.khoinguyen.amela.model.dto.paging;

import static com.khoinguyen.amela.util.Constant.PAGE_INDEX;
import static com.khoinguyen.amela.util.Constant.PAGE_SIZE;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PagingDtoRequest {
    String pageIndex = PAGE_INDEX.toString();
    String pageSize = PAGE_SIZE.toString();
    String text;
    String by = "id";
    String type = "asc";

    public long getPageIndex() {
        return parseLongOrDefault(pageIndex, PAGE_INDEX);
    }

    public long getPageSize() {
        return parseLongOrDefault(pageSize, PAGE_SIZE);
    }

    protected long parseLongOrDefault(String value, long defaultValue) {
        try {
            long index = Long.parseLong(value);
            return index > 0 ? index : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getBy() {
        return switch (by) {
            case "code" -> "code";
            case "fn" -> "firstname";
            case "ln" -> "lastname";
            case "e" -> "email";
            case "gen" -> "gender";
            default -> "id";
        };
    }

    public String getType() {
        if ("asc".equalsIgnoreCase(type)) return "asc";
        else if ("desc".equalsIgnoreCase(type)) return "desc";
        else return "asc";
    }

    public String getText() {
        return text == null ? "" : text;
    }
}
