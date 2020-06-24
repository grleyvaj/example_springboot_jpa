package cu.example.application.shared;


import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    public boolean isValidId(long id) {
//        Pattern pattern = Pattern.compile("[^\\s]|[0-9]*"); //no space
//        Pattern pattern = Pattern.compile("[0-9]{3}"); //size =3

//        only positive integers
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher matcher = pattern.matcher(String.valueOf(id));
//        return matcher.matches();
        return id > 0L ? true : false;
    }
}
