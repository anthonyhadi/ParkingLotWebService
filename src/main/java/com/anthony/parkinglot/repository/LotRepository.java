package com.anthony.parkinglot.repository;

import com.anthony.parkinglot.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot,Long> {

    Lot findFirstByRegNoIsNullAndColourIsNull();

    Lot findFirstByRegNo(String regNo);
}
