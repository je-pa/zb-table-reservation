package com.zb.tablereservation.store.controller;

import com.zb.tablereservation.store.dto.*;
import com.zb.tablereservation.store.service.StoreService;
import com.zb.tablereservation.store.type.StoreSortType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    /**
     * 파트너가 매장을 생성한다.
     * @param request 매장 정보를 받아온다.
     * @return 생성된 매장 정보를 반환한다.
     */
    @PostMapping("")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<CreateStore.Response> create(
            @Valid @RequestBody CreateStore.Request request){
        return ResponseEntity.ok(storeService.create(request));
    }

    /**
     * 하나의 매장 상세정보 확인
     * @param storeId 매장 아이디
     * @return 매장아이디에 해당하는 상세 정보를 반환한다.
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailsResponse> readDetails(
            @PathVariable Long storeId){
        return ResponseEntity.ok(storeService.findDetails(storeId));
    }

    /**
     * 매장리스트를 조회한다.
     * @param page 페이지 넘버
     * @param size 한 페이지에서 볼 매장 개수
     * @param sortType 정렬기준
     * @param curLatitude 현재 위도
     * @param curLongitude 현재 경도
     * @return 페이지에관한 정보와 해당하는 페이지에 대한 item인 매장 리스트를 볼 수 있다.
     */
    @GetMapping("/list")
    public ResponseEntity<Page<StoreListResponse>> readList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "NAME_ASC")StoreSortType sortType,
            @RequestParam(defaultValue = "0") Double curLatitude,
            @RequestParam(defaultValue = "0") Double curLongitude){
        return ResponseEntity.ok(storeService.findAll(
                PageRequest.of(page,size,sortType.getSort()),
                curLatitude, curLongitude
        ));
    }

    /**
     * 매장 이름을 검색해서 매장리스트를 조회한다.
     * @param page 페이지 넘버
     * @param size 한 페이지에서 볼 매장 개수
     * @param sortType 정렬기준
     * @param curLatitude 현재 위도
     * @param curLongitude 현재 경도
     * @param name 매장이름 검색 키워드
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Page<StoreListResponse>> readListByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "NAME_ASC")StoreSortType sortType,
            @RequestParam(defaultValue = "0") Double curLatitude,
            @RequestParam(defaultValue = "0") Double curLongitude,
            @RequestParam String name){
        return ResponseEntity.ok(storeService.findAllByName(
                PageRequest.of(page,size,sortType.getSort()),
                curLatitude,
                curLongitude,
                name
        ));
    }

    /**
     * 매장 정보를 파트너가 수정한다.
     * 매장 매니저와 로그인유저가 일치해야한다.
     * @param request 수정할 정보를 담아온다.
     * @return 수정된 정보를 반환한다.
     */
    @PutMapping("")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<UpdateStore.Response> update(
            @Valid @RequestBody UpdateStore.Request request){
        return ResponseEntity.ok(storeService.update(request));
    }

    /**
     * 매장 정보를 파트너가 삭제한다
     * 매장 매니저와 로그인 유저가 일치해야한다.
     * @param storeId 삭제할 매장의 id값을 받는다.
     * @return 삭제된 매장의 id값을 반환한다.
     */
    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<DeleteStoreResponse> delete(
            @PathVariable Long storeId){
        return ResponseEntity.ok(storeService.delete(storeId));
    }
}
