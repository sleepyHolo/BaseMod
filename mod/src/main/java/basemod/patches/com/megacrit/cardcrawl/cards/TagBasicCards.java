package basemod.patches.com.megacrit.cardcrawl.cards;

import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;

public class TagBasicCards
{
	@SpirePatch(
			clz=DemonForm.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=WraithForm.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=EchoForm.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=DevaForm.class,
			method=SpirePatch.CONSTRUCTOR
	)
	public static class Forms
	{
		public static void Postfix(AbstractCard __instance)
		{
			__instance.tags.add(BaseModCardTags.FORM);
		}
	}
}
