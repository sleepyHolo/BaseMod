package basemod.patches.com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;
import java.util.List;

public class CustomActionHooks {

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class updatePatch {

        @SpireInsertPatch(rloc = 61)
        public static void Insert(GridCardSelectScreen __instance) {
            if (GridCardSelectScreenFields.forCustomReward.get(__instance)) {
                ArrayList<AbstractCard> selectedCards = __instance.selectedCards;
                selectedCards.forEach(AbstractCard::stopGlowing);

                if (GridCardSelectScreenFields.customCallback.get(__instance) == null) {
                    BaseMod.logger.error("GridCardSelectScreen is being called for a custom reward without a callback");
                }

                GridCardSelectScreenFields.customCallback.get(__instance).callback(selectedCards);
                __instance.selectedCards.clear();

                GridCardSelectScreenFields.forCustomReward.set(__instance, false);
                GridCardSelectScreenFields.customCallback.set(__instance, null);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "reopen")
    public static class reopenPatch {
        public static void Postfix(GridCardSelectScreen __instance) {
            if (GridCardSelectScreenFields.forCustomReward.get(__instance)) {
                AbstractDungeon.overlayMenu.proceedButton.hide();
            }
        }
    }
}
