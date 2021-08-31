package Functions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateScope {
    public String getDAteForScope(){
        Date date =new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);

    }
}
