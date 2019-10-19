package by.milosh.validator;

import by.milosh.model.Call;
import by.milosh.util.Extractor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class CallValidator extends ValidatorBase<Call> {

    private static final String ALLOWED_SYMBOLS = "^\\+?[\\d()\\-\\s]*$";

    @Override
    public void validateObject(Call call, Errors errors) {
        String phoneNumber = call.getPhoneNumber();
        if (!phoneNumber.matches(ALLOWED_SYMBOLS)) {
            errors.reject("100", "Phone number format is incorrect");
        } else if (!parenthesesValidation(phoneNumber)) {
            errors.reject("101", "Position of parentheses is incorrect");
        } else if (!checkDigits(phoneNumber)) {
            errors.reject("102", "Number of digits is incorrect");
        }
    }

    private boolean parenthesesValidation(String value) {
        boolean result = true;
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < value.length(); i++) {
            char currentChar = value.charAt(i);
            if (currentChar == '(') {
                deque.add(currentChar);
            } else if (currentChar == ')') {
                if (deque.isEmpty() || (deque.pollLast() != '(')) {
                    result = false;
                    break;
                }
            }
        }
        if (!deque.isEmpty()) {
            result = false;
        }
        return result;
    }

    private boolean checkDigits(String phoneNumber) {
        String phoneNumberDigitsOnly = Extractor.onlyDigits(phoneNumber);
        if (phoneNumber.startsWith("+")) {
            return phoneNumberDigitsOnly.length() == 12;
        } else if (phoneNumber.startsWith("00")) {
            return phoneNumberDigitsOnly.length() == 14;
        } else {
            return phoneNumberDigitsOnly.length() == 9;
        }
    }
}
