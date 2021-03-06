package com.example.beliemeserver.controller.responsebody;

import com.example.beliemeserver.model.dto.ItemDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemResponse extends JSONResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String stuffName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String stuffEmoji;

    int num;

    String status;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ResponseFilter.class)
    HistoryResponse lastHistory;

    public ItemResponse(String stuffName, String stuffEmoji, int num, HistoryResponse lastHistory, String status) {
        super(true);
        this.stuffName = stuffName;
        this.stuffEmoji = stuffEmoji;
        this.num = num;
        this.lastHistory = lastHistory;
        this.status = status;
    }

    private ItemResponse(boolean isNested) {
        super(false);
    }

    public static ItemResponse responseWillBeIgnore() {
        return new ItemResponse(false);
    }

    public ItemResponse toItemResponseWithoutLastHistory() {
        return new ItemResponse(stuffName, stuffEmoji, num, HistoryResponse.responseWillBeIgnore(), status);
    }

    public static ItemResponse from(ItemDto itemDto) {
        ItemResponse itemResponse = new ItemResponse(null, null, itemDto.getNum(), null, itemDto.getStatus().toString());
        if(itemDto.getStuff() != null) {
            itemResponse.setStuffName(itemDto.getStuff().getName());
            itemResponse.setStuffEmoji(itemDto.getStuff().getEmoji());
        }

        if(itemDto.getLastHistory() != null) {
            itemResponse.setLastHistory(HistoryResponse.from(itemDto.getLastHistory()));
        }
        return itemResponse;
    }
}
