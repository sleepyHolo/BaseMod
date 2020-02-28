package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;

import java.util.ArrayList;
import java.util.Map;

@SpirePatch(
		clz=CardLibrary.class,
		method="getCardList"
)
public class GetCardListSwitch
{
	public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result, LibraryType type)
	{
		if (!BaseMod.isBaseGameCardColor(AbstractCard.CardColor.valueOf(type.name()))) {
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				if (c.getValue().color.name().equals(type.name())) {
					__result.add(c.getValue());
				}
			}
		}
		return __result;
	}
}
