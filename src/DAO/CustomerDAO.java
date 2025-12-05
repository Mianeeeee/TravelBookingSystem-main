package DAO;

import Model.Customer;
import java.math.BigDecimal;
import java.sql.*;
import java.time.format.*;
import java.time.LocalDate;

public class CustomerDAO {
    // Chuẩn hóa ngày
    private static String normalizeDate(String date) {
        if (date == null || date.trim().isEmpty())
            return null;

        date = date.trim().replace("/", "-");

        String[] patterns = {
                "d/M/yyyy", "d/MM/yyyy", "dd/M/yyyy", "dd/MM/yyyy",
                "d-M-yyyy", "d-MM-yyyy", "dd-M-yyyy", "dd-MM-yyyy",
                "yyyy/M/d", "yyyy/MM/d", "yyyy/M/dd", "yyyy/MM/dd",
                "yyyy-M-d", "yyyy-MM-d", "yyyy-M-dd", "yyyy-MM-dd"
        };

        for (String pattern : patterns) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate res = LocalDate.parse(date, inputFormatter);
                return res.format(DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception ignore) {
            }
        }

        System.err.println("Không thể parse ngày: " + date);
        return null;
    }

    public static String chuanHoaNgayHienThi(String d) {
        String[] patterns = {
                "d/M/yyyy", "d/MM/yyyy", "dd/M/yyyy", "dd/MM/yyyy",
                "d-M-yyyy", "d-MM-yyyy", "dd-M-yyyy", "dd-MM-yyyy",
                "yyyy/M/d", "yyyy/MM/d", "yyyy/M/dd", "yyyy/MM/dd",
                "yyyy-M-d", "yyyy-MM-d", "yyyy-M-dd", "yyyy-MM-dd"
        };
        LocalDate date = null;
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                date = LocalDate.parse(d, formatter);
                break;
            } catch (DateTimeParseException e) {

            }
        }

        if (date != null) {
            d = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            System.out.println("Định dạng ngày sinh không hợp lệ: " + d);
        }

        return d;
    }

    // Tìm tourId khi biết tourName và dayStart và numberOfDays
    public static String findTourId(String tourName, String dayStart, double numberOfDay)
            throws ClassNotFoundException {
        String tourId = null;
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql = "SELECT tourId FROM TOUR WHERE tourName = ? AND dayStart = STR_TO_DATE(?, '%d/%m/%Y') AND numberOfDays = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tourName);
            stmt.setString(2, dayStart);
            stmt.setDouble(3, numberOfDay);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tourId = rs.getString("tourId");
            } else {
                System.out.println("Không tìm thấy tourId phù hợp.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn findTourId: " + e.getMessage());
        }
        return tourId;
    }

    // Tìm trạng thái tour
    public static String findTourState(String tourId) throws ClassNotFoundException {
        String tourState = null;
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql = "SELECT tourState FROM TOUR WHERE tourId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tourId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tourState = rs.getString("tourState");
            } else {
                System.out.println("Không tìm thấy tourId phù hợp.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn findTourState: " + e.getMessage());
        }
        return tourState;
    }

    public static BigDecimal findPrice(String Id) throws ClassNotFoundException {
        BigDecimal price = null;
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql = "SELECT price FROM TOUR WHERE tourId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                price = rs.getBigDecimal("price");
            } else {
                System.out.println("Không tìm thấy tour phù hợp.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn findPrice: " + e.getMessage());
        }
        return price;
    }

    public static void addCustomer(Customer cst) throws ClassNotFoundException {
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql = "INSERT INTO customer (name, birthday, phoneNumber, email, tourBooking, " +
                    "bookingState, bookingDate, numberOfCustomers, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cst.getName());
            stmt.setString(2, normalizeDate(cst.getBirthday()));
            stmt.setString(3, cst.getPhoneNumber());
            stmt.setString(4, cst.getEmail());
            stmt.setString(5, cst.getTourBooking());
            stmt.setString(6, cst.getBookingState());
            stmt.setString(7, normalizeDate(cst.getBookingDate()));
            stmt.setInt(8, cst.getNumberOfCustomers());
            stmt.setBigDecimal(9, cst.getPrice());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Thêm khách hàng thành công!");
            } else {
                System.out.println("Không thể thêm khách hàng.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    public static String check(Customer cst) throws SQLException, ClassNotFoundException {
        String dataExists = null;
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql1 = "SELECT email FROM customer WHERE email = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, cst.getEmail());

            String sql2 = "SELECT phoneNumber FROM customer WHERE phoneNumber = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, cst.getPhoneNumber());

            ResultSet rs1 = stmt1.executeQuery();
            if (rs1.next()) {
                dataExists = "email";
            }
            rs1.close();

            if (dataExists == null) {
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    dataExists = "phone";
                }
                rs2.close();
            }

            stmt1.close();
            stmt2.close();
        }
        return dataExists;
    }
}