USE TRAVELBOOKINGSYSTEMAPP;

CREATE TABLE Tour_Sequence (
    Last_ID INT NOT NULL
);
INSERT INTO Tour_Sequence (Last_ID) VALUES (0);

-- Bước 3: Tạo Trigger
DELIMITER $$
CREATE TRIGGER tg_tour_insert
BEFORE INSERT ON TOUR
FOR EACH ROW
BEGIN
    DECLARE next_id INT;
    -- Lấy và cập nhật số đếm trong một giao dịch an toàn
    UPDATE Tour_Sequence SET Last_ID = Last_ID + 1 WHERE Last_ID >= 0;
    SET next_id = (SELECT Last_ID FROM Tour_Sequence);
    -- Gán ID mới cho bản ghi đang được chèn
    SET NEW.tourId = CONCAT('TOUR', LPAD(next_id, 3, '0'));
END$$
DELIMITER ;