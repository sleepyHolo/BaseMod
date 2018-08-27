package basemod.patches.com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.Scanner;

public class SmartTextOrb
{
	@SpirePatch(
			clz=FontHelper.class,
			method="identifyOrb"
	)
	public static class IdentifyOrb
	{
		public static void Prefix(@ByRef String[] word)
		{
			if (word[0].endsWith(".")) {
				word[0] = word[0].substring(0, word[0].length()-1);
			}
		}

		public static TextureAtlas.AtlasRegion Postfix(TextureAtlas.AtlasRegion __result, String word)
		{
			if (__result == null && word.equals("[E]")) {
				return BaseMod.getCardSmallEnergy();
			}
			return __result;
		}
	}

	@SpirePatch(
			clz= FontHelper.class,
			method="renderSmartText",
			paramtypez={
					SpriteBatch.class,
					BitmapFont.class,
					String.class,
					float.class,
					float.class,
					float.class,
					float.class,
					Color.class
			}
	)
	public static class RenderSmartText
	{
		private static boolean didOrbPeriodFix = false;

		public static void Prefix(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, float lineSpacing, Color baseColor)
		{
			didOrbPeriodFix = false;
		}

		@SpireInsertPatch(
				locator= Locator1.class,
				localvars={"s", "word"}
		)
		public static void InsertResetScanner(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, float lineSpacing, Color baseColor, @ByRef Scanner[] s, String word)
		{
			if (word.endsWith(".")) {
				s[0].useDelimiter("\\A");
				String remaining = "." + (s[0].hasNext() ? " " + s[0].next() : "");
				s[0] = new Scanner(remaining);
				didOrbPeriodFix = true;
			}
		}

		private static class Locator1 extends SpireInsertLocator
		{
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
			{
				Matcher finalMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "setColor");
				return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
			}
		}

		@SpireInsertPatch(
				locator= Locator2.class,
				localvars={"curWidth", "spaceWidth"}
		)
		public static void InsertFixSpaceAfterOrb(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, float lineSpacing, Color baseColor, @ByRef float[] curWidth, float spaceWidth)
		{
			if (didOrbPeriodFix) {
				didOrbPeriodFix = false;
				curWidth[0] -= spaceWidth * 1.4f;
			}
		}

		private static class Locator2 extends SpireInsertLocator
		{
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
			{
				Matcher finalMatcher = new Matcher.MethodCallMatcher(Scanner.class, "next");
				return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
			}
		}
	}
}
