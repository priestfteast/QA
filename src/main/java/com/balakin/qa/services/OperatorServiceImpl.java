package com.balakin.qa.services;


import com.balakin.qa.commands.OperatorCommand;
import com.balakin.qa.converters.OperatorCommandToOperator;
import com.balakin.qa.converters.OperatorToOperatorCommand;
import com.balakin.qa.domain.Operator;
import com.balakin.qa.exceptions.NotFoundException;
import com.balakin.qa.repositories.OperatorRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperatorServiceImpl implements OperatorService {

    private final OperatorToOperatorCommand operatorToOperatorCommand;
    private final OperatorCommandToOperator operatorCommandToOperator;


    private final OperatorRepository operatorRepository;

    public OperatorServiceImpl(OperatorToOperatorCommand operatorToOperatorCommand, OperatorCommandToOperator operatorCommandToOperator, OperatorRepository operatorRepository) {
        this.operatorToOperatorCommand = operatorToOperatorCommand;
        this.operatorCommandToOperator = operatorCommandToOperator;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public List<Operator> getAllOperators(String request) {

        switch (request){
            case "[all]": return operatorRepository.findAll();
            case "[actual]": return operatorRepository.findAll().stream().filter(operator -> !operator.isFired()).collect(Collectors.toList());
            case "[fired]" : return operatorRepository.findAll().stream().filter(operator -> operator.isFired()).collect(Collectors.toList());
            default: return operatorRepository.findAll();
        }
    }

    @Override
    public Operator findById(Long l)  {
        Optional<Operator> operatorOptional = operatorRepository.findById(l);
        if(operatorOptional.isEmpty())
            throw new NotFoundException("There is no operator with ID: "+l.toString());

        return operatorOptional.get();
    }

    @Override
    public OperatorCommand findCommandById(Long l) {
        return operatorToOperatorCommand.convert(findById(l));
    }

    @Override
    public Operator findByName(String name) {
        return operatorRepository.findByFullName(name);
    }

    @Override
    public OperatorCommand saveOperatorCommand(OperatorCommand operatorCommand) throws Exception {
        Operator detachedOperator = operatorCommandToOperator.convert(operatorCommand);
        if(operatorRepository.findByFullName(detachedOperator.getFullName())!=null) {
            if (operatorRepository.findByFullName(detachedOperator.getFullName()).getEmployementDate().equals(detachedOperator.getEmployementDate()) &&
                    operatorRepository.findByFullName(detachedOperator.getFullName()).getProject().equals(detachedOperator.getProject()))
                throw new Exception("Идентичный оператор уже сохранен в базе, " + operatorCommand.getFullName() + "." +
                        " Измените Имя, смену или дату трудоустройства.");
        }
        Operator savedOperator = operatorRepository.save(detachedOperator);
        return operatorToOperatorCommand.convert(savedOperator);
    }

    @Override
    public void deleteById(Long idToDelete) {
        operatorRepository.deleteById(idToDelete);
    }


}
