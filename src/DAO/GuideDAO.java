package DAO;

import Model.Guide;
import java.util.*;
import java.sql.*;

public class GuideDAO {

    public Guide setGuide(String Name, String Birthday, String PhoneNumber, String Email, float exp, String langs) {
        Guide g = new Guide();
        g.setName(Name);
        g.setBirthday(Birthday);
        g.setPhoneNumber(PhoneNumber);
        g.setEmail(Email);
        g.setGuideExperience(exp);
        ArrayList<String> listLangs = new ArrayList<>(Arrays.asList(langs.split(", ")));
        g.setForeignLanguage(listLangs);
        g.chuanHoaTenVaNgaySinh();
        return g;
    }

    // Tìm tourId khi biết tourName và dayStart và numberOfDay
    public static String findTourId(String tourName, String dayStart, double numberOfDay)
            throws ClassNotFoundException {
        String tourId = null;
        try {
            Connection con = GetConnectionDAO.getConnection();
            String sql = "SELECT tourId FROM tour WHERE tourName = ? AND dayStart = STR_TO_DATE(?, '%d/%m/%Y') AND numberOfDays = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tourName);
            stmt.setString(2, dayStart);
            stmt.setDouble(3, numberOfDay);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tourId = rs.getString("tourId");
            } else {
                System.out.println("Không tìm thấy tour phù hợp.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn findTourId: " + e.getMessage());
        }
        return tourId;
    }

    public static String findTourGuideState(String tourId) throws ClassNotFoundException {
        String tourGuideState = null;
        try {
            Connection con = GetConnectionDAO.getConnection();
            String sql = "SELECT tourGuideState FROM tour WHERE tourId = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tourId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tourGuideState = rs.getString("tourGuideState");
            } else {
                System.out.println("Không tìm thấy tour phù hợp.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn findTourId: " + e.getMessage());
        }
        return tourGuideState;
    }

    public boolean guideCheck(Guide g, String tourId) {
        try {
            Connection con = GetConnectionDAO.getConnection();
            String sql = "SELECT languageGuideNeed FROM TOUR WHERE tourId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tourId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String langNeed = rs.getString("languageGuideNeed");
                List<String> listLangNeed = Arrays.asList(langNeed.split(", "));

                // Kiểm tra xem hướng dẫn viên có biết ít nhất một ngôn ngữ cần thiết không
                for (String lang : g.getForeignLanguage()) {
                    if (listLangNeed.contains(lang)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addGuide(Guide guide) throws ClassNotFoundException {
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql = "INSERT INTO guide (name, birthday, phoneNumber, email, guideExperience, TourBooking, BookingState, BookingDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, guide.getName());
            stmt.setString(2, guide.getBirthday());
            stmt.setString(3, guide.getPhoneNumber());
            stmt.setString(4, guide.getEmail());
            stmt.setDouble(5, guide.getGuideExperience());
            stmt.setString(6, guide.getTourBooking());
            stmt.setString(7, guide.getBookingState());
            stmt.setString(8, guide.getBookingDate());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Thêm hướng dẫn viên thành công!");
            } else {
                System.out.println("Không thể thêm hướng dẫn viên.");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    // Lấy guideId
    public static String findGuideId() throws ClassNotFoundException {
        String GuideId = null;
        try {
            Connection con = GetConnectionDAO.getConnection();
            String sql = "SELECT Id FROM guide ORDER BY Id DESC LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                GuideId = rs.getString("Id");
            } else {
                System.out.println("Không tìm thấy tour phù hợp.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi truy vấn findTourId: " + e.getMessage());
        }
        return GuideId;
    }

    // Xử lý bảng guideLanguage
    public static void addGuideLanguage(Guide guide, String GuideId) throws ClassNotFoundException {
        ArrayList<String> arr = guide.getForeignLanguage();
        for (int i = 0; i < arr.size(); i++) {
            try (Connection conn = GetConnectionDAO.getConnection()) {
                String sql = "INSERT INTO guidelanguage (GuideId, foreignLanguage) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, GuideId);
                stmt.setString(2, arr.get(i));

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Thêm hướng dẫn viên thành công!");
                } else {
                    System.out.println("Không thể thêm hướng dẫn viên.");
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error to add guide language: " + e.getMessage());
            }
        }
    }

    public static String check(Guide guide) throws SQLException, ClassNotFoundException {
        String dataExists = null;
        try (Connection conn = GetConnectionDAO.getConnection()) {
            String sql1 = "SELECT email FROM guide WHERE email = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, guide.getEmail());

            String sql2 = "SELECT phoneNumber FROM guide WHERE phoneNumber = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, guide.getPhoneNumber());

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