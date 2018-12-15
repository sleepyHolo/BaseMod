package basemod.helpers;

public class ConvertHelper {
    public static Integer tryParseInt(String txt) {
        try {
            return Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static Integer tryParseInt(String txt, Integer def) {
        try {
            return Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}