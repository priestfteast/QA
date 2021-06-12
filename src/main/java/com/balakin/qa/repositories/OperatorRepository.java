package com.balakin.qa.repositories;

import com.balakin.qa.domain.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OperatorRepository extends JpaRepository<Operator,Long> {
    Operator findByFullName(String fullName);


}
