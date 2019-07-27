package basemod.patches.com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;

@SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
public class GridCardSelectScreenFields {

    public interface GridCallback {
        void callback(ArrayList<AbstractCard> selectedCards);
    }

    public static SpireField<Boolean> forCustomReward = new SpireField<>(() -> false);
    public static SpireField<GridCallback> customCallback = new SpireField<>(() -> null);

    static void resetFields(GridCardSelectScreen instance) {
        GridCardSelectScreenFields.forCustomReward.set(instance, false);
        GridCardSelectScreenFields.customCallback.set(instance, null);
    }
}
