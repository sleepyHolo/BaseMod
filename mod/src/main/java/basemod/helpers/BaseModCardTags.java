package basemod.helpers;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BaseModCardTags
{
	// For Back to Basics, Vampires, Pandora's Box
	@Deprecated
	public static AbstractCard.CardTags BASIC_STRIKE = AbstractCard.CardTags.STARTER_STRIKE;
	@Deprecated
	public static AbstractCard.CardTags BASIC_DEFEND = AbstractCard.CardTags.STARTER_DEFEND;
	// For My True Form
	@SpireEnum public static AbstractCard.CardTags FORM;
}
