import java.sql.SQLException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        // NOTE: change credentials as appropriate
        String jdbcUrl = "jdbc:mariadb://localhost:3306/game_db";
        String user = "root";
        String pass = "password";

        try (GameDao dao = new GameDao(jdbcUrl, user, pass)) {
            System.out.println("Connected.");

            // Create an Act
            Act act = new Act(99, "Test Act", "Notes test act");
            dao.createAct(act);
            System.out.println("Created Act: " + act);

            // Create a Run that references the act
            Run run = new Run(42, act, "Test run notes");
            dao.createRun(run);
            System.out.println("Created Run: " + run);

            // Create a Tribe
            Tribe tribe = new Tribe("Test Tribe", "Narration", "Notes");
            dao.createTribe(tribe);
            System.out.println("Created Tribe: " + tribe);

            // Create a Card linked to the run and tribe and act
            Card card = new Card("Test Card");
            card.setNarration("Card narration");
            card.setCost("1G");
            card.setPower(2);
            card.setHealth(2);
            card.setDiscoveredInRun(run);
            card.setTribes(Arrays.asList(tribe));
            card.setAppearsInActs(Arrays.asList(act));
            dao.createCard(card);
            System.out.println("Created Card: " + card);

            // Create an Item
            Item item = new Item();
            item.setName("Test Item");
            item.setNarration("Item narr");
            item.setDiscoveredInRun(run);
            item.setAppearsInActs(Arrays.asList(act));
            dao.createItem(item);
            System.out.println("Created Item: " + item);

            // Create a Character
            com.example.model.Character character = new com.example.model.Character();
            character.setName("Test Character");
            character.setCanFight(true);
            character.setDiscoveredInRun(run);
            character.setAppearsInActs(Arrays.asList(act));
            dao.createCharacter(character);
            System.out.println("Created Character: " + character);

            // Create an EncounterType referencing the character
            EncounterType enc = new EncounterType();
            enc.setName("Test Encounter");
            enc.setBoss(false);
            enc.setCharacter(character);
            enc.setDiscoveredInRun(run);
            enc.setAppearsInActs(Arrays.asList(act));
            dao.createEncounterType(enc);
            System.out.println("Created EncounterType: " + enc);

            // Create a Sigil
            Sigil sigil = new Sigil();
            sigil.setName("Test Sigil");
            sigil.setDescription("desc");
            sigil.setDiscoveredInRun(run);
            dao.createSigil(sigil);
            dao.addSigilToAct(sigil.getId(), act.getId());
            System.out.println("Created Sigil: " + sigil);

            // Create a Puzzle
            Puzzle puzzle = new Puzzle("Test Puzzle", "notes", "reward", act, run);
            dao.createPuzzle(puzzle);
            System.out.println("Created Puzzle: " + puzzle);

            // --- Update phase ---
            act.setDescription("Updated description");
            dao.updateAct(act);
            System.out.println("Updated Act: " + dao.getActById(act.getId()));

            card.setCost("2G");
            dao.updateCard(card);
            System.out.println("Updated Card: " + dao.getCardById(card.getId()));

            item.setNotes("Updated item notes");
            dao.updateItem(item);
            System.out.println("Updated Item: " + dao.getItemById(item.getId()));

            character.setNotes("Updated char notes");
            dao.updateCharacter(character);
            System.out.println("Updated Character: " + dao.getCharacterById(character.getId()));

            enc.setBoss(true);
            dao.updateEncounterType(enc);
            System.out.println("Updated EncounterType: " + dao.getEncounterTypeById(enc.getId()));

            // --- Delete phase (clean up) ---
            dao.deletePuzzle(puzzle.getId());
            dao.deleteSigil(sigil.getId());
            dao.deleteEncounterType(enc.getId());
            dao.deleteCharacter(character.getId());
            dao.deleteItem(item.getId());
            dao.deleteCard(card.getId());
            dao.deleteTribe(tribe.getId());
            dao.deleteRun(run.getId());
            dao.deleteAct(act.getId());

            System.out.println("Deleted created records. Test complete.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}