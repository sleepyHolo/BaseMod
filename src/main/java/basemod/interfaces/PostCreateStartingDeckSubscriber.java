package basemod.interfaces;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;

public interface PostCreateStartingDeckSubscriber extends ISubscriber {
	void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass chosenClass, ArrayList<String> addCardsToMe);
}
