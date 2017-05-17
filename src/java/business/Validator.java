
package business;

import com.mysql.jdbc.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

public class Validator {

    public boolean validateForm(String name, String address, String phone, String email,
                                String ccNumber, Date ccExpDate, HttpServletRequest request) {
        boolean errorFlag = false;
        Calendar ccDate = Calendar.getInstance();


        if (name == null || name.equals("") || name.length() > 45) {
            errorFlag = true;
            request.setAttribute("nameError", true);
        }

        if(StringUtils.isNullOrEmpty(address)){
            errorFlag = true;
            request.setAttribute("addressError",true);
        }

        if(StringUtils.isNullOrEmpty(phone) || phone.replaceAll("[^\\d]","").length() != 10){
            errorFlag = true;
            request.setAttribute("phoneError",true);
        }

        if(StringUtils.isNullOrEmpty(email) || email.contains(" ") || !email.contains("@") || email.substring(email.length() -1) == "."){
            errorFlag = true;
            request.setAttribute("emailError",true);
        }

        if(StringUtils.isNullOrEmpty(ccNumber) || ccNumber.replaceAll("[^\\d]","").length() > 16 || ccNumber.replaceAll("[^\\d]","").length() < 10){
            errorFlag = true;
            request.setAttribute("ccNumberError",true);
        }

        if(ccExpDate == null){
            errorFlag = true;
            request.setAttribute("ccDateError",true);
        }
        else if(ccExpDate != null){
            ccDate.setTime(ccExpDate);
            if(Calendar.getInstance().get(Calendar.YEAR) > ccDate.get(Calendar.YEAR)){
                errorFlag = true;
                request.setAttribute("ccDateError", true);
            }
            else if(Calendar.getInstance().get(Calendar.YEAR) == ccDate.get(Calendar.YEAR) &&
                    Calendar.getInstance().get(Calendar.MONTH) > ccDate.get(Calendar.MONTH)){
                errorFlag = true;
                request.setAttribute("ccDateError",true);
            }
        }

        return errorFlag;
    }
}
