package basemod;

import basemod.interfaces.PostInitializeSubscriber;

public class EditCharactersInit implements PostInitializeSubscriber {

	@Override
	public void receivePostInitialize() {
		BaseMod.publishEditCharacters();
	}
	
}
