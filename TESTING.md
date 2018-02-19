#Testing

Since the game is in early access we need a somewhat robust way to figure out what parts of BaseMod break each time the game is updated. Currently that way is **TestMod**.

**TestMod** is a mod bundled with BaseMod that uses all the functionality of BaseMod such that it can be run against any new version of BaseMod to ensure there are no crashes and that BaseMod's features behave as intended.

##Testing
1. Run the game with **only** `BaseMod` and `TestMod` installed.
2. Play through part or most of `Act 1` and quit the game.
3. If the game crashes you know that there has been an issue.
4. If the game does not crash it is likely that there is not an issue.
5. Take a look at `SlayTheSpire.log` and `testmod.log` after the game quits (either crash or intentional) to confirm that there weren't any issues.

##Things to Watch for While Playing the Game
1. There should be a custom class in the game.
2. Testing playing as the custom class and as a default class is required to make sure all parts of BaseMod are currently functional.
3. This will test out the custom class, cards, relics, and localization strings.

##Currently Tested Hooks
1. `receivePostCreateStartingDeck`
2. `receivePostCreateStartingRelics`
3. `receiveStartGame`
4. `receivePostInitialize`
5. `receiveEditStrings`
6. `receiveEditRelics`
7. `receiveEditCharacters`
8. `receiveEditCards`