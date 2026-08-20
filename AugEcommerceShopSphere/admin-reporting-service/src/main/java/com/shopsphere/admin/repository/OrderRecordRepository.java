package com.shopsphere.admin.repository;

import com.shopsphere.admin.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {
}
