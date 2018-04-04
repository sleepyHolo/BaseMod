package basemod.abstracts.cardbuilder.actionbuilder;

import java.util.function.Supplier;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.cardbuilder.CardBasic;

public class ApplyPowerActionBuilder extends AmountActionBuilder {
	
	@FunctionalInterface
	public interface Function {
		public AbstractPower apply(CardBasic card, AbstractPlayer player, AbstractMonster monster, Integer amount);
	}
	
	private boolean targetsPlayer;
	private Function buildPower;
	private Supplier<String> effectName;
	
	public ApplyPowerActionBuilder(Function buildPower,
			Supplier<String> effectName) {
		this(false, buildPower, effectName);
	}
	
	public ApplyPowerActionBuilder(boolean targetsPlayer,
			Function buildPower, Supplier<String> effectName) {
		super();
		this.targetsPlayer = targetsPlayer;
		this.buildPower = buildPower;
		this.effectName = effectName;
	}
	
	public ApplyPowerActionBuilder(int amount, int upgradedAmount,
			Function buildPower, Supplier<String> effectName) {
		this(amount, upgradedAmount, false, buildPower, effectName);
	}
	
	public ApplyPowerActionBuilder(int amount, int upgradedAmount, boolean targetsPlayer,
			Function buildPower, Supplier<String> effectName) {
		super(amount, upgradedAmount);
		this.targetsPlayer = targetsPlayer;
		this.buildPower = buildPower;
		this.effectName = effectName;
	}
	
	@Override
	public String description(CardBasic card) {
		StringBuilder description = new StringBuilder();
		if (targetsPlayer) {
			description.append("Gain ");
		} else {
			description.append("Apply ");
		}
		if (usingMagicNumber()) {
			description.append("!M! ");
		} else {
			description.append(getAmount(card));
			description.append(" ");
		}
		description.append(effectName.get());
		description.append(". ");
		return description.toString();
	}
	
	@Override
	public AbstractGameAction buildAction(CardBasic card, AbstractPlayer player, AbstractMonster monster) {
		AbstractPower powerToApply = buildPower.apply(card, player, monster, getAmount(card));
		if (targetsPlayer) {
			return new ApplyPowerAction(player, player, powerToApply, getAmount(card));
		} else {
			return new ApplyPowerAction(monster, player, powerToApply, getAmount(card));
		}
		
	}
	
}
