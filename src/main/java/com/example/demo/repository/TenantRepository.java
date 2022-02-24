package com.example.demo.repository;

import com.example.demo.model.Tenant;
import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends MongoRepository<Tenant, String> {

    Optional<Tenant> findByUser(User user);
    
    @Query("{'refIds.?0': ?1 }")
    Optional<Tenant> findByRefId(String landlordId, String tenantRefId);

    @Query("{ $and: [{'refIds.?0': { $exists: true } }, {'_id': ObjectId('?1') }] }")
    Optional<Tenant> findTenantRefIdByLandlordIdAndTenantId(String landlordId, String tenantId);

}
