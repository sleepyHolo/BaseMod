package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.interfaces.AlternateCardCostModifier;
import basemod.interfaces.XCostModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.AlternateCardCosts;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "useCard"
)
public class ModifyXCostPatch
{

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.cost == -1) {
            int effect = c.energyOnUse;
            ArrayList<List> lists = new ArrayList<>();
            lists.add(AbstractDungeon.player.hand.group);
            lists.add(AbstractDungeon.player.drawPile.group);
            lists.add(AbstractDungeon.player.discardPile.group);
            lists.add(AbstractDungeon.player.powers);
            lists.add(AbstractDungeon.player.relics);
            lists.add(CardModifierPatches.CardModifierFields.cardModifiers.get(c));
            for (List list : lists) {
                for (Object item : list) {
                    if (item instanceof XCostModifier) {
                        XCostModifier mod = (XCostModifier)item;
                        if (mod.xCostModifierActive(c)) {
                            effect += mod.modifyX(c);
                        }
                    }
                }
            }
            c.energyOnUse = effect;
        }
    }

    public static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
