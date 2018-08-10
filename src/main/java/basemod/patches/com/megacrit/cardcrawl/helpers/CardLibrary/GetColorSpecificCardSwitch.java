package basemod.patches.com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Map;

@SpirePatch(
		cls="com.megacrit.cardcrawl.helpers.CardLibrary",
		method="getColorSpecificCard",
		paramtypes={
				"com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass",
				"com.megacrit.cardcrawl.random.Random"
		}
)
public class GetColorSpecificCardSwitch
{
	public static SpireReturn<AbstractCard> Prefix(AbstractPlayer.PlayerClass chosenClass, Random rand)
	{
		if (!BaseMod.isBaseGameCharacter(chosenClass)) {
			ArrayList<String> tmp = new ArrayList<>();
			for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
				if (BaseMod.getColor(chosenClass) == c.getValue().color) {
					tmp.add(c.getKey());
				}
			}
			return SpireReturn.Return(CardLibrary.cards.get(tmp.get(rand.random(0, tmp.size() - 1))));
		}

		return SpireReturn.Continue();
	}
}
