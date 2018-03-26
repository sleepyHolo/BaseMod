package basemod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ModalChoiceCard extends AbstractCard
{
    public static final String ID = "ModalChoiceCard";
    private int index;
    private ModalChoice.ModalChoiceCallback callback;

    public ModalChoiceCard(String name, String rawDescription, CardColor color, CardTarget target, int index, ModalChoice.ModalChoiceCallback callback)
    {
        super(ID, name, null, "status/beta", -2, rawDescription, CardType.SKILL, color, CardRarity.BASIC, target, 0);
        portraitImg = ImageMaster.CARD_LOCKED_SKILL;
        dontTriggerOnUseCard = true;

        this.index = index;
        this.callback = callback;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        callback.optionSelected(index);
    }

    @Override
    public void upgrade()
    {
        // NOP. Should never be upgraded
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new ModalChoiceCard(name, rawDescription, color, target, index, callback);
    }
}
