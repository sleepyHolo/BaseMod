package basemod.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface PreMonsterTurnSubscriber {
    boolean receivePreMonsterTurn(AbstractMonster m);
}