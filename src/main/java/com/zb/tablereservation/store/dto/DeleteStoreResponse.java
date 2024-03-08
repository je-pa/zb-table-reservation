package com.zb.tablereservation.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DeleteStoreResponse {
    Long storeId;
}
