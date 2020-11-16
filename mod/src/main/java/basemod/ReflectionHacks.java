package basemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReflectionHacks
{
	public static final Logger logger = LogManager.getLogger(ReflectionHacks.class.getName());

	private static final Map<Pair<Class<?>, String>, Field> fieldMap = new HashMap<>();

	static {
		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			fieldMap.put(new Pair<>(Field.class, "modifiers"), modifiersField);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	private ReflectionHacks()
	{
	}

	/**
	 * Returns the {@code Field} object for the field from the class described by {@code clz} with the name {@code fieldName}.
	 * <p> {@code Field}s returned by this method have already had their {@code accessible} flag set to true.
	 * <p> After the first request for a specific {@code Field}, the {@code Field} is cached so future requests are faster.
	 *
	 * @param clz the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @return the {@code Field} object for the specified field in {@code clz}
	 */
	public static Field getCachedField(Class<?> clz, String fieldName)
	{
		Pair<Class<?>, String> pair = new Pair<>(clz, fieldName);
		Field ret = fieldMap.get(pair);
		if (ret == null) {
			try {
				Field f = clz.getDeclaredField(fieldName);
				f.setAccessible(true);
				ret = f;
				fieldMap.put(pair, f);
			} catch (NoSuchFieldException e) {
				logger.error("Exception occurred when getting field " + fieldName + " of " + clz.getName(), e);
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * Returns the value of a {@code static} field.
	 *
	 * @param objClass the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @param <T> the type of the field
	 * @return the value of the field
	 */
	public static <T> T getPrivateStatic(Class<?> objClass, String fieldName)
	{
		try {
			//noinspection unchecked
			return (T) getCachedField(objClass, fieldName).get(null);
		} catch (Exception e) {
			logger.error("Exception occurred when getting private static field " + fieldName + " of " + objClass.getName(), e);
		}
		return null;
	}

	/**
	 * Sets the value of a {@code static} field.
	 *
	 * @param objClass the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @param newValue the value to set the field to
	 */
	public static void setPrivateStatic(Class<?> objClass, String fieldName, Object newValue)
	{
		try {
			getCachedField(objClass, fieldName).set(null, newValue);
		} catch (Exception e) {
			logger.error("Exception occurred when setting private static field " + fieldName + " of " + objClass.getName(), e);
		}
	}

	/**
	 * Sets the value of a {@code final static} field.
	 *
	 * @param objClass the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @param newValue the value to set the field to
	 */
	public static void setPrivateStaticFinal(Class<?> objClass, String fieldName, Object newValue)
	{
		try {
			Field f = getCachedField(objClass, fieldName);

			Field modifiers = getCachedField(Field.class, "modifiers");
			modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);

			f.set(null, newValue);

			modifiers.setInt(f, f.getModifiers() & Modifier.FINAL);
		} catch (Exception e) {
			logger.error("Exception occurred when setting private static (final) field " + fieldName + " of " + objClass.getName(), e);
		}
	}

	/**
	 * Returns the value of a field from the given object.
	 *
	 * @param obj the object to get the field value from
	 * @param objClass the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @param <T> the type of the field
	 * @return the value of the field
	 */
	public static <T> T getPrivate(Object obj, Class<?> objClass, String fieldName)
	{
		try {
			//noinspection unchecked
			return (T) getCachedField(objClass, fieldName).get(obj);
		} catch (Exception e) {
			logger.error("Exception occurred when getting private field " + fieldName + " of " + objClass.getName(), e);
		}

		return null;
	}

	/**
	 * Sets the value of a field in the given object.
	 *
	 * @param obj the object to set the field value of
	 * @param objClass the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @param newValue the value to set the field to
	 */
	public static void setPrivate(Object obj, Class<?> objClass, String fieldName, Object newValue)
	{
		try {
			getCachedField(objClass, fieldName).set(obj, newValue);
		} catch (Exception e) {
			logger.error("Exception occurred when setting private field " + fieldName + " of " + objClass.getName(), e);
		}
	}

	/**
	 * Sets the value of a {@code static} field.
	 *
	 * @param obj the object to set the field value of
	 * @param objClass the {@code Class} the field is declared in
	 * @param fieldName the name of the field
	 * @param newValue the value to set the field to
	 */
	public static void setPrivateFinal(Object obj, Class<?> objClass, String fieldName, Object newValue)
	{
		try {
			Field f = getCachedField(objClass, fieldName);

			Field modifiers = getCachedField(Field.class, "modifiers");
			modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);

			f.set(obj, newValue);

			modifiers.setInt(f, f.getModifiers() & Modifier.FINAL);
		} catch (Exception e) {
			logger.error("Exception occurred when setting private (final) field " + fieldName + " of " + objClass.getName(), e);
		}
	}

	/**
	 * Returns the value of a field from a superclass of {@code obj}.
	 * <p> Searches up the chain of superclasses until it finds a field with the correct name.
	 * If no such field is found, returns {@code null}.
	 * <p> If the found field type cannot be cast to {@code <T>} a {@code ClassCastException} will be thrown.
	 *
	 * @param obj the object to get the field from
	 * @param objClass the {@code Class} of {@code obj}
	 * @param fieldName the name of the field
	 * @param <T> the type of the field
	 * @return the value of the field
	 */
	public static <T> T getPrivateInherited(Object obj, Class<?> objClass, String fieldName)
	{
		objClass = objClass.getSuperclass();
		while (objClass != null && objClass != Object.class) {
			try {
				Field f = objClass.getDeclaredField(fieldName);
				f.setAccessible(true);
				try {
					//noinspection unchecked
					return (T) f.get(obj);
				} catch (IllegalAccessException e) {
					logger.error("Exception occurred when getting private field " + fieldName + " of the superclass of " + objClass.getName(), e);
					return null;
				}
			} catch (NoSuchFieldException ignored) {}
			objClass = objClass.getSuperclass();
		}

		return null;
	}

	/**
	 * Sets the value of a field of a superclass of {@code obj}.
	 * <p> Searches up the chain of superclasses until it finds a field with the correct name.
	 *
	 * @param obj the object to set the field of
	 * @param objClass the {@code Class} of {@code obj}
	 * @param fieldName the name of the field
	 * @param newValue the value to set the field to
	 */
	public static void setPrivateInherited(Object obj, Class<?> objClass, String fieldName, Object newValue)
	{
		objClass = objClass.getSuperclass();
		while (objClass != null && objClass != Object.class) {
			try {
				Field f = objClass.getDeclaredField(fieldName);
				f.setAccessible(true);
				try {
					f.set(obj, newValue);
				} catch (IllegalAccessException e) {
					logger.error("Exception occurred when setting private field " + fieldName + " of the superclass of " + objClass.getName(), e);
				}
				return;
			} catch (NoSuchFieldException ignored) {}
			objClass = objClass.getSuperclass();
		}
	}
}
