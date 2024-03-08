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

    @PostMapping("")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<CreateStore.Response> create(
            @Valid @RequestBody CreateStore.Request request){
        return ResponseEntity.ok(storeService.create(request));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailsResponse> readDetails(
            @PathVariable Long storeId){
        return ResponseEntity.ok(storeService.findDetails(storeId));
    }

    /**
     *
     * 요청예시: /list?page=0&size=10&sort=id,desc
     * @param
     * @return
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

    @PutMapping("")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<UpdateStore.Response> update(
            @Valid @RequestBody UpdateStore.Request request){
        return ResponseEntity.ok(storeService.update(request));
    }

    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<DeleteStoreResponse> delete(
            @PathVariable Long storeId){
        return ResponseEntity.ok(storeService.delete(storeId));
    }
}
