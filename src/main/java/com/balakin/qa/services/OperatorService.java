package com.balakin.qa.services;



import com.balakin.qa.commands.OperatorCommand;
import com.balakin.qa.domain.Operator;

import java.util.List;

public interface OperatorService {
    List<Operator> getAllOperators(String request);
    Operator findById(Long l);
    Operator findByName(String name);
    OperatorCommand findCommandById(Long l);
    OperatorCommand saveOperatorCommand(OperatorCommand operatorCommand) throws Exception;
    void deleteById(Long idToDelete);
}
