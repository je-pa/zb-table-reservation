package com.zb.tablereservation.store.service;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.security.util.MySecurityUtil;
import com.zb.tablereservation.store.dto.*;
import com.zb.tablereservation.store.entity.Store;
import com.zb.tablereservation.store.repository.StoreRepository;
import com.zb.tablereservation.user.entity.User;
import com.zb.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    public CreateStore.Response create(CreateStore.Request request) {
        Store store = request.toEntity();
        store.setManager(getCurrentUser());

        return CreateStore.Response.fromEntity(storeRepository.save(store));
    }
    public StoreDetailsResponse findDetails(Long storeId) {
        return StoreDetailsResponse.fromEntity(getStoreById(storeId));
    }

    public Page<StoreListResponse> findAll(PageRequest pageRequest, Double curLatitude, Double curLongitude) {
        return storeRepository.findByNativeProjection(curLatitude,curLongitude,pageRequest)
                .map(StoreListResponse::fromStoreProjection);
    }
    public Page<StoreListResponse> findAllByName(PageRequest pageRequest, Double curLatitude, Double curLongitude, String name) {
        return storeRepository.findByNativeProjection(curLatitude,curLongitude,pageRequest, name)
                .map(StoreListResponse::fromStoreProjection);
    }

    @Transactional
    public UpdateStore.Response update(UpdateStore.Request request) {
        Store store = getStoreById(request.getStoreId());

        verifyManager(store.getManager().getId());

        store.updateStore(request);
        return UpdateStore.Response.fromEntity(store);
    }

    public DeleteStoreResponse delete(Long storeId) {
        Store store = getStoreById(storeId);

        verifyManager(store.getManager().getId());

        storeRepository.delete(store);
        return DeleteStoreResponse.builder()
                .storeId(storeId)
                .build();
    }

    private void verifyManager(Long managerId) {
        if(getCurrentUser().getId().equals(managerId)){
            throw new RuntimeException(
                    ExceptionCode.LOGIN_USER_MANAGER_MISMATCH.getMessage());
        }
    }

    private Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        ExceptionCode.STORE_NOT_FOUND.getMessage()));
    }

    private User getCurrentUser() {
        return userRepository.findByUserId(MySecurityUtil.getCustomUserDetails().getUsername())
                .orElseThrow(()-> new RuntimeException(
                        ExceptionCode.USER_NOT_FOUND.getMessage()));
    }
}
