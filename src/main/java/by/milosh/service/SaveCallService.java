package by.milosh.service;

import by.milosh.model.Call;
import by.milosh.util.Extractor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

@Service
public class SaveCallService {

    private static final String PHONE_FORMAT_START_00 = "%d%d%d%d%d %d%d%d %d%d%d %d%d%d";
    private static final String PHONE_FORMAT_START_PLUS = "00%d%d%d %d%d%d %d%d%d %d%d%d";
    private static final String PHONE_FORMAT_SHORT = "00420 %d%d%d %d%d%d %d%d%d";

    public void save(Call call) throws IOException {
        String lastName = call.getLastName();
        String firstName = call.getFirstName();
        String fileName;
        if (firstName != null) {
            fileName = lastName.toUpperCase() + "_" + firstName.toUpperCase();
        } else {
            fileName = lastName.toUpperCase();
        }

        String phoneNumberDigitsOnly = Extractor.onlyDigits(call.getPhoneNumber());
        String time = call.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        try (FileWriter fileWriter = new FileWriter(fileName + ".txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(time + " " + normalizePhoneNumber(phoneNumberDigitsOnly));
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        char[] chars = phoneNumber.toCharArray();
        Integer[] intArray = new Integer[chars.length];
        for (int i = 0; i <= chars.length - 1; i++) {
            intArray[i] = Character.getNumericValue(chars[i]);
        }
        if (phoneNumber.length() == 14) {
            return String.format(PHONE_FORMAT_START_00, intArray);
        } else if (phoneNumber.length() == 12) {
            return String.format(PHONE_FORMAT_START_PLUS, intArray);
        } else {
            return String.format(PHONE_FORMAT_SHORT, intArray);
        }


    }
}
