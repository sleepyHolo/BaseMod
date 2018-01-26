# BaseMod #
BaseMod provides a number of hooks and a console.

Currently supported version: `[EARLY_ACCESS_013]`

## Requirements ##
#### General Use ####
* Java 8+

#### Development ####
* Java 8
* Maven
* CFR 124 (run this with Java 8, doesn't work well with 9)

## Building ##
1. Decompile the `com.megacrit.cardcrawl` package from `desktop-1.0.jar`
2. Put the decompiled files in `../_lib/decompiled` relative to the repo
3. Run `mvn package`

## Installation ##
1. Copy `target/BaseMod.jar` to your ModTheSpire mods directory.

## Console ##
Default hotkey is `` ` ``, can be changed from BaseMod's settings screen.
* `deck add [id] {upgrades}` add card to deck (optional: integer # of upgrades)
* `deck r [id]` remove card from deck
* `draw [num]` draw cards
* `energy add [amount]` gain energy
* `energy inf` toggles infinite energy
* `energy r [amount]` lose energy
* `gold add [amount]` gain gold
* `gold r [amount]` lose gold
* `hand add [id] {upgrades}` add card to hand with (optional: integer # of upgrades)
* `hand r all` exhaust entire hand
* `hand r [id]` exhaust card from hand
* `info` toggle Settings.isInfo
* `kill all` kills all enemies in the current combat
* `relic add [id]` generate relic
* `relic list` logs all relic pools
* `relic r [id]` lose relic

## For Modders ##
### Hooks ###
#### Subscription handling ####
* `BaseMod.subscribeTo...(this)`
* `BaseMod.unsubscribeFrom...(this)`

#### Subscriptions ####
Implement the appropriate interface (ex. `basemod.interfaces.PostInitializeSubscriber`)
* `void receivePostDraw(AbstractCard)` - After a card is drawn
* `void receivePostEnergyRecharge()` - At the start of every player turn, after energy has recharged
* `void receivePostInitialize()` - One time only, at the end of `CardCrawlGame.initialize()`
* `boolean receivePreMonsterTurn(AbstractMonster)` - Before each monster takes its turn. Returning false will skip the monsters turn.
* `void receiveRender(SpriteBatch)` - Under tips and the cursor, above everything else
* `void receivePostRender(SpriteBatch)` - Above everything
* `void receivePreStartGame()` - When starting a new game, before generating the dungeon
* `void receivePreUpdate()` - Immediately after input is handled
* `void receivePostUpdate()` - Immediately before input is disposed

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

#### v1.2.2 ####
* Add `AbstractDungeon` cleanup to diff, nothing changed yet.
* Add `relic list` command
* Fix crash when attempting to `deck add` an invalid card id
* Add upgrade support to `deck add` and `hand add` 
* Add `PreMonsterTurnSubscriber` interface and related code

#### v1.2.3 ####
* Add `PostCampfireSubscriber` interface and related code
* Add proper support for `IntangiblePower` to be applied to players

#### v1.3.0 ####
* Update to support `[EARLY_ACCESS_13]`

#### v1.3.1 ####
* Fix bug that prevented the `deck r` command from working

#### v1.3.1a ####
* Pull in changes from `[EARLY_ACCESS_13]` hotfix 2

#### v1.3.2 ####
* A lot of behind the scenes work on settings pages. 
* MODDERS: You shouldn't try to build complex settings pages right now, its probably going to get significantly cleaned up and reworked to be more simple soon.

#### v1.3.3 ####
* Fix non-id parameters on commands being cases sensitive
* Change backspace behavior on console
* Fix scaling for new settings panel elements introduced in v1.3.2

## Contributors ##
* t-larson - Original author
* FlipskiZ - `hand` command, bug fixes
