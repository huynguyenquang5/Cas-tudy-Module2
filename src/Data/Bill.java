package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class Bill implements Serializable {
    private User userId;
    private LocalDate date;
    private long numberUsed;
    private boolean status;

    public Bill(User userId, LocalDate date, int numberUsed, boolean status) {
        this.userId = userId;
        this.date = date;
        this.numberUsed = numberUsed;
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getNumberUsed() {
        return numberUsed;
    }

    public void setNumberUsed(int numberUsed) {
        this.numberUsed = numberUsed;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bill.txt{" +
                "userId=" + userId.getUserId()+
                ", date=" + date +
                ", numberUsed=" + numberUsed +
                ", status=" + status +
                ", amountBeforeTax=" + getAmountBeforeTax() +
                ", tax=" + getTax() +
                ", amountAfterTax=" + getAmountAfterTax() +
                '}';
    }
    public long getAmountBeforeTax() {
        if (numberUsed <= 50) {
            return 1678 * numberUsed;
        } else if (numberUsed <= 100) {
            return (1678 * 50) + (1734 * (numberUsed - 50));
        } else if (numberUsed <= 200) {
            return (1678 * 50) + (1734 * 50) + (2014 * (numberUsed - 100));
        } else if (numberUsed <= 300) {
            return (1678 * 50) + (1734 * 50) + (2014 * 100) + (2536 * (numberUsed - 200));
        } else if (numberUsed <= 400) {
            return (1678 * 50) + (1734 * 50) + (2014 * 100) + (2536 * 100) + (2834 * (numberUsed - 300));
        } else {
            return (1678 * 50) + (1734 * 50) + (2014 * 100) + (2536 * 100) + (2834 * 100) + (2927 * (numberUsed - 400));
        }
    }
    public double getTax() {
        return getAmountBeforeTax() * 0.08;
    }

    public double getAmountAfterTax() {
        return getAmountBeforeTax() * 1.08;
    }

}
