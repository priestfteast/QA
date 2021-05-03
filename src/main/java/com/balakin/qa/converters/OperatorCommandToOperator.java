package com.balakin.qa.converters;

import com.balakin.qa.commands.OperatorCommand;
import com.balakin.qa.domain.Operator;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class OperatorCommandToOperator implements Converter<OperatorCommand, Operator> {

    @Nullable
    @Synchronized
    @Override
    public Operator convert(OperatorCommand operatorCommand) {
        if(operatorCommand==null) {
            return null;
        }

        Operator operator = new Operator();
        operator.setId(operatorCommand.getId());
        operator.setFullName(operatorCommand.getFullName().trim());
        operator.setEmployementDate(operatorCommand.getEmployementDate());
        operator.setFired(operatorCommand.isFired());
        operator.setProject(operatorCommand.getProject());
        if(operatorCommand.isFired()) {
            operator.setRetirementDate(operatorCommand.getRetirementDate());
            operator.setFireCause(operatorCommand.getFireCause());
        }

        return operator;
    }
}
