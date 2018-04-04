package basemod.abstracts.cardbuilder.actionbuilder;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.cardbuilder.CardBasic;

public abstract class ApplyPowerActionBuilder extends AmountActionBuilder {
	
	public ApplyPowerActionBuilder() {
		super();
	}
	
	public ApplyPowerActionBuilder(int amount, int upgradedAmount) {
		super(amount, upgradedAmount);
	}
	
	public abstract AbstractPower buildPower(CardBasic card, AbstractPlayer player, AbstractMonster monster);
	
	public abstract String effectName();
	
	@Override
	public String description(CardBasic card) {
		StringBuilder description = new StringBuilder();
		description.append("Apply ");
		if (usingMagicNumber()) {
			description.append("!M! ");
		} else {
			description.append(getAmount(card));
			description.append(" ");
		}
		description.append(effectName());
		description.append(". ");
		return description.toString();
	}
	
	@Override
	public AbstractGameAction buildAction(CardBasic card, AbstractPlayer player, AbstractMonster monster) {
		AbstractPower powerToApply = buildPower(card, player, monster);
		return new ApplyPowerAction(player, player, powerToApply, getAmount(card));
	}
	
}
