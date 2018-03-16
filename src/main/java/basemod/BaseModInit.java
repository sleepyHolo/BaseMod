package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;

import basemod.interfaces.PostInitializeSubscriber;

/**
 * 
 * Handles the creation of the ModBadge and settings panel for BaseMod
 *
 */
public class BaseModInit implements PostInitializeSubscriber {
    public static final String MODNAME = "BaseMod";
    public static final String AUTHOR = "t-larson, test447, FlipskiZ, Haashi, Blank The Evil";
    public static final String DESCRIPTION = "Modding API and Dev console.";
	
    private InputProcessor oldInputProcessor;
	
	@SuppressWarnings("deprecation")
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
                    BaseMod.maybeSetString("console-key", Keys.toString(keycode));
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