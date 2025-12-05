package Model;

import java.math.BigDecimal;

public class Customer extends Human {
    private int numberOfCustomers;
    private BigDecimal price;

    public Customer() {
        super();
        this.numberOfCustomers = 0;
        this.price = BigDecimal.ZERO;
    }

    // Get
    public int getNumberOfCustomers() {
        return this.numberOfCustomers;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    // Set
    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
