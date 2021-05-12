package com.balakin.qa.repositories;

import com.balakin.qa.domain.CheckList;

import com.balakin.qa.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList,Long> {

    CheckList findByProject(Project project);

}
