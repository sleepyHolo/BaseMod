package basemod.patches.com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CustomActionHooks {

    @SuppressWarnings("unused")
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

                // Callback that was passed in from `open` is executed here
                GridCardSelectScreenFields.customCallback.get(__instance).callback(selectedCards);

                // Reset screen
                __instance.selectedCards.clear();
                GridCardSelectScreenFields.resetFields(__instance);
            }
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = GridCardSelectScreen.class, method = "reopen")
    public static class reopenPatch {
        public static void Postfix(GridCardSelectScreen __instance) {
            if (GridCardSelectScreenFields.forCustomReward.get(__instance)) {
                AbstractDungeon.overlayMenu.proceedButton.hide();
            }
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = GridCardSelectScreen.class, method = "callOnOpen")
    public static class resetFlagsOnOpen {
        public static void Prefix(GridCardSelectScreen __instance) {
            GridCardSelectScreenFields.resetFields(__instance);
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class resetFlagsOnClose {
        @SpireInsertPatch(rloc = 51)
        public static void Insert() {
            if (GridCardSelectScreenFields.forCustomReward.get(AbstractDungeon.gridSelectScreen)) {
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }
    }
}
