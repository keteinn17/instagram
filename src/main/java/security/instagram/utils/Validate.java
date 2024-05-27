package security.instagram.utils;

import org.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import security.instagram.config.auth.BearerContextHolder;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
public class Validate {
    public static JSONObject handlerError(BindingResult bindingResult){
        JSONObject jsonObject = new JSONObject();
        List<ObjectError> errorList = bindingResult.getAllErrors();
        for(ObjectError objectError:errorList){
            String code =objectError.getCode();
            String message = objectError.getDefaultMessage();
            jsonObject.append(code,message);
        }
        return jsonObject;
    }

    public static String removeDiacritics(String text) { // Điện thoại to dien-thoai
        text = text.replace("Đ", "D").replace("đ", "d").trim();

        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutDiacritics = pattern.matcher(normalizedText).replaceAll("");
        withoutDiacritics = withoutDiacritics.toLowerCase().replaceAll("\\s+", "-");
        return withoutDiacritics;
    }

    public static Long getUserId() throws Exception {
        Long userId;
        try{
            userId = Long.valueOf(BearerContextHolder.getContext().getUserId());
        }catch (NullPointerException e){
            throw new Exception(Constants.SOMETHING_WENT_WRONG);
        }
        return userId;
    }
}