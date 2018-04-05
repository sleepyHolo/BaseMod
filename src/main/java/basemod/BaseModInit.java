package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.interfaces.PostInitializeSubscriber;

/**
 * 
 * Handles the creation of the ModBadge and settings panel for BaseMod
 *
 */
public class BaseModInit implements PostInitializeSubscriber {
    public static final String MODNAME = "BaseMod";
    public static final String AUTHOR = "t-larson, test447, FlipskiZ, Haashi, Blank The Evil, kiooeht";
    public static final String DESCRIPTION = "Modding API and Dev console.";
	
    private InputProcessor oldInputProcessor;
	
    public static final float BUTTON_X = 350.0f;
    public static final float BUTTON_Y = 650.0f;
    public static final float BUTTON_LABEL_X = 475.0f;
    public static final float BUTTON_LABEL_Y = 700.0f;
    public static final float BUTTON_ENABLE_X = 350.0f;
    public static final float BUTTON_ENABLE_Y = 600.0f;
    
	@Override
	public void receivePostInitialize() {
        // BaseMod post initialize handling
        ModPanel settingsPanel = new ModPanel();
        
        ModLabel buttonLabel = new ModLabel("", BUTTON_LABEL_X, BUTTON_LABEL_Y, settingsPanel, (me) -> {
            if (me.parent.waitingOnEvent) {
                me.text = "Press key";
            } else {
                me.text = "Change console hotkey (" + Keys.toString(DevConsole.toggleKey) + ")";
            }
        });
        settingsPanel.addUIElement(buttonLabel);
        
        ModButton consoleKeyButton = new ModButton(BUTTON_X, BUTTON_Y, settingsPanel, (me) -> {
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
        settingsPanel.addUIElement(consoleKeyButton);
        
        ModLabeledToggleButton enableConsole = new ModLabeledToggleButton("Enable dev console",
        		BUTTON_ENABLE_X, BUTTON_ENABLE_Y, Settings.CREAM_COLOR, FontHelper.charDescFont,
        		DevConsole.enabled, settingsPanel, (label) -> {}, (button) -> {
        			DevConsole.enabled = button.enabled;
        			BaseMod.maybeSetBoolean("console-enabled", button.enabled);
        		});
        settingsPanel.addUIElement(enableConsole);
        
        Texture badgeTexture = new Texture(Gdx.files.internal("img/BaseModBadge.png"));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
	}
	
}