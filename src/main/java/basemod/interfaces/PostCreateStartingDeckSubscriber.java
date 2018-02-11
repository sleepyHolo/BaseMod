package basemod.interfaces;

import java.util.ArrayList;

public interface PostCreateStartingDeckSubscriber {
	boolean receivePostCreateStartingDeck(ArrayList<String> addCardsToMe);
}
