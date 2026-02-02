import java.sql.SQLException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        // NOTE: change credentials as appropriate
        String jdbcUrl = "jdbc:mariadb://localhost:3306/game_journal_db";
        String user = "game_journal_db_admin";
        String pass = "very_secure_password";

        try (GameDao dao = new GameDao(jdbcUrl, user, pass)) {
            System.out.println("Connected.");

            // --- Read phase ---
            System.out.println("Reading tables... ");
            for (Act o : dao.listActs()) System.out.println(o);
            for (Run o : dao.listRuns()) System.out.println(o);
            for (Tribe o : dao.listTribes()) System.out.println(o);
            for (Sigil o : dao.listSigils()) System.out.println(o);
            for (Card o : dao.listCards()) System.out.println(o);
            for (Item o : dao.listItems()) System.out.println(o);
            for (Character o : dao.listCharacters()) System.out.println(o);
            for (EncounterType o : dao.listEncounterTypes()) System.out.println(o);
            for (Puzzle o : dao.listPuzzles()) System.out.println(o);


            // --- Create phase ---
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

            // Create a Sigil
            Sigil sigil = new Sigil();
            sigil.setName("Test Sigil");
            sigil.setDescription("desc");
            sigil.setDiscoveredInRun(run);
            dao.createSigil(sigil);
            dao.addSigilToAct(sigil.getId(), act.getId());
            System.out.println("Created Sigil: " + sigil);

            // Create a Card linked to the run, tribe, act, and sigil
            Card card = new Card("Test Card");
            card.setNarration("Card narration");
            card.setCost("1G");
            card.setPower(2);
            card.setHealth(2);
            card.setDiscoveredInRun(run);
            card.setTribes(Arrays.asList(tribe));
            card.setAppearsInActs(Arrays.asList(act));
            card.setSigils(Arrays.asList(sigil));
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
            Character character = new Character();
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

            // Create a Puzzle
            Puzzle puzzle = new Puzzle("Test Puzzle", "notes", "reward", act, run);
            dao.createPuzzle(puzzle);
            System.out.println("Created Puzzle: " + puzzle);

            // --- Update phase ---
            act.setDescription("Updated description");
            dao.updateAct(act);
            System.out.println("Updated Act: " + dao.getActById(act.getId()));

            run.setNotes("Updated notes");
            dao.updateRun(run);
            System.out.println("Updated Run: " + dao.getRunById(run.getId()));

            tribe.setNotes("Updated notes");
            dao.updateTribe(tribe);
            System.out.println("Updated Tribe: " + dao.getTribeById(tribe.getId()));

            sigil.setNotes("Updated notes");
            dao.updateSigil(sigil);
            System.out.println("Updated Sigil: " + dao.getSigilById(sigil.getId()));

            card.setCost("2G");
            card.setSigils(Arrays.asList());
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

            puzzle.setReward("Updated reward");
            dao.updatePuzzle(puzzle);
            System.out.println("Updated Puzzle: " + dao.getPuzzleById(puzzle.getId()));

            // --- Delete phase (clean up) ---
            dao.deletePuzzle(puzzle.getId());
            dao.deleteEncounterType(enc.getId());
            dao.deleteCharacter(character.getId());
            dao.deleteItem(item.getId());
            dao.deleteCard(card.getId());
            dao.deleteSigil(sigil.getId());
            dao.deleteTribe(tribe.getId());
            dao.deleteRun(run.getId());
            dao.deleteAct(act.getId());

            System.out.println("Deleted created records. Test complete.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}