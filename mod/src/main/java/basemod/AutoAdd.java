package basemod;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CtClass;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;

public class AutoAdd
{
	public static <T> Collection<CtClass> findClasses(String modID, Filter pkgFilter, Class<T> type)
	{
		try {
			ClassFinder finder = new ClassFinder();
			for (ModInfo info : Loader.MODINFOS) {
				if (info != null && modID != null && modID.equals(info.ID) && info.jarURL != null) {
					finder.add(new File(info.jarURL.toURI()));
				}
			}

			if (pkgFilter == null) {
				pkgFilter = new Filter("");
			}
			ClassFilter filter =
					new AndClassFilter(
							new NotClassFilter(new InterfaceOnlyClassFilter()),
							new NotClassFilter(new AbstractClassFilter()),
							new ClassModifiersClassFilter(Modifier.PUBLIC),
							pkgFilter
					);
			Collection<ClassInfo> foundClasses = new ArrayList<>();
			finder.findClasses(foundClasses, filter);

			Collection<CtClass> ret = new ArrayList<>();
			for (ClassInfo classInfo : foundClasses) {
				CtClass ctClass = Loader.getClassPool().get(classInfo.getClassName());

				CtClass ctSuperClass = ctClass;
				do {
					if (ctSuperClass.getName().equals(type.getName())) {
						ret.add(ctClass);
						break;
					}
					ctSuperClass = ctSuperClass.getSuperclass();
				} while (ctSuperClass != null);
			}

			return ret;
		} catch (NotFoundException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> Collection<CtClass> findClasses(String modID, Class<T> type)
	{
		return findClasses(modID, null, type);
	}

	public static <T> void any(String modID, Filter pkgFilter, Class<T> type, BiConsumer<Info, T> add)
	{
		try {
			Collection<CtClass> foundClasses = findClasses(modID, pkgFilter, type);

			for (CtClass ctClass : foundClasses) {
				Info info = (Info) ctClass.getAnnotation(Info.class);
				if (info != null && info.ignore()) {
					continue;
				}

				T t = type.cast(Loader.getClassPool().getClassLoader().loadClass(ctClass.getName()).newInstance());
				add.accept(info, t);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> void any(String modID, Class<T> type, BiConsumer<Info, T> add)
	{
		any(modID, null, type, add);
	}

	public static void cards(String modID, Filter pkgFilter)
	{
		any(
				modID,
				pkgFilter,
				AbstractCard.class,
				(info, card) -> {
					BaseMod.addCard(card);
					if (info != null && info.seen()) {
						UnlockTracker.unlockCard(card.cardID);
					}
				}
		);
	}

	public static void cards(String modID, String pkgFilter)
	{
		cards(modID, new Filter(pkgFilter));
	}

	public static void cards(String modID, Class<?> pkgFilter)
	{
		cards(modID, new Filter(pkgFilter));
	}

	public static void cards(String modID)
	{
		cards(modID, "");
	}

	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Info
	{
		boolean ignore() default false;
		boolean seen() default false;
	}

	public static class Filter implements ClassFilter
	{
		private final String packageName;

		public Filter(String pkgName)
		{
			packageName = pkgName;
		}

		public Filter(Class<?> cls)
		{
			this(cls.getPackage().getName());
		}

		@Override
		public boolean accept(ClassInfo classInfo, ClassFinder classFinder)
		{
			return classInfo.getClassName().startsWith(packageName);
		}
	}
}
