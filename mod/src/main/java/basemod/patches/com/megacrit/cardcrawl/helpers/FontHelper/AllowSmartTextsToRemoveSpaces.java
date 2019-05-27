package basemod.patches.com.megacrit.cardcrawl.helpers.FontHelper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.util.ArrayList;
import java.util.List;

public class AllowSmartTextsToRemoveSpaces {
	public static final String REMOVE_SPACE_SPECIAL_KEYWORD = "[REMOVE_SPACE]";

	@SpirePatch(
			clz = FontHelper.class,
			method = "renderSmartText",
			paramtypez = {
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
	public static class RenderSmartTextPatch {
		public static boolean removeSpace = false;
		public static boolean forceNotBreakingLine = false;

		@SpireInsertPatch(
				locator = RemoveSpecialWordLocator.class,
				localvars = {"word"}
		)
		public static void InsertRemoveSpecialWord(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, float lineSpacing, Color baseColor, @ByRef String[] word) {
			removeSpace = false;
			forceNotBreakingLine = false;
			if (word[0].startsWith(REMOVE_SPACE_SPECIAL_KEYWORD)) {
				word[0] = word[0].replace(REMOVE_SPACE_SPECIAL_KEYWORD, "");
				removeSpace = true;
				if (word[0].length() == 1) {
					forceNotBreakingLine = true;
				}
			}
		}

		private static class RemoveSpecialWordLocator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior method) throws Exception {
				Matcher matcher = new Matcher.MethodCallMatcher(String.class, "equals");
				return LineFinder.findInOrder(method, matcher);
			}
		}

		@SpireInsertPatch(
				locator = RemoveSpaceLocator.class,
				localvars = {"curWidth", "spaceWidth"}
		)
		public static void InsertRemoveSpace(SpriteBatch sb, BitmapFont font, String msg, float x, float y, float lineWidth, float lineSpacing, Color baseColor, @ByRef float[] curWidth, float spaceWidth) {
			if (removeSpace) {
				curWidth[0] -= spaceWidth;
			}
		}

		private static class RemoveSpaceLocator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior method) throws Exception {
				Matcher matcher = new Matcher.MethodCallMatcher(BitmapFont.class, "draw");
				List<Matcher> prereqs = new ArrayList<>();
				prereqs.add(matcher);
				return LineFinder.findInOrder(method, prereqs, matcher);
			}
		}

		public static ExprEditor Instrument() throws CannotCompileException {
			final int[] counter = {0};
			return changeSecondLayoutWidthExprEditor(counter, RenderSmartTextPatch.class.getName());
		}
	}

	@SpirePatch(
			clz = FontHelper.class,
			method = "getSmartHeight",
			paramtypez = {
					BitmapFont.class,
					String.class,
					float.class,
					float.class
			}
	)
	public static class GetSmartHeightPatch {
		public static boolean forceNotBreakingLine = false;

		@SpireInsertPatch(
				locator = RemoveSpecialWordLocator.class,
				localvars = {"word"}
		)
		public static void Insert(BitmapFont font, String msg, float lineWidth, float lineSpacing, @ByRef String[] word) {
			forceNotBreakingLine = false;
			if (word[0].startsWith(REMOVE_SPACE_SPECIAL_KEYWORD)) {
				word[0] = word[0].replace(REMOVE_SPACE_SPECIAL_KEYWORD, "");
				if (word[0].length() == 1) {
					forceNotBreakingLine = true;
				}
			}
		}

		private static class RemoveSpecialWordLocator extends SpireInsertLocator {
			@Override
			public int[] Locate(CtBehavior method) throws Exception {
				Matcher matcher = new Matcher.MethodCallMatcher(String.class, "equals");
				return LineFinder.findInOrder(method, matcher);
			}
		}

		public static ExprEditor Instrument() throws CannotCompileException {
			final int[] counter = {0};
			return changeSecondLayoutWidthExprEditor(counter, GetSmartHeightPatch.class.getName());
		}
	}

	private static ExprEditor changeSecondLayoutWidthExprEditor(int[] counter, String className) {
		return new ExprEditor() {
			public void edit(FieldAccess f) throws CannotCompileException {
				if (f.getFieldName().equals("width")) {
					counter[0]++;
					if (counter[0] == 2) {
						f.replace("if(" + className + ".forceNotBreakingLine) $_ = 0.0F; else $_ = $proceed($$);");
					}
				}
			}
		};
	}
}
