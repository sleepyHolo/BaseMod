package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="draw", paramtypes={"int"})
public class PostDrawHook {
    @SpireInsertPatch(loc=1036, localvars={"c"})
    public static void Insert(Object mObj, int numCards, Object cObj) {
    	AbstractCard c = (AbstractCard) cObj;
        BaseMod.publishPostDraw(c);
    }
}
