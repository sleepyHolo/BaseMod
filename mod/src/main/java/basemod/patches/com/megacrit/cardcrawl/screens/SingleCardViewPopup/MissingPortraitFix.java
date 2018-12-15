package basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(
		clz=SingleCardViewPopup.class,
		method="renderPortrait"
)
public class MissingPortraitFix
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"card"}
	)
	public static SpireReturn<Void> Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card)
	{
		if (card.jokePortrait == null) {
			return SpireReturn.Return(null);
		}
		return SpireReturn.Continue();
	}

	private static class Locator extends SpireInsertLocator
	{
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
		{
			Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "jokePortrait");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}
