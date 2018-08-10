package basemod.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class CustomRelic extends AbstractRelic {
    public CustomRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, "", tier, sfx);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setTexture(texture);
    }
    
    public CustomRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, "", tier, sfx);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        outline.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setTextureOutline(texture, outline);
    }
    
    public void setTexture(Texture t) {
        img = t;
        largeImg = t;
        outlineImg = t;
    }
    
    public void setTextureOutline(Texture t, Texture o) {
        img = t;
        largeImg = t;
        outlineImg = o;
    }
}