package basemod.interfaces;

import java.util.ArrayList;

public interface PostCreateStartingRelicsSubscriber extends ISubscriber {
	@Deprecated
	boolean receivePostCreateStartingRelics(ArrayList<String> addRelicsToMe);
}
