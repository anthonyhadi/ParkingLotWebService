package com.anthony.parkinglot.repository;

import com.anthony.parkinglot.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotRepository extends JpaRepository<Lot,Long> { }
