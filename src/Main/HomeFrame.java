package Main;

import View.CustomerForm;
import View.GuideForm;
import View.ManagerForm;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeFrame extends JFrame {

    // Constructor của lớp, nhận vào thông tin CSDL
    public HomeFrame(String dbUrl, String dbUser, String dbPassword) {
        super("Trang chủ"); // Đặt tiêu đề cho cửa sổ thông qua constructor của lớp cha (JFrame)

        // Thiết lập các thuộc tính cơ bản cho Frame
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Tạo các component giao diện (đoạn mã này được chuyển từ hàm main)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] options = { "Khách hàng", "Hướng dẫn viên", "Quản lý" };
        JList<String> list = new JList<>(options);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        JButton btnOpen = new JButton("Mở");
        btnOpen.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected == null) {
                statusLabel.setText("⚠️ Vui lòng chọn mục trước!");
                statusLabel.setForeground(Color.RED);
                return;
            }
            switch (selected) {
                case "Khách hàng":
                    statusLabel.setText("✅ Thành công!");
                    statusLabel.setForeground(Color.GREEN);
                    // Sử dụng các tham số đã được truyền vào constructor
                    new CustomerForm(dbUrl, dbUser, dbPassword);
                    dispose();
                    break;
                case "Hướng dẫn viên":
                    statusLabel.setText("✅ Thành công!");
                    statusLabel.setForeground(Color.GREEN);
                    new GuideForm(dbUrl, dbUser, dbPassword);
                    dispose();
                    break;
                case "Quản lý":
                    statusLabel.setText("✅ Thành công!");
                    statusLabel.setForeground(Color.GREEN);
                    new ManagerForm(dbUrl, dbUser, dbPassword);
                    dispose();
                    break;
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        bottomPanel.add(btnOpen, BorderLayout.CENTER);

        mainPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Thay vì frame.setContentPane(mainPanel), ta dùng this
        this.setContentPane(mainPanel);
        // Hiển thị Frame sau khi đã cấu hình xong
        this.setVisible(true);
    }
}