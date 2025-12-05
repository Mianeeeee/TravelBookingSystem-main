// File: Main.java
package Main;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Thông tin CSDL
        final String DB_URL = "jdbc:mysql://localhost:3306/TravelBookingSystemApp";
        final String DB_USER = "root";
        final String DB_PASSWORD = "miane7755nW"; // đổi nếu bạn có mật khẩu MySQL

        // Chạy GUI trên Event Dispatch Thread để đảm bảo an toàn luồng
        SwingUtilities.invokeLater(() -> {
            // Chỉ cần tạo một đối tượng của HomeFrame là cửa sổ sẽ hiện ra
            new HomeFrame(DB_URL, DB_USER, DB_PASSWORD);
        });
    }

    // Tiện ích dùng chung (giữ nguyên không đổi)
    public static boolean isDouble(String str) {
        if (str == null || str.isEmpty())
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean truePhoneNumber(String str) {
        if (str.length() != 10 && str.length() != 9)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty())
            return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}