package View;

import Main.Main;
import Model.Guide;
import Model.Tour;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DAO.GuideDAO;
import DAO.TourDAO;

import java.awt.*;
import java.sql.SQLException;

public class GuideForm extends JFrame {
    public GuideForm(String DB_URL, String DB_USER, String DB_PASSWORD) {
        setTitle("Xác minh hướng dẫn viên");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblXacMinh = new JLabel("Mã xác minh:");
        JPasswordField txtXacMinh = new JPasswordField();

        JCheckBox showPass = new JCheckBox("Hiện mật khẩu");
        showPass.addActionListener(e -> {
            if (showPass.isSelected()) {
                txtXacMinh.setEchoChar((char) 0); // hiện mật khẩu
            } else {
                txtXacMinh.setEchoChar('*'); // ẩn mật khẩu
            }
        });
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton btnSubmit = new JButton("Xác nhận");
        btnSubmit.addActionListener(e -> {
            String code = new String(txtXacMinh.getPassword());
            if (code.equals("guide123")) {
                new GuideSignIn(DB_URL, DB_USER, DB_PASSWORD);
                dispose();
                dispose();
            } else {
                statusLabel.setText("❌️ Mã xác minh không đúng!");
                statusLabel.setForeground(Color.RED);
            }
        });

        panel.add(lblXacMinh);
        panel.add(txtXacMinh);
        panel.add(showPass);
        panel.add(statusLabel);
        panel.add(new JLabel());
        panel.add(btnSubmit);

        setContentPane(panel);
        setVisible(true);
    }
}

class GuideSignIn extends JFrame {
    public GuideSignIn(String DB_URL, String DB_USER, String DB_PASSWORD) {
        setTitle("Thông tin hướng dẫn viên");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblName = new JLabel("Họ và tên:");
        JTextField txtName = new JTextField();

        JLabel lblBirthday = new JLabel("Ngày sinh:");
        JTextField txtBirthday = new JTextField();

        JLabel lblPhone = new JLabel("Số điện thoại:");
        JTextField txtPhone = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();

        JLabel lblExperience = new JLabel("Kinh nghiệm (năm):");
        JTextField txtExperience = new JTextField();

        JLabel lblLanguages = new JLabel("<html>Ngoại ngữ <br> (Giữ Ctrl để chọn nhiểu ngôn ngữ): </html>");
        JList<String> listLangs = new JList<>(new String[] { "Tiếng Anh", "Tiếng Trung", "Tiếng Nhật", "Tiếng Hàn" });
        listLangs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listLangs);
        scrollPane.setPreferredSize(new Dimension(120, 80));

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        // Tạo Object Guide
        // Các thông số về tour để chuỗi rỗng
        JButton btnSubmit = new JButton("Tiếp tục");
        btnSubmit.addActionListener(e -> {
            Guide guide = new Guide();
            String name = txtName.getText();
            String birthday = txtBirthday.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String exp = txtExperience.getText();
            StringBuilder langs = new StringBuilder();
            for (String lang : listLangs.getSelectedValuesList()) {
                if (langs.length() > 0)
                    langs.append(", ");
                langs.append(lang);
            }

            if (name.isEmpty() || birthday.isEmpty() || phone.isEmpty()
                    || email.isEmpty() || exp.isEmpty() || langs.isEmpty()) {
                statusLabel.setText("<html>⚠️ Vui lòng nhập đầy đủ <br>thông tin!</html>");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.truePhoneNumber(phone)) {
                statusLabel.setText("❌ Số điện thoại không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!email.endsWith("@gmail.com")) {
                statusLabel.setText("❌ Email không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.isDouble(exp) || Double.parseDouble(exp) < 0) {
                statusLabel.setText("❌ Kinh nghiệm không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            }
            GuideDAO guideDAO = new GuideDAO();
            guide = guideDAO.setGuide(name, birthday, phone, email, Float.parseFloat(exp), langs.toString());

            try {
                if (GuideDAO.check(guide) != null) {
                    String field = null;
                    try {
                        field = GuideDAO.check(guide);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if (field.equals("email")) {
                        statusLabel.setText("❌ Email đã tồn tại!");
                    } else if (field.equals("phone")) {
                        statusLabel.setText("❌ Số điện thoại đã tồn tại!");
                    }
                    statusLabel.setForeground(Color.RED);
                    return;
                }
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }
            try {
                new BookingTour(guide, DB_URL, DB_USER, DB_PASSWORD);
                dispose();
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        });

        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblBirthday);
        panel.add(txtBirthday);
        panel.add(lblPhone);
        panel.add(txtPhone);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblExperience);
        panel.add(txtExperience);

        panel.add(lblLanguages);
        panel.add(scrollPane);

        panel.add(statusLabel);
        panel.add(btnSubmit);

        setContentPane(panel);
        setVisible(true);
    }
}

class BookingTour extends JFrame {

    public BookingTour(Guide guide,
            String DB_URL, String DB_USER, String DB_PASSWORD) throws ClassNotFoundException {
        setTitle("Chọn tour dẫn");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        TourDAO tourDao = new TourDAO();

        JLabel lblTour = new JLabel("Tên tour:");
        String[] tours = tourDao.collectTourGuideInfo("NOT FULL");
        JComboBox<String> cbTour = new JComboBox<>(tours);

        JLabel lblDate = new JLabel("Ngày khởi hành:");
        JComboBox<String> cbDate = new JComboBox<>(); // Khởi tạo rỗng

        JLabel lblNumberOfDays = new JLabel("Số ngày: ");
        JComboBox<Double> cbNumberOfDays = new JComboBox<>(); // Khởi tạo rỗng

        JLabel statusLabel = new JLabel("Trạng thái Tour: ");
        JLabel lblTourState = new JLabel("NOT FULL");
        lblTourState.setForeground(new Color(0, 102, 0));

        if (cbTour.getItemCount() > 0) {
            String initialTour = (String) cbTour.getSelectedItem();
            String[] initialDates = tourDao.collectTourStartDate(initialTour);
            cbDate.setModel(new DefaultComboBoxModel<>(initialDates));

            if (cbDate.getItemCount() > 0) {
                String initialDate = (String) cbDate.getSelectedItem();
                Double[] initialDays = tourDao.collectTourDays(initialTour, initialDate);
                cbNumberOfDays.setModel(new DefaultComboBoxModel<>(initialDays));
            }
        }

        // === THÊM ACTIONLISTENER CHO cbTour ===
        cbTour.addActionListener(e -> {
            String selectedTour = (String) cbTour.getSelectedItem();
            if (selectedTour != null) {
                String[] newDates;
                try {
                    newDates = tourDao.collectTourStartDate(selectedTour);
                    cbDate.setModel(new DefaultComboBoxModel<>(newDates));
                    if (cbDate.getItemCount() > 0) {
                        String firstDate = (String) cbDate.getSelectedItem();
                        Double[] newNumDays = tourDao.collectTourDays(selectedTour, firstDate);
                        cbNumberOfDays.setModel(new DefaultComboBoxModel<>(newNumDays));
                    }
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
            reupTourGuideStatus(lblTourState);
        });

        // === THÊM ACTIONLISTENER CHO cbDate ===
        cbDate.addActionListener(e -> {
            String selectedTour = (String) cbTour.getSelectedItem();
            String selectedDate = (String) cbDate.getSelectedItem();
            if (selectedTour != null && selectedDate != null) {
                Double[] newNumDays;
                try {
                    newNumDays = tourDao.collectTourDays(selectedTour, selectedDate);
                    cbNumberOfDays.setModel(new DefaultComboBoxModel<>(newNumDays));
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            reupTourGuideStatus(lblTourState);
        });

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String tourName = (String) cbTour.getSelectedItem();
            String date = (String) cbDate.getSelectedItem();
            Double numDays = (Double) cbNumberOfDays.getSelectedItem();
            String tourId = null;

            try {
                tourId = GuideDAO.findTourId(tourName, date, numDays);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            if (tourId == null) {
                JOptionPane.showMessageDialog(this,
                        "❌️ Không tìm thấy tour phù hợp!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            updateTourGuideStatus(tourId, lblTourState, guide, tourName, date);

        });
        panel.add(lblTour);
        panel.add(cbTour);
        panel.add(lblDate);
        panel.add(cbDate);
        panel.add(lblNumberOfDays);
        panel.add(cbNumberOfDays);
        panel.add(statusLabel);
        panel.add(lblTourState);
        panel.add(new JLabel());
        panel.add(btnConfirm);
        setContentPane(panel);
        setVisible(true);
    }

    private void updateTourGuideStatus(String tourId, JLabel lblTourState, Guide guide, String tourName, String date) {
        Tour tour = new Tour();
        try {
            tour = TourDAO.getTourById(tourId);
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        tour.setTourGuideState();
        String tourGuideState = tour.getTourGuideState();

        lblTourState.setText(tourGuideState);
        if ("FULL".equalsIgnoreCase(tourGuideState)) {
            lblTourState.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "⚠️ Tour này đã đủ Hướng dẫn viên! Vui lòng chọn tour khác.",
                    "Tour Đã Đầy",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            lblTourState.setForeground(new Color(0, 102, 0)); // Màu xanh lá cây đậm
            if (!new GuideDAO().guideCheck(guide, tourId)) {
                JOptionPane.showMessageDialog(this,
                        "❌️ Bạn không đủ điều kiện ngôn ngữ để dẫn tour này!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(this,
                        "✅️ Bạn đủ điều kiện ngôn ngữ để dẫn tour này!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                // set các thông số cho tour khi đủ điều kiện
                tour.setCurrentGuides(1);
                tour.setTourGuideState();
                try {
                    TourDAO.updateTour(tour, tourId);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
                // sau khi đủ điều kiện thì set các thông tin tour cho guide
                guide.setTourBooking(tourId);
                guide.setBookingState("Confirmed");
                guide.setBookingDate(java.time.LocalDate.now().toString());
                // Thêm chức năng add vào db sau
                try {
                    GuideDAO.addGuide(guide);
                    String GuideId = GuideDAO.findGuideId();
                    GuideDAO.addGuideLanguage(guide, GuideId);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(this,
                        "Họ và tên: " + guide.getName() +
                                "\nNgày sinh: " + guide.getBirthday() +
                                "\nSố điện thoại: " + guide.getPhoneNumber() +
                                "\nEmail: " + guide.getEmail() +
                                "\nKinh nghiệm: " + guide.getGuideExperience() + " năm" +
                                "\nNgoại ngữ: " + guide.getForeignLanguageAsString() +
                                "\nTên tour: " + tourName +
                                "\nNgày khởi hành: " + date,
                        "Xác nhận dẫn tour",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void reupTourGuideStatus(JLabel lblTourState) {
        lblTourState.setText("NOT FULL");
        lblTourState.setForeground(new Color(0, 102, 0));
    }
}