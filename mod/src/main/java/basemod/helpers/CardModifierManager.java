package basemod.helpers;

import basemod.abstracts.AbstractCardModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CardModifierManager
{
    private static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }

    public static void addModifier(AbstractCard card, AbstractCardModifier mod) {
        modifiers(card).add(mod);
        Collections.sort(modifiers(card));
    }

    public static void removeModifier(AbstractCard card, AbstractCardModifier mod) {
        if (modifiers(card).contains(mod)) {
            modifiers(card).remove(mod);
        }
    }

    public static void removeEndOfTurnModifiers(AbstractCard card) {
        Iterator<AbstractCardModifier> it = modifiers(card).iterator();
        while (it.hasNext()) {
            AbstractCardModifier mod = it.next();
            if (mod.removeAtEndOfTurn(card)) {
                it.remove();
            }
        }
    }

    public static void removeWhenPlayedModifiers(AbstractCard card) {
        Iterator<AbstractCardModifier> it = modifiers(card).iterator();
        while (it.hasNext()) {
            AbstractCardModifier mod = it.next();
            if (mod.removeOnCardPlayed(card)) {
                it.remove();
            }
        }
    }

}
