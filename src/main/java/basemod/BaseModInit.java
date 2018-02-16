package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import basemod.interfaces.PostInitializeSubscriber;

/**
 * 
 * Handles the creation of the ModBadge and settings panel for BaseMod
 *
 */
public class BaseModInit implements PostInitializeSubscriber {
    private static final String MODNAME = "BaseMod";
    private static final String AUTHOR = "t-larson, daviscook447, FlipskiZ";
    private static final String DESCRIPTION = "v1.6.3 NL Provides hooks and a console.";
	
    private InputProcessor oldInputProcessor;
	
	@Override
	public void receivePostInitialize() {
        // BaseMod post initialize handling
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("", 475.0f, 700.0f, (me) -> {
            if (me.parent.waitingOnEvent) {
                me.text = "Press key";
            } else {
                me.text = "Change console hotkey (" + Keys.toString(DevConsole.toggleKey) + ")";
            }
        });
        
        settingsPanel.addButton(350.0f, 650.0f, (me) -> {
            me.parent.waitingOnEvent = true;
            oldInputProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean keyUp(int keycode) {
                    DevConsole.toggleKey = keycode;
                    me.parent.waitingOnEvent = false;
                    Gdx.input.setInputProcessor(oldInputProcessor);
                    return true;
                }
            });
        });
        
        Texture badgeTexture = new Texture(Gdx.files.internal("img/BaseModBadge.png"));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
	}
	
}