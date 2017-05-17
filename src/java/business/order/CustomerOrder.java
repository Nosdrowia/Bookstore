package business.order;

import java.util.Date;

public class CustomerOrder {

    private long customerOrderId;
    private int amount;
    private Date dateCreated;
    private long confirmationNumber;
    private long customerId;

    public CustomerOrder(long customerOrderId, int amount, Date dateCreated, long confirmationNumber, long customerId) {
        this.customerOrderId = customerOrderId;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.confirmationNumber = confirmationNumber;
        this.customerId = customerId;
    }

    public long getCustomerOrderId() {
        return customerOrderId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public long getConfirmationNumber() {
        return confirmationNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "customerOrderId=" + customerOrderId +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", confirmationNumber=" + confirmationNumber +
                ", customerId=" + customerId +
                '}';
    }
}
