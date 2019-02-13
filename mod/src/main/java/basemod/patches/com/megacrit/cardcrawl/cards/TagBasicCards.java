package basemod.patches.com.megacrit.cardcrawl.cards;

import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.cards.red.Strike_Red;

public class TagBasicCards
{
	@SpirePatch(
			clz=Strike_Red.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=Strike_Green.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=Strike_Blue.class,
			method=SpirePatch.CONSTRUCTOR
	)
	public static class Strikes
	{
		public static void Postfix(AbstractCard __instance)
		{
			__instance.tags.add(BaseModCardTags.BASIC_STRIKE);
		}
	}

	@SpirePatch(
			clz=Defend_Red.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=Defend_Green.class,
			method=SpirePatch.CONSTRUCTOR
	)
	@SpirePatch(
			clz=Defend_Blue.class,
			method=SpirePatch.CONSTRUCTOR
	)
	public static class Defends
	{
		public static void Postfix(AbstractCard __instance)
		{
			__instance.tags.add(BaseModCardTags.BASIC_DEFEND);
		}
	}

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
	public static class Forms
	{
		public static void Postfix(AbstractCard __instance)
		{
			__instance.tags.add(BaseModCardTags.FORM);
		}
	}
}
