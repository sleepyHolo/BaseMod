package basemod.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractCardModifier implements Comparable<AbstractCardModifier> {
    public int priority = 0;

    /**
     * Methods determine times when the card mod will be removed from the card.
     * Any number of these can be set to return true, and are implemented as methods
     * to give the user more control of the case-by-case.
     */
    public boolean removeOnCardPlayed(AbstractCard card) {
        return false;
    }

    //note: this method behaves the same way the game treats temporary costs. Any time a "costForTurn" would be reset, this is called.
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return false;
    }

    /**
     * Methods modify the relevant stat as it's being calculated on the game's end, without
     * interfering with normal card stats. The return is the new value used by the game.
     * all card parameters pass the instance of the card which the mod is applied to.
     * AbstractMonster parameters will pass null when called from ApplyPowers.
     */
    //called before related power functions, for flat increases, to happen before vulnerable.
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage;
    }

    //called before "final" power functions, to apply percentage increases, but before things like intangible.
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage;
    }

    //called before power functions, for flat increases.
    public float modifyBlock(float block, AbstractCard card) {
        return block;
    }

    //called after power functions, for percentage and immutable(panic button-like) changes.
    public float modifyBlockFinal(float block, AbstractCard card) {
        return block;
    }

    /**
     * Intercepts the creation of a card's description at the time that initializeDescription is called.
     * rawDescription can be manipulated freely at this point, and then the game will parse it as normal.
     */
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription;
    }

    /**
     * Called when the card is used, same timing as onUseCard hooks in powers and relics.
     */
    public void onUse(AbstractCard card) {

    }

    /**
     * called when the card is drawn.
     */
    public void onDrawn(AbstractCard card) {

    }

    /**
     * called when the card is exhausted.
     */
    public void onExhausted(AbstractCard card) {

    }
    /**
     * called when the mod is initially applied to the card, including when
     * a new instance of a card is created, and mods are copied to that new card.
     */
    public void onInitialApplication(AbstractCard card) {

    }

    public void onRemove(AbstractCard card) {

    }

    /**
     * called whenever applyPowers is called on a card. Useful for if you want an
     * effect to be tied to the existence of a power, or otherwise somehow dynamic.
     */
    public void onApplyPowers(AbstractCard card) {

    }
    public abstract AbstractCardModifier makeCopy();

    /**
     * lower number = calculates first. For list sorting purposes. Don't override. Or do, I'm a comment, not a cop.
     */
    @Override
    public int compareTo(AbstractCardModifier other) {
        return priority - other.priority;
    }
}
