package basemod.abstracts.cardbuilder.actionbuilder;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import basemod.abstracts.cardbuilder.CardBasic;

public class PoisonActionBuilder extends ApplyPowerActionBuilder {
	
	public PoisonActionBuilder() {
		super();
	}
	
	public PoisonActionBuilder(int amount, int upgradedAmount) {
		super(amount, upgradedAmount);
	}

	@Override
	public AbstractPower buildPower(CardBasic card, AbstractPlayer player, AbstractMonster monster) {
		return new PoisonPower(monster, player, getAmount(card));
	}
	
	@Override
	public String effectName() {
		return "Posion";
	}
	
}
