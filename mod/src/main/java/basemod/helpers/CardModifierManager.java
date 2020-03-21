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
        mod.onInitialApplication(card);
    }

    public static void removeModifier(AbstractCard card, AbstractCardModifier mod) {
        if (modifiers(card).contains(mod)) {
            modifiers(card).remove(mod);
            mod.onRemove(card);
        }
    }

    public static void removeEndOfTurnModifiers(AbstractCard card) {
        Iterator<AbstractCardModifier> it = modifiers(card).iterator();
        while (it.hasNext()) {
            AbstractCardModifier mod = it.next();
            if (mod.removeAtEndOfTurn(card)) {
                it.remove();
                mod.onRemove(card);
            }
        }
    }

    public static void removeWhenPlayedModifiers(AbstractCard card) {
        Iterator<AbstractCardModifier> it = modifiers(card).iterator();
        while (it.hasNext()) {
            AbstractCardModifier mod = it.next();
            if (mod.removeOnCardPlayed(card)) {
                it.remove();
                mod.onRemove(card);
            }
        }
    }

    public static void removeAllModifiers(AbstractCard card) {
        Iterator<AbstractCardModifier> it = modifiers(card).iterator();
        while (it.hasNext()) {
            AbstractCardModifier mod = it.next();
            it.remove();
            mod.onRemove(card);
        }
    }

    public static void copyModifiers(AbstractCard oldCard, AbstractCard newCard) {
        removeAllModifiers(newCard);
        modifiers(oldCard).forEach((item) -> {
            AbstractCardModifier newMod = item.makeCopy();
            modifiers(newCard).add(newMod);
            newMod.onInitialApplication(newCard);
        });
    }

    public static void onApplyPowers(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onApplyPowers(card);
        }
    }

    public static void onCreateDescription(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            card.rawDescription = mod.modifyDescription(card.rawDescription, card);
        }
    }

    public static void onUseCard(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onUse(card);
        }
    }

    public static void onCardDrawn(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onDrawn(card);
        }
    }

    public static void onCardExhausted(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onExhausted(card);
        }
    }

    public static void onCardDiscarded(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onDiscarded(card);
        }
    }

    public static float onModifyDamage(float damage, AbstractCard card, AbstractMonster mo) {
        for (AbstractCardModifier mod : modifiers(card)) {
            damage = mod.modifyDamage(damage, card.damageTypeForTurn, card, mo);
        }
        return damage;
    }

    public static float onModifyDamageFinal(float damage, AbstractCard card, AbstractMonster mo) {
        for (AbstractCardModifier mod : modifiers(card)) {
            damage = mod.modifyDamageFinal(damage, card.damageTypeForTurn, card, mo);
        }
        return damage;
    }

    public static float onModifyBlock(float block, AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            block = mod.modifyBlock(block, card);
        }
        return block;
    }

    public static float onModifyBlockFinal(float block, AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            block = mod.modifyBlockFinal(block, card);
        }
        return block;
    }

    public static void onUpdate(AbstractCard card) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onUpdate(card);
        }
    }

    public static void onRender(AbstractCard card, SpriteBatch sb) {
        for (AbstractCardModifier mod : modifiers(card)) {
            mod.onRender(card, sb);
        }
    }

    public static String getCostString(AbstractCard card, String currentString) {
        for (AbstractCardModifier mod : modifiers(card)) {
            currentString = mod.replaceCostString(card, currentString);
        }
        return currentString;
    }
}
