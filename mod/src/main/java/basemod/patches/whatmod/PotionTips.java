package basemod.patches.whatmod;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import javassist.*;
import org.clapper.util.classutil.*;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// What class/method we put here doesn't matter
@SpirePatch(
		clz=CardCrawlGame.class,
		method=SpirePatch.CONSTRUCTOR
)
public class PotionTips
{
	public static void WhatMod(AbstractPotion __instance)
	{
		if (WhatMod.enabled) {
			if (__instance.tips.size() > 0 && __instance.tips.get(0).header.toLowerCase().equals(__instance.name.toLowerCase())) {
				String modName = WhatMod.findModName(__instance.getClass());
				if (modName != null) {
					__instance.tips.get(0).body = FontHelper.colorString(modName, "p") + " NL " + __instance.tips.get(0).body;
				}
			}
		}
	}

	public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException
	{
		ClassFinder finder = new ClassFinder();
		// This only searches mod files, as base game potions shouldn't show whatmod anyways
		finder.add(
				Arrays.stream(Loader.MODINFOS)
						.map(modInfo -> modInfo.jarURL)
						.filter(Objects::nonNull)
						.map(url -> {
							try {
								return url.toURI();
							} catch (URISyntaxException e) {
								return null;
							}
						})
						.filter(Objects::nonNull)
						.map(File::new)
						.collect(Collectors.toList())
		);

		ClassPool pool = ctBehavior.getDeclaringClass().getClassPool();

		ClassFilter filter =
				new AndClassFilter(
						new NotClassFilter(new InterfaceOnlyClassFilter()),
						new NotClassFilter(new AbstractClassFilter()),
						new ClassModifiersClassFilter(Modifier.PUBLIC),
						new SuperClassFilter(pool, AbstractPotion.class)
				);
		List<ClassInfo> foundClasses = new ArrayList<>();
		finder.findClasses(foundClasses, filter);

		String src = PotionTips.class.getName() + ".WhatMod(this);";

		for (ClassInfo classInfo : foundClasses) {
			try {
				CtMethod initializeData = null;
				CtClass ctClass = pool.get(classInfo.getClassName());
				try {
					initializeData = ctClass.getDeclaredMethod("initializeData");
				} catch (NotFoundException ignored) {
				}

				// If the potion defines initializeData(), postfix it
				// Otherwise, postfix all of its constructors
				if (initializeData != null) {
					initializeData.insertAfter(src);
				} else {
					for (CtConstructor ctor : ctClass.getDeclaredConstructors()) {
						ctor.insertAfter(src);
					}
				}
			} catch (NotFoundException ignored) {
			}
		}
	}

	private static class SuperClassFilter implements ClassFilter
	{
		private ClassPool pool;
		private CtClass baseClass;

		public SuperClassFilter(ClassPool pool, Class<?> baseClass) throws NotFoundException
		{
			this.pool = pool;
			this.baseClass = pool.get(baseClass.getName());
		}

		@Override
		public boolean accept(ClassInfo classInfo, ClassFinder classFinder)
		{
			try {
				CtClass ctClass = pool.get(classInfo.getClassName());
				while (ctClass != null) {
					if (ctClass.equals(baseClass)) {
						return true;
					}
					ctClass = ctClass.getSuperclass();
				}
			} catch (NotFoundException ignored) {
			}

			return false;
		}
	}
}
