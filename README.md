# BaseMod #
BaseMod provides a number of hooks and a console.

## Requirements ##
#### General Use ####
* Java 8+

#### Development ####
* Java 8+
* Maven

## Building ##
1. Run `mvn package`

## Installation ##
1. Copy `target/BaseMod.jar` to your ModTheSpire mods directory.

## Console ##
Default hotkey is `` ` ``, can be changed from BaseMod's settings screen.
* `deck add [id]` add card to deck
* `deck r [id]` remove card from deck
* `draw [num]` draw cards
* `energy add [amount]` gain energy
* `energy inf` toggles infinite energy
* `energy r [amount]` lose energy
* `gold add [amount]` gain gold
* `gold r [amount]` lose gold
* `hand add [id]` add card to hand
* `hand r all` exhaust entire hand
* `hand r [id]` exhaust card from hand
* `info` toggle Settings.isInfo
* `kill all` kills all enemies in the current combat
* `relic add [id]` generate relic
* `relic r [id]` lose relic

## For Modders ##
### Hooks ###
#### Subscription handling ####
* `BaseMod.subscribeTo...(this)`
* `BaseMod.unsubscribeFrom...(this)`

#### Subscriptions ####
Implement the appropriate interface (ex. `basemod.interfaces.PostInitializeSubscriber`)
* `receivePostDraw(AbstractCard)` - After a card is drawn
* `receivePostEnergyRecharge()` - At the start of every player turn, after energy has recharged
* `receivePostInitialize()` - One time only, at the end of `CardCrawlGame.initialize()`
* `receiveRender(SpriteBatch)` - Under tips and the cursor, above everything else
* `receivePostRender(SpriteBatch)` - Above everything
* `receivePreUpdate()` - Immediately after input is handled
* `receivePostUpdate()` - Immediately before input is disposed

### Mod Badges ###
32x32 images that display under the title on the main menu. Clicking one opens that mods settings menu.
* `BaseMod.registerModBadge(Texture texture, String modName, String author, String description, ModPanel settingsPanel)`
* `ModPanel.addButton(float x, float y, Consumer<ModButton> clickEvent)`
* `ModPanel.addLabel(String text, float x, float y, Consumer<ModLabel> updateEvent)`

Example of setting up a basic mod badge with settings panel:

```java
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
registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
```

### Custom Relics ###
* `CustomRelic(String id, Texture texture, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx)`
* `BaseMod.loadCustomRelicStrings(String json)`

## Changelog ##
#### v1.0.0 ####
* Initial release

#### v1.0.1 ####
* Scale console by Settings.scale
* Prevent game hotkeys from activating while console is visible

#### v1.1.0 ####
* Add mod badges
* Add initial support for mod settings screens
* Add `relic` console command
* Add option to change console keybind on BaseMod settings screen

#### v1.1.1 #####
* Scale mod badges by Settings.scale
* Scale mod settings screens by Settings.scale

#### v1.1.2 #####
* Fix bug with IDs which contain spaces (FlipskiZ)
* Add `card` console command (FlipskiZ)
* Add `kill all` console command

#### v1.1.3 #####
* Initial support for each mod badge being tied to its own settings panel

#### v1.1.4 #####
* Add `gold` command
* Add `energy` command
* Remove bundled font and use one from the base game instead

#### v1.1.5 ####
* Add `energy inf` command
* Add `PostEnergyRechargeSubscriber` interface and related code

#### v1.2.0 ####
* Add `PostDrawSubscriber` interface and related code
* Add `CustomRelic` extension of AbstractRelic
* Add support for loading custom RelicStrings
* Fix a bug that prevented the character `D` from being input into the console
* Rename `card` command to `hand`
* Add `hand r all` command
* Add `deck` command 
* Add `draw` command
* Add `float BaseMod.pathDensityMultiplier` property which can be used to modify map generation

#### v1.2.1 ####
* Add `PreStartGameSubscriber` interface and related code

## Contributors ##
* t-larson - Original author
* FlipskiZ - `hand` command, bug fixes
