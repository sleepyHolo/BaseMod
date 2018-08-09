package basemod.patches.com.megacrit.cardcrawl.events.GremlinMatchGame;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

@SpirePatch(
		cls="com.megacrit.cardcrawl.events.shrines.GremlinMatchGame",
		method="initializeCards"
)
public class ClassSpecificCard
{
	@SpireInsertPatch(
			rloc=32,
			localvars={"retVal"}
	)
	public static void Insert(GremlinMatchGame __instance, ArrayList<AbstractCard> retVal)
	{
		String cardID = BaseMod.playerGremlinMatchCardIDMap.get(AbstractDungeon.player.chosenClass.toString());
		if (cardID != null) {
			retVal.add(CardLibrary.getCopy(cardID));
		}
	}
}
