package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 This Roguelike Card Game is a survival-based
 adventure where players progress using randomly generated cards.
 Each playthrough is unique, letting players customize their character,
 collect attribute-enhancing cards, and face a variety of enemies with
 different abilities. Strategy and adaptability are key to surviving
 each encounter.
 **/
public class CardRogue extends Application {

    // ========== GAME STATE ==========
    private Player player;           // The player character
    private Enemy enemy;             // The current enemy
    private GameManager gameManager; // Manages game logic
    private UIManager uiManager;     // Manages the user interface
    private Stage primaryStage;      // Main window
    private String playerName;       // Logged in player name

    /**
     * Main entry point for JavaFX application
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("CARD ROGUE");

        // Show login screen first
        showLoginScreen();
    }

    /**
     * Display the login screen
     */
    private void showLoginScreen() {
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: #1a1a1a;");
        loginBox.setPadding(new Insets(50));

        Label titleLabel = new Label("⋆༺\uD80CCARD\uDDA9⚔\uD80CROGUE\uDDAA༻⋆");
        titleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 44));
        titleLabel.setTextFill(Color.ORANGE);

        Label subtitleLabel = new Label("꧁⎝ \uD80C\uDDA9༺The Dungeon Awaits...༻\uD80C\uDDAA ⎠꧂ ");
        subtitleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        subtitleLabel.setTextFill(Color.WHITE);

        // Login form
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(400);
        formBox.setStyle("-fx-border-color: orange; -fx-border-width: 3; -fx-background-color: #2b2b2b; -fx-padding: 30;");

        Label nameLabel = new Label("⋆༺\uD80CWhat...\uDDA9Be..☠\uFE0Ethy..\uFE0EName...\uD80C\uDDAA༻⋆");
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setPromptText("Hero Name");
        nameField.setFont(Font.font("Courier New", 14));
        nameField.setStyle("-fx-background-color: #0a0a0a; -fx-text-fill: white; -fx-prompt-text-fill: gray;");
        nameField.setMaxWidth(300);

        Button loginButton = new Button("꧁\uD80C\uDE88\uD808\uDD9CEnter The Dungeon\uDCF8\uD808\uDD9C\uD80C\uDE89꧂");
        loginButton.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: #006400; -fx-text-fill: white; -fx-padding: 10 30;");
        loginButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                playerName = name;
                showMainMenu();
            }
        });

        // Press Enter to login
        nameField.setOnAction(e -> loginButton.fire());

        formBox.getChildren().addAll(nameLabel, nameField, loginButton);
        loginBox.getChildren().addAll(titleLabel, subtitleLabel, formBox);

        Scene loginScene = new Scene(loginBox, 800, 600);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Focus on text field
        nameField.requestFocus();
    }

    /**
     * Display the main menu
     */
    private void showMainMenu() {
        VBox menuBox = new VBox(25);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: #1a1a1a;");
        menuBox.setPadding(new Insets(50));

        Label welcomeLabel = new Label(".˳·˖✶\uD80C Ready..\uDDA9" + "\uD80CTo DIE\uDC7A\uD80C\uDDAA✶˖·˳. " + playerName + "?");
        welcomeLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.ORANGE);

        Label titleLabel = new Label("\uD83C\uDC22\uD83C\uDC23\uD83C\uDC26\uD83C\uDC24");
        titleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.WHITE);

        // Menu buttons
        Button newGameButton = createMenuButton("⋆༺\uD80C\uDDA9Enter⚔Dungeon\uD80C\uDDAA༻⋆", "#006400");
        newGameButton.setOnAction(e -> startNewGame());

        Button howToPlayButton = createMenuButton("⋆༺\uD80C\uDDA9Prepare\uD80C\uDDAA༻⋆", "#0066cc");
        howToPlayButton.setOnAction(e -> showHowToPlay());

        Button exitButton = createMenuButton("Scared...\uD80C\uDC79‿\uD80C\uDC79 ", "#8B0000");
        exitButton.setOnAction(e -> primaryStage.close());

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(newGameButton, howToPlayButton, exitButton);

        menuBox.getChildren().addAll(welcomeLabel, titleLabel, buttonBox);

        Scene menuScene = new Scene(menuBox, 800, 600);
        primaryStage.setScene(menuScene);
    }

    /**
     * Create a styled menu button
     */
    private Button createMenuButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-padding: 15 50; -fx-cursor: hand;");
        button.setMinWidth(300);

        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: yellow; -fx-padding: 15 50; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-padding: 15 50; -fx-cursor: hand;"));

        return button;
    }

    /**
     * Show how to play instructions
     */
    private void showHowToPlay() {
        VBox instructionsBox = new VBox(15);
        instructionsBox.setAlignment(Pos.TOP_CENTER);
        instructionsBox.setStyle("-fx-background-color: #1a1a1a;");
        instructionsBox.setPadding(new Insets(30));

        Label titleLabel = new Label("HOW TO PLAY");
        titleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.ORANGE);

        String instructions =
                "OBJECTIVE:\n" +
                        "Defeat enemies and survive as long as possible!\n\n" +
                        "GAMEPLAY:\n" +
                        "1. Drag cards from your hand to the 3 card slots\n" +
                        "2. You can play up to 3 cards per turn\n" +
                        "3. Click 'ATTACK WITH SWORD' to execute your turn\n" +
                        "4. All played cards will be replaced with new ones\n\n" +
                        "CARD TYPES:\n" +
                        "• Health Potion - Restores your health\n" +
                        "• Mana Potion - Restores your mana\n" +
                        "• Sword/Axe - Increases your next attack damage\n" +
                        "• Fireball - Direct damage (costs mana)\n" +
                        "• Bad Helm - Reduces your life (avoid!)\n\n" +
                        "ENEMY TYPES:\n" +
                        "• Spider - Poisons you (can't use health potions for 3 turns)\n" +
                        "• Rat - Makes you dizzy (can't use mana items for 3 turns)\n" +
                        "• Bat - Special enemy with unique abilities\n\n" +
                        "STRATEGY:\n" +
                        "Stack attack cards before attacking for massive damage!\n";

        Label instructionsLabel = new Label(instructions);
        instructionsLabel.setFont(Font.font("Courier New", 14));
        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setWrapText(true);
        instructionsLabel.setMaxWidth(700);

        Button backButton = new Button("BACK TO MENU");
        backButton.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        backButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-padding: 10 30;");
        backButton.setOnAction(e -> showMainMenu());

        instructionsBox.getChildren().addAll(titleLabel, instructionsLabel, backButton);

        Scene instructionsScene = new Scene(instructionsBox, 800, 600);
        primaryStage.setScene(instructionsScene);
    }

    /**
     * Start a new game
     */
    private void startNewGame() {
        // Initialize game objects
        player = new Player();
        player.setName(playerName);
        enemy = new Enemy();
        gameManager = new GameManager(player, enemy);
        uiManager = new UIManager(gameManager, this);

        // Create the main scene
        Scene scene = new Scene(uiManager.getRoot(), 1000, 750);
        primaryStage.setScene(scene);

        // Start the first battle
        gameManager.spawnNewEnemy();
        uiManager.updateUI();
    }

    /**
     * Return to main menu
     */
    public void returnToMenu() {
        showMainMenu();
    }

    /**
     * Called when player clicks the attack button
     */
    public void onAttackButtonClicked() {
        gameManager.executePlayerTurn();
        uiManager.updateUI();
    }

    /**
     * Called when a card is played to a slot
     */
    public void onCardPlayed(int handIndex, int slotIndex) {
        gameManager.playCardToSlot(handIndex, slotIndex);
        uiManager.updateUI();
    }

    /**
     * Called when a card is removed from a slot
     */
    public void onCardRemoved(int slotIndex) {
        gameManager.removeCardFromSlot(slotIndex);
        uiManager.updateUI();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// ========== PLAYER CLASS ==========
/**
 * Represents the player character with stats and abilities
 */
class Player {
    private String name;
    private int health;
    private final int maxHealth;
    private final int baseAttack;
    private int attackBonus;
    private int mana;
    private final int maxMana;
    private int poisonTurns;
    private int dizzinessTurns;

    public Player() {
        this.name = "Hero";
        this.maxHealth = 50;
        this.health = maxHealth;
        this.baseAttack = 1;
        this.attackBonus = 0;
        this.maxMana = 50;
        this.mana = maxMana;
        this.poisonTurns = 0;
        this.dizzinessTurns = 0;
    }

    // Getters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getTotalAttack() { return baseAttack + attackBonus; }
    public int getAttackBonus() { return attackBonus; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public boolean isPoisoned() { return poisonTurns > 0; }
    public boolean isDizzy() { return dizzinessTurns > 0; }
    public int getPoisonTurns() { return poisonTurns; }
    public int getDizzinessTurns() { return dizzinessTurns; }

    // Actions
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    public void addAttackBonus(int amount) {
        attackBonus += amount;
    }

    public void resetAttackBonus() {
        attackBonus = 0;
    }

    public boolean canSpendMana(int cost) {
        return mana >= cost;
    }

    public void spendMana(int cost) {
        mana -= cost;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void applyPoison() {
        poisonTurns = 3;
    }

    public void applyDizziness() {
        dizzinessTurns = 3;
    }

    public void decreaseStatusEffects() {
        if (poisonTurns > 0) poisonTurns--;
        if (dizzinessTurns > 0) dizzinessTurns--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}

// ========== ENEMY CLASS ==========
/**
 * Represents an enemy with stats and special abilities
 */
class Enemy {
    private String type;
    private int health;
    private int maxHealth;
    private int attack;

    public Enemy() {
        // Default values, will be set when spawning
        this.type = "Spider";
        this.health = 3;
        this.maxHealth = 3;
        this.attack = 2;
    }

    // Getters
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttack() { return attack; }

    // Actions
    public void spawn(String enemyType, int hp, int atk) {
        this.type = enemyType;
        this.health = hp;
        this.maxHealth = hp;
        this.attack = atk;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }
}

// ========== ITEM CARD CLASS ==========
/**
 * Represents an item card that can be played
 */
class ItemCard {
    private final String type;
    private final String name;
    private final String description;
    private final int value;
    private final int manaCost;
    private final int moveValue; // How many orbs to move (1-4)

    public ItemCard(String type, String name, String description, int value, int manaCost, int moveValue) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.value = value;
        this.manaCost = manaCost;
        this.moveValue = moveValue;
    }

    // Getters
    public String getType() { return type; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getValue() { return value; }
    public int getManaCost() { return manaCost; }
    public int getMoveValue() { return moveValue; }
}

// ========== CARD FACTORY CLASS ==========
/**
 * Creates random item cards
 */
class CardFactory {
    private final Random random;

    public CardFactory() {
        this.random = new Random();
    }

    public ItemCard createRandomCard() {
        String[] types = {"HEALTH_POTION", "MANA_POTION", "SWORD", "AXE", "FIREBALL", "BAD_HELM"};
        String type = types[random.nextInt(types.length)];

        // Random move value 1-4
        int moveValue = random.nextInt(4) + 1;

        switch (type) {
            case "HEALTH_POTION":
                int healAmount = random.nextInt(11) + 10; // 10-20
                return new ItemCard(type, "Pot༒of༒Life", "Heal +" + healAmount + " | Move:" + moveValue, healAmount, 0, moveValue);

            case "MANA_POTION":
                int manaAmount = random.nextInt(11) + 10; // 10-20
                return new ItemCard(type, "Elixir❦", "Mana +" + manaAmount + " | Move:" + moveValue, manaAmount, 0, moveValue);

            case "SWORD":
                int swordBonus = random.nextInt(3) + 1; // 1-3
                return new ItemCard(type, "GreatBlade\uD83D\uDDE1", "Next Attack +" + swordBonus + " | Move:" + moveValue, swordBonus, 0, moveValue);

            case "AXE":
                int axeBonus = random.nextInt(4) + 2; // 2-5
                return new ItemCard(type, "Waraxe\uD80C\uDF0F", "Next Attack +" + axeBonus + " | Move:" + moveValue, axeBonus, 0, moveValue);

            case "FIREBALL":
                int damage = random.nextInt(18) + 3; // 3-20
                int cost = random.nextInt(18) + 3; // 3-20
                return new ItemCard(type, "Ball\uD83D\uDF38o'\uD83D\uDF38Fire", "DMG:" + damage + " Cost:" + cost + " | Move:" + moveValue, damage, cost, moveValue);

            case "BAD_HELM":
                int healthLoss = random.nextInt(3) + 1; // 1-3
                return new ItemCard(type, "Bad\uD81A\uDD0DHelm", "HP -" + healthLoss + " | Move:" + moveValue, healthLoss, 0, moveValue);

            default:
                return new ItemCard("SWORD", "Sword", "Attack +1 | Move:1", 1, 0, 1);
        }
    }
}

// ========== GAME MANAGER CLASS ==========
/**
 * Manages all game logic including combat, card effects, and turn order
 */
class GameManager {
    private final Player player;
    private final Enemy enemy;
    private final CardFactory cardFactory;
    private final List<ItemCard> playerHand;
    private final ItemCard[] playedCards;
    private int killCount;
    private int stage;
    private boolean gameOver;
    private final Random random;
    private int currentOrbPosition; // 0-11 for the 12 orbs
    private boolean canAttackNextTurn; // Flag for skull effect

    // Orb sequence: GOLD(0), BLUE(1,2), SKULL(3,4), BLUE(5), SKULL(6), BLUE(7,8), SKULL(9,10), BLUE(11)
    private static final String[] ORB_SEQUENCE = {
            "GOLD", "BLUE", "BLUE", "SKULL", "SKULL", "BLUE",
            "SKULL", "BLUE", "BLUE", "SKULL", "SKULL", "BLUE"
    };

    public GameManager(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.cardFactory = new CardFactory();
        this.playerHand = new ArrayList<>();
        this.playedCards = new ItemCard[3];
        this.killCount = 0;
        this.stage = 1;
        this.gameOver = false;
        this.random = new Random();
        this.currentOrbPosition = 0; // Start at GOLD orb
        this.canAttackNextTurn = true;

        // Initialize player hand with 5 cards
        for (int i = 0; i < 5; i++) {
            playerHand.add(cardFactory.createRandomCard());
        }
    }

    // Getters
    public Player getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }
    public List<ItemCard> getPlayerHand() { return playerHand; }
    public ItemCard[] getPlayedCards() { return playedCards; }
    public int getKillCount() { return killCount; }
    public boolean isGameOver() { return gameOver; }
    public int getCurrentOrbPosition() { return currentOrbPosition; }
    public String[] getOrbSequence() { return ORB_SEQUENCE; }
    public boolean canAttack() { return canAttackNextTurn; }

    /**
     * Spawns a new enemy with scaled stats
     */
    public void spawnNewEnemy() {
        String[] enemyTypes = {"Spider", "Rat", "Bat"};
        String type = enemyTypes[random.nextInt(enemyTypes.length)];

        // Scale health and attack based on stage
        int healthMin = 2 + (stage - 1) * 2;
        int healthMax = 5 + (stage - 1) * 3;
        int health = random.nextInt(healthMax - healthMin + 1) + healthMin;

        int attackMin = 1 + (stage - 1);
        int attackMax = 3 + (stage - 1) * 2;
        int attack = random.nextInt(attackMax - attackMin + 1) + attackMin;

        enemy.spawn(type, health, attack);
        stage++;
    }

    /**
     * Play a card from hand to a slot
     */
    public void playCardToSlot(int handIndex, int slotIndex) {
        if (handIndex >= 0 && handIndex < playerHand.size() && slotIndex >= 0 && slotIndex < 3) {
            if (playerHand.get(handIndex) != null && playedCards[slotIndex] == null) {
                playedCards[slotIndex] = playerHand.get(handIndex);
                playerHand.set(handIndex, null);
            }
        }
    }

    /**
     * Remove a card from a slot back to hand
     */
    public void removeCardFromSlot(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < 3 && playedCards[slotIndex] != null) {
            // Find first empty slot in hand
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i) == null) {
                    playerHand.set(i, playedCards[slotIndex]);
                    playedCards[slotIndex] = null;
                    break;
                }
            }
        }
    }

    /**
     * Execute the player's turn (apply card effects and attack)
     */
    public void executePlayerTurn() {
        if (gameOver) return;

        // Check if player can attack
        if (!canAttackNextTurn) {
            canAttackNextTurn = true; // Reset for next turn

            // Still apply card effects and move orbs, but no attack
            for (ItemCard card : playedCards) {
                if (card != null) {
                    applyCardEffect(card);
                    // Move orb based on card's move value
                    moveOrb(card.getMoveValue());
                }
            }

            // Clear played cards and refresh hand
            clearAndRefreshCards();

            // Enemy still attacks
            executeEnemyTurn();
            return;
        }

        // Apply all played card effects and move orbs
        for (ItemCard card : playedCards) {
            if (card != null) {
                applyCardEffect(card);
                // Move orb based on card's move value
                moveOrb(card.getMoveValue());
            }
        }

        // Attack the enemy
        int totalDamage = player.getTotalAttack();
        enemy.takeDamage(totalDamage);

        // Reset attack bonus after attacking
        player.resetAttackBonus();

        // Clear played cards and refresh hand
        clearAndRefreshCards();

        // Check if enemy is defeated
        if (!enemy.isAlive()) {
            killCount++;
            // Reset orb to GOLD position
            currentOrbPosition = 0;
            spawnNewEnemy();
        } else {
            // Enemy's turn
            executeEnemyTurn();
        }
    }

    /**
     * Move the orb position and apply skull effects
     */
    private void moveOrb(int steps) {
        currentOrbPosition = (currentOrbPosition + steps) % 12;

        // Check if landed on skull
        if (ORB_SEQUENCE[currentOrbPosition].equals("SKULL")) {
            applySkullEffect();
        }
    }

    /**
     * Apply random skull effect
     */
    private void applySkullEffect() {
        int effect = random.nextInt(3); // 0, 1, or 2

        switch (effect) {
            case 0: // Lose 2 health
                player.takeDamage(2);
                break;
            case 1: // Lose 2 mana
                player.spendMana(Math.min(2, player.getMana()));
                break;
            case 2: // Can't attack next turn
                canAttackNextTurn = false;
                break;
        }
    }

    /**
     * Clear played cards and refresh hand
     */
    private void clearAndRefreshCards() {
        // Clear played cards
        for (int i = 0; i < 3; i++) {
            playedCards[i] = null;
        }

        // Refresh all cards in hand
        for (int i = 0; i < playerHand.size(); i++) {
            playerHand.set(i, cardFactory.createRandomCard());
        }
    }

    /**
     * Apply the effect of a card
     */
    private void applyCardEffect(ItemCard card) {
        switch (card.getType()) {
            case "HEALTH_POTION":
                if (!player.isPoisoned()) {
                    player.heal(card.getValue());
                }
                break;

            case "MANA_POTION":
                if (!player.isDizzy()) {
                    player.restoreMana(card.getValue());
                }
                break;

            case "SWORD":
            case "AXE":
                player.addAttackBonus(card.getValue());
                break;

            case "FIREBALL":
                if (!player.isDizzy() && player.canSpendMana(card.getManaCost())) {
                    player.spendMana(card.getManaCost());
                    enemy.takeDamage(card.getValue());
                }
                break;

            case "BAD_HELM":
                player.takeDamage(card.getValue());
                break;
        }
    }

    /**
     * Execute the enemy's turn (attack player and apply status effects)
     */
    private void executeEnemyTurn() {
        player.takeDamage(enemy.getAttack());

        // Apply status effects based on enemy type
        if (enemy.getType().equals("Spider")) {
            player.applyPoison();
        } else if (enemy.getType().equals("Rat")) {
            player.applyDizziness();
        }

        // Decrease status effect timers
        player.decreaseStatusEffects();

        // Check if player died
        if (!player.isAlive()) {
            gameOver = true;
        }
    }
}

// ========== UI MANAGER CLASS ==========
/**
 * Manages all user interface elements
 */
class UIManager {
    private final GameManager gameManager;
    private final CardRogue gameApp;
    private final BorderPane root;

    // UI Components
    private Label playerHealthLabel;
    private Label playerAttackLabel;
    private Label playerManaLabel;
    private Label enemyNameLabel;
    private Label enemyHealthLabel;
    private Label enemyAttackLabel;
    private Label killCountLabel;
    private Label statusLabel;
    private HBox itemCardsBox;
    private HBox playedCardsBox;
    private Button attackButton;

    public UIManager(GameManager gameManager, CardRogue gameApp) {
        this.gameManager = gameManager;
        this.gameApp = gameApp;
        this.root = new BorderPane();
        root.setStyle("-fx-background-color: #2b2b2b;");

        buildUI();
    }

    public BorderPane getRoot() {
        return root;
    }

    /**
     * Build the entire user interface
     */
    private void buildUI() {
        root.setTop(createTopSection());
        root.setCenter(createCenterSection());
        root.setBottom(createBottomSection());
    }

    /**
     * Create the top section with player and enemy stats
     */
    private HBox createTopSection() {
        HBox topBox = new HBox(50);
        topBox.setPadding(new Insets(20));
        topBox.setAlignment(Pos.CENTER);

        VBox playerBox = createPlayerStatsBox();
        VBox centerBox = createCenterInfoBox();
        VBox enemyBox = createEnemyStatsBox();

        topBox.getChildren().addAll(playerBox, centerBox, enemyBox);
        return topBox;
    }

    private VBox createPlayerStatsBox() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-border-color: orange; -fx-border-width: 3; -fx-background-color: #1a1a1a; -fx-padding: 15;");
        box.setMinWidth(250);

        Label titleLabel = new Label(gameManager.getPlayer().getName().toUpperCase());
        titleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.ORANGE);

        playerHealthLabel = createStatLabel("❤ Health: 50/50");
        playerAttackLabel = createStatLabel("⚔ Attack: 1");
        playerManaLabel = createStatLabel("🔮 Mana: 50/50");

        box.getChildren().addAll(titleLabel, playerHealthLabel, playerAttackLabel, playerManaLabel);
        return box;
    }

    private VBox createCenterInfoBox() {
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);

        killCountLabel = new Label("Kills: 0");
        killCountLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        killCountLabel.setTextFill(Color.WHITE);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Courier New", 14));
        statusLabel.setTextFill(Color.YELLOW);
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(200);
        statusLabel.setAlignment(Pos.CENTER);

        centerBox.getChildren().addAll(killCountLabel, statusLabel);
        return centerBox;
    }

    private VBox createEnemyStatsBox() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-border-color: orange; -fx-border-width: 3; -fx-background-color: #1a1a1a; -fx-padding: 15;");
        box.setMinWidth(250);

        Label titleLabel = new Label("\uD83D\uDE08 FOE \uD83D\uDE08");
        titleLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.ORANGE);

        enemyNameLabel = createStatLabel("Spider");
        enemyHealthLabel = createStatLabel("❤ Health: 0/0");
        enemyAttackLabel = createStatLabel("⚔ Attack: 0");

        box.getChildren().addAll(titleLabel, enemyNameLabel, enemyHealthLabel, enemyAttackLabel);
        return box;
    }

    private Label createStatLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Create the center section with card slots and attack button
     */
    private VBox createCenterSection() {
        VBox center = new VBox(20);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20));

        playedCardsBox = new HBox(15);
        playedCardsBox.setAlignment(Pos.CENTER);
        playedCardsBox.setPrefHeight(180);

        for (int i = 0; i < 3; i++) {
            playedCardsBox.getChildren().add(createEmptyCardSlot(i));
        }

        attackButton = new Button("⚔");
        attackButton.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
        attackButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-padding: 15 30;");
        attackButton.setOnAction(e -> gameApp.onAttackButtonClicked());

        Button menuButton = new Button("⚙");
        menuButton.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        menuButton.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-padding: 10 20;");
        menuButton.setOnAction(e -> gameApp.returnToMenu());

        center.getChildren().addAll(playedCardsBox, attackButton, menuButton);
        return center;
    }

    private VBox createEmptyCardSlot(int slotIndex) {
        VBox slot = new VBox();
        slot.setAlignment(Pos.CENTER);
        slot.setStyle("-fx-border-color: orange; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #0a0a0a; -fx-padding: 10;");
        slot.setMinWidth(140);
        slot.setMinHeight(160);

        Label emptyLabel = new Label("EMPTY\nSLOT");
        emptyLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        emptyLabel.setTextFill(Color.GRAY);
        emptyLabel.setAlignment(Pos.CENTER);

        slot.getChildren().add(emptyLabel);
        setupDropTarget(slot, slotIndex);

        return slot;
    }

    /**
     * Setup drag-and-drop target for a card slot
     */
    private void setupDropTarget(VBox slot, int slotIndex) {
        slot.setOnDragOver(event -> {
            if (event.getGestureSource() != slot && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        slot.setOnDragEntered(event -> {
            if (event.getGestureSource() != slot && event.getDragboard().hasString()) {
                slot.setStyle("-fx-border-color: lime; -fx-border-width: 3; -fx-border-style: solid; -fx-background-color: #1a1a1a; -fx-padding: 10;");
            }
            event.consume();
        });

        slot.setOnDragExited(event -> {
            slot.setStyle("-fx-border-color: orange; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #0a0a0a; -fx-padding: 10;");
            event.consume();
        });

        slot.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                int cardIndex = Integer.parseInt(db.getString());
                gameApp.onCardPlayed(cardIndex, slotIndex);
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    /**
     * Create the bottom section with player's hand
     */
    private VBox createBottomSection() {
        VBox bottom = new VBox(15);
        bottom.setPadding(new Insets(20));
        bottom.setAlignment(Pos.CENTER);

        itemCardsBox = new HBox(15);
        itemCardsBox.setAlignment(Pos.CENTER);

        bottom.getChildren().addAll(itemCardsBox);
        return bottom;
    }

    /**
     * Update all UI elements to reflect current game state
     */
    public void updateUI() {
        updatePlayerStats();
        updateEnemyStats();
        updateKillCount();
        updateStatusEffects();
        updatePlayerHand();
        updatePlayedCards();

        if (gameManager.isGameOver()) {
            attackButton.setDisable(true);
            statusLabel.setText("GAME OVER! Final Kills: " + gameManager.getKillCount());
            statusLabel.setTextFill(Color.RED);
        }
    }

    private void updatePlayerStats() {
        Player player = gameManager.getPlayer();
        playerHealthLabel.setText("❤ Health: " + player.getHealth() + "/" + player.getMaxHealth());

        String attackText = "⚔ Attack: " + player.getTotalAttack();
        if (player.getAttackBonus() > 0) {
            attackText += " (+" + player.getAttackBonus() + " bonus)";
        }
        playerAttackLabel.setText(attackText);

        playerManaLabel.setText("🔮 Mana: " + player.getMana() + "/" + player.getMaxMana());

    }

    private void updateEnemyStats() {
        Enemy enemy = gameManager.getEnemy();
        enemyNameLabel.setText(enemy.getType());
        enemyHealthLabel.setText("❤ Health: " + Math.max(0, enemy.getHealth()) + "/" + enemy.getMaxHealth());
        enemyAttackLabel.setText("⚔ Attack: " + enemy.getAttack());
    }

    private void updateKillCount() {
        killCountLabel.setText("·:*¨༺ FOES♱SLAIN♱" + gameManager.getKillCount());
    }

    private void updateStatusEffects() {
        Player player = gameManager.getPlayer();
        String effects = "";

        if (player.isPoisoned()) {
            effects += "🧪 Poisoned (" + player.getPoisonTurns() + ") ";
        }
        if (player.isDizzy()) {
            effects += "💫 Dizzy (" + player.getDizzinessTurns() + ")";
        }

        statusLabel.setText(effects);
    }

    private void updatePlayerHand() {
        itemCardsBox.getChildren().clear();
        List<ItemCard> hand = gameManager.getPlayerHand();

        for (int i = 0; i < hand.size(); i++) {
            ItemCard card = hand.get(i);
            if (card != null) {
                itemCardsBox.getChildren().add(createHandCardUI(card, i));
            }
        }
    }

    private VBox createHandCardUI(ItemCard card, int index) {
        VBox cardBox = new VBox(5);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: #450303; -fx-padding: 10; -fx-cursor: hand;");
        cardBox.setMinWidth(140);
        cardBox.setMinHeight(160);

        Label nameLabel = new Label(card.getName());
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.ORANGE);
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(120);

        Label descLabel = new Label(card.getDescription());
        descLabel.setFont(Font.font("Courier New", 12));
        descLabel.setTextFill(Color.WHITE);
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(120);

        cardBox.getChildren().addAll(nameLabel, descLabel);
        setupDragSource(cardBox, index);

        return cardBox;
    }

    /**
     * Setup drag source for a card in hand
     */
    private void setupDragSource(VBox cardBox, int cardIndex) {
        cardBox.setOnDragDetected(event -> {
            Dragboard db = cardBox.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(cardIndex));
            db.setContent(content);
            event.consume();
        });
    }

    private void updatePlayedCards() {
        playedCardsBox.getChildren().clear();
        ItemCard[] played = gameManager.getPlayedCards();

        for (int i = 0; i < 3; i++) {
            if (played[i] == null) {
                playedCardsBox.getChildren().add(createEmptyCardSlot(i));
            } else {
                playedCardsBox.getChildren().add(createPlayedCardUI(played[i], i));
            }
        }
    }

    private VBox createPlayedCardUI(ItemCard card, int slotIndex) {
        VBox cardBox = new VBox(5);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setStyle("-fx-border-color: lime; -fx-border-width: 3; -fx-background-color: #1a1a1a; -fx-padding: 10;");
        cardBox.setMinWidth(140);
        cardBox.setMinHeight(160);

        Label nameLabel = new Label(card.getName());
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.LIME);
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(120);

        Label descLabel = new Label(card.getDescription());
        descLabel.setFont(Font.font("Courier New", 12));
        descLabel.setTextFill(Color.WHITE);
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(120);

        Button removeButton = new Button("REMOVE");
        removeButton.setFont(Font.font("Courier New", FontWeight.BOLD, 10));
        removeButton.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white;");
        removeButton.setOnAction(e -> gameApp.onCardRemoved(slotIndex));

        cardBox.getChildren().addAll(nameLabel, descLabel, removeButton);
        return cardBox;
    }
}