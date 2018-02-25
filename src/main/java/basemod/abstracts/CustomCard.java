package basemod.abstracts;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.ReflectionHacks;

public abstract class CustomCard extends AbstractCard {
	
	public static HashMap<String, Texture> imgMap;
	
	public static final String PORTRAIT_ENDING = "_p";
	
	public static Texture getPortraitImage(CustomCard card) {
		int endingIndex = card.textureImg.lastIndexOf(".");
		String newPath = card.textureImg.substring(0, endingIndex) + 
				PORTRAIT_ENDING + card.textureImg.substring(endingIndex); 
		System.out.println("Finding texture: " + newPath);
		Texture portraitTexture;
		try {
			portraitTexture = new Texture(newPath);
		} catch (Exception e) {
			portraitTexture = null;
		}
		return portraitTexture;
	}
	
	static {
		imgMap = new HashMap<>();
	}
	
	public String textureImg;
	
	public CustomCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, "status/beta", "status/beta", cost, rawDescription, type, color, rarity, target, cardPool);
		
		this.textureImg = img;
		loadCardImage(img);
	}
	
	// loadCardImage - copy of hack here: https://github.com/t-larson/STS-ModLoader/blob/master/modloader/CustomCard.java
	public void loadCardImage(String img) {
		Texture cardTexture;
		if (imgMap.containsKey(img)) {
			cardTexture = imgMap.get(img);
		} else {
			cardTexture = new Texture(img);
			imgMap.put(img, cardTexture);
		}
		cardTexture.setFilter(Texture.TextureFilter.Linear,  Texture.TextureFilter.Linear);
		int tw = cardTexture.getWidth();
		int th = cardTexture.getHeight();
		TextureAtlas.AtlasRegion cardImg = new AtlasRegion(cardTexture, 0, 0, tw, th);
		ReflectionHacks.setPrivateInherited(this, CustomCard.class, "portrait", cardImg);
	}
	
}
