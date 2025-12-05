USE TRAVELBOOKINGSYSTEMAPP;

CREATE TABLE Customer_Sequence (
    Last_ID INT NOT NULL
);
INSERT INTO Customer_Sequence (Last_ID) VALUES (0);

-- Bước 3: Tạo Trigger
DELIMITER $$
CREATE TRIGGER tg_customers_insert
BEFORE INSERT ON Customer
FOR EACH ROW
BEGIN
    DECLARE next_id INT;
    -- Lấy và cập nhật số đếm trong một giao dịch an toàn
    UPDATE Customer_Sequence SET Last_ID = Last_ID + 1 WHERE Last_ID >= 0;
    SET next_id = (SELECT Last_ID FROM Customer_Sequence);
    -- Gán ID mới cho bản ghi đang được chèn
    SET NEW.Id = CONCAT('KH', LPAD(next_id, 3, '0'));
END$$
DELIMITER ;