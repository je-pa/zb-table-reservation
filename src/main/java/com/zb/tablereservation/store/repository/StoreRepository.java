package com.zb.tablereservation.store.repository;

import com.zb.tablereservation.store.dao.StoreProjection;
import com.zb.tablereservation.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = "SELECT s.id as storeId" +
                        ", s.name as storeName" +
                        ", s.address" +
                        ", s.review_count as reviewCount" +
                        ", s.star_score_avg as starScoreAvg" +
                        ", s.description" +
                        ", u.name as managerName" +
                        ", (6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude)))) AS distance " +
                    "FROM store s " +
                   "INNER JOIN user u ON s.manager_id = u.id", nativeQuery = true)
    Page<StoreProjection> findByNativeProjection(@Param("latitude") Double latitude, @Param("longitude") Double longitude, Pageable pageable);
    @Query(value = "SELECT s.id as storeId" +
            " , s.name as storeName" +
            " , s.address" +
            " , s.review_count as reviewCount" +
            " , s.star_score_avg as starScoreAvg" +
            " , s.description" +
            " , u.name as managerName" +
            " , (6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude)))) AS distance " +
            " FROM store s " +
            " INNER JOIN user u ON s.manager_id = u.id" +
            " WHERE storeName = LIKE %:name%", nativeQuery = true)
    Page<StoreProjection> findByNativeProjection(@Param("latitude") Double latitude, @Param("longitude") Double longitude, Pageable pageable,String name);

}
