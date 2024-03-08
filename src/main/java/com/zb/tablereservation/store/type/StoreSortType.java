package com.zb.tablereservation.store.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;


@Getter
@AllArgsConstructor
public enum StoreSortType {
    NAME_ASC(getSort("name", true)),
    NAME_DESC(getSort("name", false)),

    STAR_SCORE_ASC(getSort("star_score_avg", true)),
    STAR_SCORE_DESC(getSort("star_score_avg", false)),

    DISTANCE_ASC(getSort("distance", true)),
    DISTANCE_DESC(getSort("distance", false));

    private final Sort sort;

    private static Sort getSort(String item, boolean asc){
        return asc
                ? Sort.by(Order.asc(item),Order.asc("id"))
                : Sort.by(Order.desc(item),Order.asc("id"));
    }
}
