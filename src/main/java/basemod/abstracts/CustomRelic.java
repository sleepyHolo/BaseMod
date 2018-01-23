package basemod.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class CustomRelic extends AbstractRelic {
    public CustomRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, "", tier, sfx);
        setTexture(texture);
    }
    
    public void setTexture(Texture t) {
        img = t;
        largeImg = t;
        outlineImg = t;
    }
}