package basemod.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.BaseMod;

public class ReflectionHacks {
	// use same logger as BaseMod
    public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());

	private ReflectionHacks() {}

    //
    // Reflection hacks
    //
    
    // getPrivateStatic - read private static variables
    @SuppressWarnings("rawtypes")
	public static Object getPrivateStatic(Class objClass, String fieldName) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            return targetField.get(null);
        } catch (Exception e) {
            logger.error("Exception occured when getting private static field " + fieldName + " of " + objClass.getName(), e);
        }
        
        return null;
    }
    
    // setPrivateStatic - modify private static variables
    @SuppressWarnings("rawtypes")
	public static void setPrivateStatic(Class objClass, String fieldName, Object newValue) {
    	setPrivateStaticFinal(objClass, fieldName, newValue);
    }
    
    // setPrivateStaticFinal - modify (private) static (final) variables
    @SuppressWarnings("rawtypes")
	public static void setPrivateStaticFinal(Class objClass, String fieldName, Object newValue) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(targetField, targetField.getModifiers() & ~Modifier.FINAL);
            
            targetField.setAccessible(true);
            targetField.set(null, newValue);
        } catch (Exception e) {
            logger.error("Exception occured when setting private static (final) field " + fieldName + " of " + objClass.getName(), e);
        }
    }

    // getPrivate - read private varibles of an object
    @SuppressWarnings("rawtypes")
	public static Object getPrivate(Object obj, Class objClass, String fieldName) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            return targetField.get(obj);
        } catch (Exception e) {
            logger.error("Exception occured when getting private field " + fieldName + " of " + objClass.getName(), e);
        }

        return null;
    }

    // setPrivate - set private variables of an object
    @SuppressWarnings("rawtypes")
	public static void setPrivate(Object obj, Class objClass, String fieldName, Object newValue) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            targetField.set(obj, newValue);
        } catch (Exception e) {
            logger.error("Exception occured when setting private field " + fieldName + " of " + objClass.getName(), e);
        }
    }
    
    // setPrivateInherited - set private variable of superclass of an object
    @SuppressWarnings("rawtypes")
	public static void setPrivateInherited(Object obj, Class objClass, String fieldName, Object newValue) {
    	try {
    		Field targetField = objClass.getSuperclass().getDeclaredField(fieldName);
    		targetField.setAccessible(true);
    		targetField.set(obj, newValue);
    	} catch (Exception e) {
    		logger.error("Exception occured when setting private field " + fieldName + " of the superclass of " + objClass.getName(), e);
    	}
    }
	
}
