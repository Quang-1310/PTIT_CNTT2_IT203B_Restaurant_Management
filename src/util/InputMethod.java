package util;

import java.util.Scanner;

public class InputMethod {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString() {
        return scanner.nextLine().trim();
    }

    public static int getInteger() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException e) {
                System.err.print("Lỗi định dạng số nguyên, vui lòng nhập lại: ");
            }
        }
    }
}
