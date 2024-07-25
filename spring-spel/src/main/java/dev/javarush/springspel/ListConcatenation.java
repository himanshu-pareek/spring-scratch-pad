package dev.javarush.springspel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Operation;
import org.springframework.expression.OperatorOverloader;
import org.springframework.lang.Nullable;

public class ListConcatenation implements OperatorOverloader {

    @Override
    public boolean overridesOperation(
        @Nullable Operation operation,
        @Nullable Object leftOperand,
        @Nullable Object rightOperand
    ) throws EvaluationException {
        return operation == Operation.ADD
            && leftOperand instanceof List
            && rightOperand instanceof List;
    }

    @Override
    public Object operate(
        @Nullable Operation operation,
        @Nullable Object leftOperand,
        @Nullable Object rightOperand
    ) throws EvaluationException {
        if (operation == Operation.ADD
            && leftOperand instanceof List list1
            && rightOperand instanceof List list2
        ) {
			List result = new ArrayList(list1);
			result.addAll(list2);
			return result;
		}
		throw new UnsupportedOperationException(
			"No overload for operation %s and operands [%s] and [%s]"
				.formatted(operation, leftOperand, rightOperand));
    }
    
}
