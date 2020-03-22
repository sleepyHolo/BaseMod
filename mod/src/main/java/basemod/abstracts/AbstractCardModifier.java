package basemod.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
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
    public void onUse(AbstractCard card, AbstractCreature target) {

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
     * called when the card is discarded.
     * WARNING: this hook might possibly be unstable.
     * Please keep an eye on if it ever triggers when it should not.
     */
    public void onDiscarded(AbstractCard card) {

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

    /**
     * update/render hooks. Can be used to attach particle systems to a card, or a bottle icon, or similar.
     */
    public void onUpdate(AbstractCard card) {

    }

    public void onRender(AbstractCard card, SpriteBatch sb) {

    }

    /**
     * triggers at the end of the player's turn. The group passed is the current location of the card at the
     * time that this method is called.
     */
    public void atEndOfTurn(AbstractCard card, CardGroup group) {

    }

    /**
     * Method group used for implementing an alternative cost for cards. Gold, Health, orb slots, stacks of a power, etc.
     * most methods should use card.costForTurn for checking against it.
     *
     * Method Determines both the existence of an alternate cost, and whether the player has sufficient amount of it.
     * return -1 for no alternate cost, or an int for how much energy-equivalent the player has in that resource.
     * For example, if the player has 8 stacks of a power, and every 3 stacks equals 1 energy, return 2.
     */
    public int getAlternateResource(AbstractCard card) {
        return -1;
    }

    /**
     * determines whether the alternate cost is to be considered before normal energy expenditure.
     */
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return false;
    }

    /**
     * determines whether the cost can be split. For example, if the player has only 2 energy
     * and 2 resource, but the cost is 3, spend 2 and 1.
     */
    public boolean canSplitCost(AbstractCard card) {
        return false;
    }

    /**
     * "costs" should be "spent", or removed, in this method.
     * If the cost can be split, return should be remaining energy to spend. Else return 0. Resources spent should
     * be based on the int passed, rather than card.costForTurn, due to other mods possibly interacting with it.
     */
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        return 0;
    }

    /**
     * Manipulate the way the cost variable displays. For example, you could make the cost render as 'Y'.
     * Note: the mod with higher priority will prioritize the change, due to it being the last to run this method.
     */
    public String replaceCostString(AbstractCard card, String currentCostString) {
        return currentCostString;
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
