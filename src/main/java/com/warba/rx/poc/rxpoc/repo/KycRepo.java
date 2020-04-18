package com.warba.rx.poc.rxpoc.repo;

import com.warba.rx.poc.rxpoc.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycRepo extends JpaRepository<Kyc, Long> {
}
