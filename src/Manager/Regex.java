package Manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static Pattern patternUserId;
    private static Pattern patternPassword;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
    private static final String USERID_PATTERN =
            "^PD\\d{4}T\\d{6}$";
    public Regex() {
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
        patternUserId = Pattern.compile(USERID_PATTERN);

    }

    public boolean validatePassword(String regex) {
        matcher = patternPassword.matcher(regex);
        return matcher.matches();
    }

    public boolean validateUserId(String regex) {
        matcher = patternUserId.matcher(regex);
        return matcher.matches();
    }
}
