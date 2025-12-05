package Model;

import java.time.*;
import java.time.format.*;

public class Human {
    private String Name, Birthday, PhoneNumber, Email, TourBooking, BookingDate, BookingState;

    public Human() {
        this.Name = "";
        this.Birthday = "";
        this.PhoneNumber = "";
        this.Email = "";
    }


    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBirthday() {
        return this.Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTourBooking() {
        return this.TourBooking;
    }

    public void setTourBooking(String TourBooking) {
        this.TourBooking = TourBooking;
    }

    public String getBookingDate() {
        return this.BookingDate;
    }

    public void setBookingDate(String BookingDate) {
        this.BookingDate = BookingDate;
    }

    public String getBookingState() {
        return this.BookingState;
    }

    public void setBookingState(String BookingState) {
        this.BookingState = BookingState;
    }
    
    public void chuanHoaTenVaNgaySinh() {
        //Chuan hoa ten
        StringBuilder tmpName = new StringBuilder();
        String[] WordsName = this.Name.trim().toLowerCase().split("\\s+");
        
        for(String word : WordsName) {
            tmpName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        this.Name = tmpName.toString().trim();
        
        //Chuan hoa ngay sinh
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
                date = LocalDate.parse(this.Birthday, formatter);
                break; 
            } catch (DateTimeParseException e) {
            
            }
        }

        if (date != null) {
            this.Birthday = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            System.out.println("Định dạng ngày sinh không hợp lệ: " + this.Birthday);
        }
    }
}
