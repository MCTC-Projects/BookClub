import javax.swing.*;

/**
 * Created by DayDay on 3/1/2015.
 */
public class ISBN_Validator {
    public static boolean ValidateISBN (String ISBN) {

        if (ISBN.length() == 10) {
            return ISBN10Validator(ISBN);
        } else if (ISBN.length() == 13) {
            return ISBN13Validator(ISBN);
        } else {
            String message =
                    "ISBN must be 10 (ISBN-10) or 13 (ISBN-13) digits long.";
            Validator.messageBox(message, "Entry Error");
            return false;
        }
    }

    private static boolean ISBN10Validator(String ISBN10) {
        char checkChar = ISBN10.charAt(9);    //10th digit is check digit

        String isbnSubstring = ISBN10.substring(0, ISBN10.length() - 1);

        if (!Validator.isInteger(ISBN10, "ISBN", false)) {
            if (!Validator.isInteger(isbnSubstring, "ISBN", false) ||   //first 9 characters must be numeric
                    (!Character.isDigit(checkChar) &&                   //check digit must be numeric
                            checkChar != 'x' && checkChar != 'X')) {    // or x/X
                Validator.messageBox("ISBN-10 can only contain numeric digits (last character can be an 'X').", "Entry Error");
                return false;
            }
        }

        int check;

        if (checkChar == 'x' || checkChar == 'X') {
            //check digit could equal 10 which is represented by an X
            check = 10;
        } else {
            check = checkChar-'0';
        }

        int sum = 0;
        int x = 10;

        for (char c : isbnSubstring.toCharArray()) {
            //multiply each digit (except check digit)
            //by a number in a sequence from 10 to 2
            int digit = c-'0';
            sum += digit * x;
            x--;
        }

        //equation for calculating check digit
        sum = (11 - (sum % 11)) % 11;

        //check digit must equal calculated checksum
        if (check == sum) {
            Validator.messageBox("Valid ISBN", "Valid");
            return true;
        } else {
            Validator.messageBox("Invalid ISBN", "Invalid");
            return false;
        }
    }

    private static boolean ISBN13Validator(String ISBN13) {
        if (!Validator.isInteger(ISBN13, "ISBN", false)) {
            Validator.messageBox("ISBN-13 can only contain numeric digits.", "Entry Error");
            return false;
        }

        if (ISBN13.startsWith("978")) {

            int sum = 0;
            int check = ISBN13.charAt(12)-'0';    //13th digit is check digit

            for (int i = 0; i < 12; i++) {
                int digit = ISBN13.charAt(i)-'0';

                //sum up digits, multiplying even digits by 3
                if ((i + 1) % 2 == 1) {
                    //Odd digits
                    sum += digit;
                } else {
                    //Even digits
                    sum += (3 * digit);
                }
            }

            //equation for calculating check digit
            sum = (10 - (sum % 10)) % 10;

            //check digit must equal calculated checksum
            if (check == sum) {
                return true;
            }
        }
        Validator.messageBox("Invalid ISBN", "Invalid");
        return false;
    }
}
