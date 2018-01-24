package basemod.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface PreMonsterTurnSubscriber {
    public boolean receivePreMonsterTurn(AbstractMonster m);
}