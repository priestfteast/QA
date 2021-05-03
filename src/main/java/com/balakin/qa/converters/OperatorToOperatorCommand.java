package com.balakin.qa.converters;

import com.balakin.qa.commands.OperatorCommand;
import com.balakin.qa.domain.Operator;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class OperatorToOperatorCommand implements Converter<Operator, OperatorCommand> {

    @Nullable
    @Synchronized
    @Override
    public OperatorCommand convert(Operator operator) {
        if(operator==null) {
            return null;
        }

        OperatorCommand operatorCommand = new OperatorCommand();
        operatorCommand.setId(operator.getId());
        operatorCommand.setFullName(operator.getFullName());
        operatorCommand.setEmployementDate(operator.getEmployementDate());
        operatorCommand.setProject(operator.getProject());
        operatorCommand.setFired(operator.isFired());

        if(operator.isFired()) {
            operatorCommand.setFireCause(operator.getFireCause());
            operatorCommand.setRetirementDate(operator.getRetirementDate());
        }

        return operatorCommand;
    }
}
