package Data;

public class Admin {
    private String staffId;
    private String staffPassword;

    public Admin(String staffId, String staffPassword) {
        this.staffId = staffId;
        this.staffPassword = staffPassword;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }
}
