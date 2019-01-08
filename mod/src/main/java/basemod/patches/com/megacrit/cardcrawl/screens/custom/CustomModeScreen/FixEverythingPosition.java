package basemod.patches.com.megacrit.cardcrawl.screens.custom.CustomModeScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FixEverythingPosition
{
	@SpirePatch(
			clz=CustomModeScreen.class,
			method="update"
	)
	public static class Update
	{
		@SpireInsertPatch(
				locator = LocatorBefore.class,
				localvars = {"scrollY"}
		)
		public static void InsertBefore(CustomModeScreen __instance, @ByRef float[] scrollY)
		{
			if (__instance.options.size() > PositionCharacterButtons.MAX_BUTTONS_PER_ROW) {
				int rows = __instance.options.size() / PositionCharacterButtons.MAX_BUTTONS_PER_ROW;
				scrollY[0] -= rows * 100 * Settings.scale;
			}
		}

		@SpireInsertPatch(
				locator = LocatorAfter.class,
				localvars = {"scrollY"}
		)
		public static void InsertAfter(CustomModeScreen __instance, @ByRef float[] scrollY)
		{
			if (__instance.options.size() > PositionCharacterButtons.MAX_BUTTONS_PER_ROW) {
				int rows = __instance.options.size() / PositionCharacterButtons.MAX_BUTTONS_PER_ROW;
				scrollY[0] += rows * 100 * Settings.scale;
			}
		}

		private static class LocatorBefore extends SpireInsertLocator
		{
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
			{
				Matcher finalMatcher = new Matcher.MethodCallMatcher(CustomModeScreen.class, "updateAscension");
				return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
			}
		}

		private static class LocatorAfter extends SpireInsertLocator
		{
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
			{
				Matcher finalMatcher = new Matcher.MethodCallMatcher(CustomModeScreen.class, "updateEmbarkButton");
				return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
			}
		}
	}

	@SpirePatch(
			clz=CustomModeScreen.class,
			method="renderScreen"
	)
	public static class RenderHeaders
	{
		@SpireInsertPatch(
				locator = LocatorBefore.class,
				localvars = {"scrollY"}
		)
		public static void InsertBefore(CustomModeScreen __instance, SpriteBatch sb, @ByRef float[] scrollY)
		{
			if (__instance.options.size() > PositionCharacterButtons.MAX_BUTTONS_PER_ROW) {
				int rows = __instance.options.size() / PositionCharacterButtons.MAX_BUTTONS_PER_ROW;
				scrollY[0] -= rows * 100 * Settings.scale;
			}
		}

		public static void Postfix(CustomModeScreen __instance, SpriteBatch sb)
		{
			if (__instance.options.size() > PositionCharacterButtons.MAX_BUTTONS_PER_ROW) {
				try {
					Field scrollY = CustomModeScreen.class.getDeclaredField("scrollY");
					scrollY.setAccessible(true);
					int rows = __instance.options.size() / PositionCharacterButtons.MAX_BUTTONS_PER_ROW;
					scrollY.setFloat(__instance, scrollY.getFloat(__instance) + rows * 100 * Settings.scale);
				} catch (IllegalAccessException | NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}

		private static class LocatorBefore extends SpireInsertLocator
		{
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
			{
				Matcher finalMatcher = new Matcher.MethodCallMatcher(CustomModeScreen.class, "renderHeader");
				List<Matcher> matchers = new ArrayList<>();
				matchers.add(finalMatcher);
				return LineFinder.findInOrder(ctMethodToPatch, matchers, finalMatcher);
			}
		}
	}

	@SpirePatch(
			clz=CustomModeScreen.class,
			method="renderAscension"
	)
	public static class RenderAscension
	{
		public static void Prefix(CustomModeScreen __instance, SpriteBatch sb)
		{
			if (__instance.options.size() > PositionCharacterButtons.MAX_BUTTONS_PER_ROW) {
				try {
					Field scrollY = CustomModeScreen.class.getDeclaredField("scrollY");
					scrollY.setAccessible(true);
					int rows = __instance.options.size() / PositionCharacterButtons.MAX_BUTTONS_PER_ROW;
					scrollY.setFloat(__instance, scrollY.getFloat(__instance) - rows * 100 * Settings.scale);
				} catch (IllegalAccessException | NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}

		public static void Postfix(CustomModeScreen __instance, SpriteBatch sb)
		{
			if (__instance.options.size() > PositionCharacterButtons.MAX_BUTTONS_PER_ROW) {
				try {
					Field scrollY = CustomModeScreen.class.getDeclaredField("scrollY");
					scrollY.setAccessible(true);
					int rows = __instance.options.size() / PositionCharacterButtons.MAX_BUTTONS_PER_ROW;
					scrollY.setFloat(__instance, scrollY.getFloat(__instance) + rows * 100 * Settings.scale);
				} catch (IllegalAccessException | NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
