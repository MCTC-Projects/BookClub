/**
 * Created by DayDay on 3/1/2015.
 */
public class ISBN_Validator {
    public static boolean isValidISBN(String ISBN) {

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

    public static long getIsbn13Long(String isbn10or13) {
        //Use any ISBN, validated or not,
        // get a validated ISBN-13 as a 64-bit integer
        return Long.parseLong(getValidISBN13(isbn10or13));
    }

    public static String getValidISBN13(String unvalidatedISBN) {
        //Takes unvalidated ISBN-10 or -13
        // validates it through isValidISBN method
        // if ISBN-10, converts it to ISBN-13
        // then returns a valid ISBN-13

        if(isValidISBN(unvalidatedISBN)) {
            if (unvalidatedISBN.length() == 10) {
                return convertIsbn10to13(unvalidatedISBN);
            } else {
                return unvalidatedISBN;
            }
        }
        return null;
    }

    public static String getValidISBN10(String unvalidatedISBN) {
        //Takes unvalidated ISBN-10 or -13
        // validates it through isValidISBN method
        // if ISBN-13, converts it to ISBN-10
        // then returns a valid ISBN-10

        if(isValidISBN(unvalidatedISBN)) {
            if (unvalidatedISBN.length() == 13) {
                return convertIsbn13to10(unvalidatedISBN);
            } else {
                return unvalidatedISBN;
            }
        }
        return null;
    }

    private static boolean ISBN10Validator(String ISBN10) {
        char checkChar = ISBN10.charAt(9);    //10th digit is check digit

        String isbnSubstring = ISBN10.substring(0, 9);

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

            int checksum = CalculateISBN13Checksum(ISBN13.substring(0, 12));

            int check = ISBN13.charAt(12)-'0';    //13th digit is check digit

            //check digit must equal calculated checksum
            if (check == checksum) {
                return true;
            }
        }
        Validator.messageBox("Invalid ISBN", "Invalid");
        return false;
    }

    private static String convertIsbn10to13(String ISBN10) {
        String ISBN10Substring = ISBN10.substring(0, 9);

        String ISBN13Substring = "978" + ISBN10Substring;

        //Calculate checksum using ISBN-13 algorithm
        String check = Integer.toString(CalculateISBN13Checksum(ISBN13Substring));

        return ISBN13Substring + check;
    }

    private static String convertIsbn13to10(String ISBN13) {
        String ISBN10Substring = ISBN13.substring(3,12);

        //Calculate checksum using ISBN-10 algorithm
        int checkSum = CalculateISBN10Checksum(ISBN10Substring);
        String check;

        if (checkSum == 10) {
            check = "X";
        } else {
            check = Integer.toString(checkSum);
        }

        return ISBN10Substring + check;
    }

    private static int CalculateISBN10Checksum(String ISBN10Substring) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int x = 10 - i;

            int digit = ISBN10Substring.charAt(i)-'0';

            sum += (digit * x);
        }

        return (11 - (sum % 11)) % 11;
    }

    private static int CalculateISBN13Checksum(String ISBN13Substring) {
        //Takes first 12 digits of ISBN-13
        // or first 9 digits of ISBN-10 with 978
        // added to the start of the 9-digit string
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = ISBN13Substring.charAt(i)-'0';

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
        return  (10 - (sum % 10)) % 10;
    }
}
