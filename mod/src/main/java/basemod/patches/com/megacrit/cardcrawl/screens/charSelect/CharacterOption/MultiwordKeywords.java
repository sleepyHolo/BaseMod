package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
		clz=CharacterOption.class,
		method="renderRelics"
)
public class MultiwordKeywords {

	@SpireInsertPatch(
			locator = Locator.class,
			localvars = {"relicString"}
	)
	public static void Insert(CharacterOption __instance, SpriteBatch sb, @ByRef String[] relicString) {
		relicString[0] = basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.MultiwordKeywords.replaceMultiWordKeywords(relicString[0]);
	}

	public static class Locator extends SpireInsertLocator {
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
			Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderSmartText");

			return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
		}
	}
}
