import java.sql.*;
import java.util.*;

/**
 * GameDao - handles CRUD for Act, Run, Puzzle, Sigil, Tribe, Card, Item, Character, EncounterType
 *
 * Usage: instantiate with JDBC URL, user, password.
 */
public class GameDao implements AutoCloseable {
    private final Connection conn;

    public GameDao(String jdbcUrl, String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(jdbcUrl, user, password);
    }

    @Override
    public void close() throws SQLException { if (conn != null && !conn.isClosed()) conn.close(); }

    // -------------------- Acts --------------------
    public Act createAct(Act act) throws SQLException {
        String sql = "INSERT INTO Act (number, description, notes) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, act.getNumber());
            st.setString(2, act.getDescription());
            st.setString(3, act.getNotes());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) act.setId(rs.getInt(1));
            }
            return act;
        }
    }
    public Act getActById(int id) throws SQLException {
        String sql = "SELECT * FROM Act WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Act a = new Act();
                    a.setId(rs.getInt("id"));
                    a.setNumber(rs.getInt("number"));
                    a.setDescription(rs.getString("description"));
                    a.setNotes(rs.getString("notes"));
                    return a;
                }
            }
        }
        return null;
    }
    public List<Act> listActs() throws SQLException {
        List<Act> out = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Act")) {
            while(rs.next()){
                Act a = new Act();
                a.setId(rs.getInt("id"));
                a.setNumber(rs.getInt("number"));
                a.setDescription(rs.getString("description"));
                a.setNotes(rs.getString("notes"));
                out.add(a);
            }
        }
        return out;
    }
    public boolean updateAct(Act act) throws SQLException {
        String sql = "UPDATE Act SET number=?, description=?, notes=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, act.getNumber());
            st.setString(2, act.getDescription());
            st.setString(3, act.getNotes());
            st.setInt(4, act.getId());
            return st.executeUpdate() > 0;
        }
    }
    public boolean deleteAct(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Act WHERE id=?")) {
            st.setInt(1,id); return st.executeUpdate()>0;
        }
    }

    // -------------------- Run --------------------
    public Run createRun(Run run) throws SQLException {
        String sql = "INSERT INTO Run (number, act_id, notes) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, run.getNumber());
            if (run.getAct() != null) st.setInt(2, run.getAct().getId()); else st.setNull(2, Types.INTEGER);
            st.setString(3, run.getNotes());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) run.setId(rs.getInt(1)); }
            return run;
        }
    }
    public Run getRunById(int id) throws SQLException {
        String sql = "SELECT r.*, a.id as act_id, a.number as act_number FROM Run r LEFT JOIN Act a ON r.act_id=a.id WHERE r.id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Run r = new Run();
                    r.setId(rs.getInt("id"));
                    r.setNumber(rs.getInt("number"));
                    r.setNotes(rs.getString("notes"));
                    int actId = rs.getInt("act_id");
                    if (!rs.wasNull()) {
                        Act a = new Act();
                        a.setId(actId);
                        a.setNumber(rs.getInt("act_number"));
                        r.setAct(a);
                    }
                    return r;
                }
            }
        }
        return null;
    }
    public List<Run> listRuns() throws SQLException {
        List<Run> out = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Run")) {
            while(rs.next()){
                Run r = new Run(); r.setId(rs.getInt("id")); r.setNumber(rs.getInt("number")); r.setNotes(rs.getString("notes"));
                int actId = rs.getInt("act_id"); if(!rs.wasNull()){ Act a = new Act(); a.setId(actId); r.setAct(a); }
                out.add(r);
            }
        }
        return out;
    }
    public boolean updateRun(Run run) throws SQLException {
        String sql = "UPDATE Run SET number=?, act_id=?, notes=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, run.getNumber());
            if (run.getAct()!=null) st.setInt(2, run.getAct().getId()); else st.setNull(2, Types.INTEGER);
            st.setString(3, run.getNotes());
            st.setInt(4, run.getId());
            return st.executeUpdate()>0;
        }
    }
    public boolean deleteRun(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Run WHERE id=?")) {
            st.setInt(1,id); return st.executeUpdate()>0;
        }
    }

    // -------------------- Puzzle --------------------
    public Puzzle createPuzzle(Puzzle p) throws SQLException {
        String sql = "INSERT INTO Puzzle (name, notes, reward, act_id, discovered_in_run_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, p.getName());
            st.setString(2, p.getNotes());
            st.setString(3, p.getReward());
            if (p.getAct()!=null) st.setInt(4, p.getAct().getId()); else st.setNull(4, Types.INTEGER);
            if (p.getDiscoveredInRun()!=null) st.setInt(5, p.getDiscoveredInRun().getId()); else st.setNull(5, Types.INTEGER);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) p.setId(rs.getInt(1)); }
            return p;
        }
    }
    public Puzzle getPuzzleById(int id) throws SQLException {
        String sql = "SELECT * FROM Puzzle WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Puzzle p = new Puzzle();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setNotes(rs.getString("notes"));
                    p.setReward(rs.getString("reward"));
                    int actId = rs.getInt("act_id"); if(!rs.wasNull()){ Act a = new Act(); a.setId(actId); p.setAct(a); }
                    int runId = rs.getInt("discovered_in_run_id"); if(!rs.wasNull()){ Run r = new Run(); r.setId(runId); p.setDiscoveredInRun(r); }
                    return p;
                }
            }
        }
        return null;
    }
    public boolean updatePuzzle(Puzzle p) throws SQLException {
        String sql = "UPDATE Puzzle SET name=?, notes=?, reward=?, act_id=?, discovered_in_run_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,p.getName());
            st.setString(2,p.getNotes());
            st.setString(3,p.getReward());
            if (p.getAct()!=null) st.setInt(4,p.getAct().getId()); else st.setNull(4, Types.INTEGER);
            if (p.getDiscoveredInRun()!=null) st.setInt(5,p.getDiscoveredInRun().getId()); else st.setNull(5, Types.INTEGER);
            st.setInt(6,p.getId());
            return st.executeUpdate()>0;
        }
    }
    public boolean deletePuzzle(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Puzzle WHERE id=?")) {
            st.setInt(1,id); return st.executeUpdate()>0;
        }
    }

    // -------------------- Sigil --------------------
    public Sigil createSigil(Sigil s) throws SQLException {
        String sql = "INSERT INTO Sigil (name, description, icon, notes, discovered_in_run_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1,s.getName());
            st.setString(2,s.getDescription());
            st.setString(3,s.getIcon());
            st.setString(4,s.getNotes());
            if (s.getDiscoveredInRun()!=null) st.setInt(5, s.getDiscoveredInRun().getId()); else st.setNull(5, Types.INTEGER);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) s.setId(rs.getInt(1)); }
            // appearsInActs handled separately via addSigilToAct
            return s;
        }
    }
    public Sigil getSigilById(int id) throws SQLException {
        String sql = "SELECT * FROM Sigil WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Sigil s = new Sigil();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setDescription(rs.getString("description"));
                    s.setIcon(rs.getString("icon"));
                    s.setNotes(rs.getString("notes"));
                    int runId = rs.getInt("discovered_in_run_id"); if(!rs.wasNull()){ Run r = new Run(); r.setId(runId); s.setDiscoveredInRun(r); }
                    // fetch acts
                    s.setAppearsInActs(getActsForSigil(s.getId()));
                    return s;
                }
            }
        }
        return null;
    }
    public boolean updateSigil(Sigil s) throws SQLException {
        String sql = "UPDATE Sigil SET name=?, description=?, icon=?, notes=?, discovered_in_run_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,s.getName()); st.setString(2,s.getDescription()); st.setString(3,s.getIcon()); st.setString(4,s.getNotes());
            if (s.getDiscoveredInRun()!=null) st.setInt(5,s.getDiscoveredInRun().getId()); else st.setNull(5, Types.INTEGER);
            st.setInt(6,s.getId());
            return st.executeUpdate()>0;
        }
    }
    public boolean deleteSigil(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Sigil WHERE id=?")) {
            st.setInt(1,id); return st.executeUpdate()>0;
        }
    }
    public void addSigilToAct(int sigilId, int actId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO Sigil_Act (sigil_id, act_id) VALUES (?,?)")) {
            st.setInt(1, sigilId); st.setInt(2, actId); st.executeUpdate();
        }
    }
    public List<Act> getActsForSigil(int sigilId) throws SQLException {
        List<Act> out = new ArrayList<>();
        String sql = "SELECT a.* FROM Act a JOIN Sigil_Act sa ON a.id=sa.act_id WHERE sa.sigil_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,sigilId);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){ Act a=new Act(); a.setId(rs.getInt("id")); a.setNumber(rs.getInt("number")); a.setDescription(rs.getString("description")); a.setNotes(rs.getString("notes")); out.add(a); }
            }
        }
        return out;
    }

    // -------------------- Tribe --------------------
    public Tribe createTribe(Tribe t) throws SQLException {
        String sql = "INSERT INTO Tribe (name, narration, notes) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, t.getName());
            st.setString(2, t.getNarration());
            st.setString(3, t.getNotes());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) t.setId(rs.getInt(1)); }
            return t;
        }
    }
    public Tribe getTribeById(int id) throws SQLException {
        String sql = "SELECT * FROM Tribe WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Tribe t = new Tribe();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setNarration(rs.getString("narration"));
                    t.setNotes(rs.getString("notes"));
                    return t;
                }
            }
        }
        return null;
    }
    public boolean updateTribe(Tribe t) throws SQLException {
        String sql = "UPDATE Tribe SET name=?, narration=?, notes=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,t.getName()); st.setString(2,t.getNarration()); st.setString(3,t.getNotes()); st.setInt(4,t.getId());
            return st.executeUpdate()>0;
        }
    }
    public boolean deleteTribe(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Tribe WHERE id=?")) {
            st.setInt(1,id); return st.executeUpdate()>0;
        }
    }

    // -------------------- Card --------------------
    public Card createCard(Card c) throws SQLException {
        String sql = "INSERT INTO Card (name, narration, notes, power, health, cost, isRare, isUnique, discovered_in_run_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1,c.getName()); st.setString(2,c.getNarration()); st.setString(3,c.getNotes());
            if (c.getPower()!=null) st.setInt(4,c.getPower()); else st.setNull(4, Types.INTEGER);
            if (c.getHealth()!=null) st.setInt(5,c.getHealth()); else st.setNull(5, Types.INTEGER);
            st.setString(6,c.getCost());
            st.setInt(7, c.isRare() ? 1 : 0);
            st.setInt(8, c.isUnique() ? 1 : 0);
            if (c.getDiscoveredInRun()!=null) st.setInt(9,c.getDiscoveredInRun().getId()); else st.setNull(9, Types.INTEGER);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) c.setId(rs.getInt(1)); }
            // tribes and appearsInActs handled by helper methods
            if (c.getTribes()!=null) {
                for (Tribe t : c.getTribes()) addCardToTribe(c.getId(), t.getId());
            }
            if (c.getAppearsInActs()!=null) {
                for (Act a : c.getAppearsInActs()) addCardToAct(c.getId(), a.getId());
            }
            if (c.getSigils()!=null) {
                for (Sigil s : c.getSigils()) addSigilToCard(s.getId(),c.getId());
            }
            return c;
        }
    }
    public Card getCardById(int id) throws SQLException {
        String sql = "SELECT * FROM Card WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Card c = new Card();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setNarration(rs.getString("narration"));
                    c.setNotes(rs.getString("notes"));
                    int p = rs.getInt("power"); if(!rs.wasNull()) c.setPower(p);
                    int h = rs.getInt("health"); if(!rs.wasNull()) c.setHealth(h);
                    c.setCost(rs.getString("cost"));
                    c.setRare(rs.getInt("isRare")==1);
                    c.setUnique(rs.getInt("isUnique")==1);
                    int runId = rs.getInt("discovered_in_run_id"); if(!rs.wasNull()){ Run r = new Run(); r.setId(runId); c.setDiscoveredInRun(r); }
                    c.setTribes(getTribesForCard(id));
                    c.setAppearsInActs(getActsForCard(id));
                    c.setSigils(getSigilsForCard(id));
                    return c;
                }
            }
        }
        return null;
    }
    public boolean updateCard(Card c) throws SQLException { 
        String sql = "UPDATE Card SET name=?, narration=?, notes=?, power=?, health=?, cost=?, isRare=?, isUnique=?, discovered_in_run_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,c.getName()); st.setString(2,c.getNarration()); st.setString(3,c.getNotes());
            if (c.getPower()!=null) st.setInt(4,c.getPower()); else st.setNull(4, Types.INTEGER);
            if (c.getHealth()!=null) st.setInt(5,c.getHealth()); else st.setNull(5, Types.INTEGER);
            st.setString(6,c.getCost());
            st.setInt(7, c.isRare()?1:0);
            st.setInt(8, c.isUnique()?1:0);
            if (c.getDiscoveredInRun()!=null) st.setInt(9,c.getDiscoveredInRun().getId()); else st.setNull(9, Types.INTEGER);
            st.setInt(10, c.getId());
            boolean ok = st.executeUpdate()>0;
            // Synchronize tribes, sigils, and acts: naive approach remove existing then add new
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM Card_Tribe WHERE card_id=?")) {
                del.setInt(1, c.getId()); del.executeUpdate();
            }
            if (c.getTribes()!=null) for (Tribe t : c.getTribes()) addCardToTribe(c.getId(), t.getId());

            try (PreparedStatement del2 = conn.prepareStatement("DELETE FROM Card_Act WHERE card_id=?")) {
                del2.setInt(1, c.getId()); del2.executeUpdate();
            }
            if (c.getAppearsInActs()!=null) for (Act a : c.getAppearsInActs()) addCardToAct(c.getId(), a.getId());
            
            try (PreparedStatement del2 = conn.prepareStatement("DELETE FROM Card_Sigil WHERE card_id=?")) {
                del2.setInt(1, c.getId()); del2.executeUpdate();
            }
            if (c.getSigils()!=null) for (Sigil s : c.getSigils()) addSigilToCard(s.getId(),c.getId());
            return ok;
        }
    }
    public boolean deleteCard(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Card WHERE id=?")) {
            st.setInt(1,id); return st.executeUpdate()>0;
        }
    }
    public void addCardToTribe(int cardId, int tribeId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO Card_Tribe (card_id, tribe_id) VALUES (?,?)")) {
            st.setInt(1, cardId); st.setInt(2, tribeId); st.executeUpdate();
        }
    }
    public List<Tribe> getTribesForCard(int cardId) throws SQLException {
        List<Tribe> out = new ArrayList<>();
        String sql = "SELECT t.* FROM Tribe t JOIN Card_Tribe ct ON t.id=ct.tribe_id WHERE ct.card_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, cardId);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){ Tribe t = new Tribe(); t.setId(rs.getInt("id")); t.setName(rs.getString("name")); t.setNarration(rs.getString("narration")); t.setNotes(rs.getString("notes")); out.add(t); }
            }
        }
        return out;
    }
    public void addCardToAct(int cardId, int actId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO Card_Act (card_id, act_id) VALUES (?,?)")) {
            st.setInt(1, cardId); st.setInt(2, actId); st.executeUpdate();
        }
    }
    public List<Act> getActsForCard(int cardId) throws SQLException {
        List<Act> out = new ArrayList<>();
        String sql = "SELECT a.* FROM Act a JOIN Card_Act ca ON a.id=ca.act_id WHERE ca.card_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,cardId);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){ Act a = new Act(); a.setId(rs.getInt("id")); a.setNumber(rs.getInt("number")); a.setDescription(rs.getString("description")); a.setNotes(rs.getString("notes")); out.add(a); }
            }
        }
        return out;
    }

    public void addSigilToCard(int sigilId, int cardId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO Card_Sigil (card_id, sigil_id) VALUES (?,?)")) {
            st.setInt(1, cardId); st.setInt(2, sigilId); st.executeUpdate();
        }
    }
    public List<Sigil> getSigilsForCard(int cardId) throws SQLException {
        List<Sigil> out = new ArrayList<>();
        String sql = "SELECT a.* FROM Sigil a JOIN Card_Sigil ca ON a.id=ca.sigil_id WHERE ca.card_id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,cardId);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){ Sigil s = new Sigil(); s.setId(rs.getInt("id")); s.setName(rs.getString("name")); s.setDescription(rs.getString("description")); s.setIcon(rs.getString("icon")); s.setNotes(rs.getString("notes")); out.add(s); }
            }
        }
        return out;
    }
    // -------------------- Item --------------------
    public Item createItem(Item it) throws SQLException {
        String sql = "INSERT INTO Item (name, narration, notes, discovered_in_run_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, it.getName()); st.setString(2, it.getNarration()); st.setString(3, it.getNotes());
            if (it.getDiscoveredInRun()!=null) st.setInt(4, it.getDiscoveredInRun().getId()); else st.setNull(4, Types.INTEGER);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()){ if (rs.next()) it.setId(rs.getInt(1)); }
            if (it.getAppearsInActs()!=null) for (Act a : it.getAppearsInActs()) addItemToAct(it.getId(), a.getId());
            return it;
        }
    }
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT * FROM Item WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Item it = new Item();
                    it.setId(rs.getInt("id")); it.setName(rs.getString("name")); it.setNarration(rs.getString("narration")); it.setNotes(rs.getString("notes"));
                    int runId = rs.getInt("discovered_in_run_id"); if(!rs.wasNull()){ Run r = new Run(); r.setId(runId); it.setDiscoveredInRun(r); }
                    it.setAppearsInActs(getActsForItem(id));
                    return it;
                }
            }
        }
        return null;
    }
    public boolean updateItem(Item it) throws SQLException {
        String sql = "UPDATE Item SET name=?, narration=?, notes=?, discovered_in_run_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, it.getName()); st.setString(2, it.getNarration()); st.setString(3, it.getNotes());
            if (it.getDiscoveredInRun()!=null) st.setInt(4, it.getDiscoveredInRun().getId()); else st.setNull(4, Types.INTEGER);
            st.setInt(5, it.getId());
            boolean ok = st.executeUpdate()>0;
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM Item_Act WHERE item_id=?")) { del.setInt(1, it.getId()); del.executeUpdate(); }
            if (it.getAppearsInActs()!=null) for (Act a : it.getAppearsInActs()) addItemToAct(it.getId(), a.getId());
            return ok;
        }
    }
    public boolean deleteItem(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM Item WHERE id=?")) {
            st.setInt(1, id); return st.executeUpdate()>0;
        }
    }
    public void addItemToAct(int itemId, int actId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO Item_Act (item_id, act_id) VALUES (?,?)")) {
            st.setInt(1,itemId); st.setInt(2,actId); st.executeUpdate();
        }
    }
    public List<Act> getActsForItem(int itemId) throws SQLException {
        List<Act> out=new ArrayList<>();
        String sql="SELECT a.* FROM Act a JOIN Item_Act ia ON a.id=ia.act_id WHERE ia.item_id=?";
        try (PreparedStatement st=conn.prepareStatement(sql)) {
            st.setInt(1,itemId);
            try (ResultSet rs = st.executeQuery()) { while(rs.next()){ Act a=new Act(); a.setId(rs.getInt("id")); a.setNumber(rs.getInt("number")); a.setDescription(rs.getString("description")); a.setNotes(rs.getString("notes")); out.add(a);} }
        }
        return out;
    }

    // -------------------- Character --------------------
    public Character createCharacter(Character c) throws SQLException {
        String sql = "INSERT INTO `Character` (name, notes, canFight, discovered_in_run_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, c.getName()); st.setString(2, c.getNotes()); st.setInt(3, c.isCanFight()?1:0);
            if (c.getDiscoveredInRun()!=null) st.setInt(4, c.getDiscoveredInRun().getId()); else st.setNull(4, Types.INTEGER);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) c.setId(rs.getInt(1)); }
            if (c.getAppearsInActs()!=null) for (Act a : c.getAppearsInActs()) addCharacterToAct(c.getId(), a.getId());
            return c;
        }
    }
    public Character getCharacterById(int id) throws SQLException {
        String sql = "SELECT * FROM `Character` WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Character c = new Character();
                    c.setId(rs.getInt("id")); c.setName(rs.getString("name")); c.setNotes(rs.getString("notes")); c.setCanFight(rs.getInt("canFight")==1);
                    int runId = rs.getInt("discovered_in_run_id"); if(!rs.wasNull()){ Run r = new Run(); r.setId(runId); c.setDiscoveredInRun(r); }
                    c.setAppearsInActs(getActsForCharacter(id));
                    return c;
                }
            }
        }
        return null;
    }
    public boolean updateCharacter(Character c) throws SQLException {
        String sql = "UPDATE `Character` SET name=?, notes=?, canFight=?, discovered_in_run_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,c.getName()); st.setString(2,c.getNotes()); st.setInt(3, c.isCanFight()?1:0);
            if (c.getDiscoveredInRun()!=null) st.setInt(4, c.getDiscoveredInRun().getId()); else st.setNull(4, Types.INTEGER);
            st.setInt(5,c.getId());
            boolean ok = st.executeUpdate()>0;
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM Character_Act WHERE character_id=?")) { del.setInt(1,c.getId()); del.executeUpdate(); }
            if (c.getAppearsInActs()!=null) for (Act a : c.getAppearsInActs()) addCharacterToAct(c.getId(), a.getId());
            return ok;
        }
    }
    public boolean deleteCharacter(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM `Character` WHERE id=?")) { st.setInt(1, id); return st.executeUpdate()>0; }
    }
    public void addCharacterToAct(int characterId, int actId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO Character_Act (character_id, act_id) VALUES (?,?)")) {
            st.setInt(1, characterId); st.setInt(2, actId); st.executeUpdate();
        }
    }
    public List<Act> getActsForCharacter(int characterId) throws SQLException {
        List<Act> out=new ArrayList<>();
        String sql="SELECT a.* FROM Act a JOIN Character_Act ca ON a.id=ca.act_id WHERE ca.character_id=?";
        try (PreparedStatement st=conn.prepareStatement(sql)) {
            st.setInt(1,characterId);
            try (ResultSet rs = st.executeQuery()) { while(rs.next()){ Act a=new Act(); a.setId(rs.getInt("id")); a.setNumber(rs.getInt("number")); a.setDescription(rs.getString("description")); a.setNotes(rs.getString("notes")); out.add(a);} }
        }
        return out;
    }

    // -------------------- EncounterType --------------------
    public EncounterType createEncounterType(EncounterType e) throws SQLException {
        String sql = "INSERT INTO EncounterType (name, narration, notes, icon, isBoss, character_id, discovered_in_run_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, e.getName()); st.setString(2, e.getNarration()); st.setString(3, e.getNotes()); st.setString(4, e.getIcon());
            st.setInt(5, e.isBoss()?1:0);
            if (e.getCharacter()!=null) st.setInt(6, e.getCharacter().getId()); else st.setNull(6, Types.INTEGER);
            if (e.getDiscoveredInRun()!=null) st.setInt(7, e.getDiscoveredInRun().getId()); else st.setNull(7, Types.INTEGER);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) { if (rs.next()) e.setId(rs.getInt(1)); }
            if (e.getAppearsInActs()!=null) for (Act a : e.getAppearsInActs()) addEncounterTypeToAct(e.getId(), a.getId());
            return e;
        }
    }
    public EncounterType getEncounterTypeById(int id) throws SQLException {
        String sql = "SELECT * FROM EncounterType WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    EncounterType e = new EncounterType();
                    e.setId(rs.getInt("id")); e.setName(rs.getString("name")); e.setNarration(rs.getString("narration")); e.setNotes(rs.getString("notes"));
                    e.setIcon(rs.getString("icon")); e.setBoss(rs.getInt("isBoss")==1);
                    int charId = rs.getInt("character_id"); if(!rs.wasNull()){ Character c = new Character(); c.setId(charId); e.setCharacter(c); }
                    int runId = rs.getInt("discovered_in_run_id"); if(!rs.wasNull()){ Run r = new Run(); r.setId(runId); e.setDiscoveredInRun(r); }
                    e.setAppearsInActs(getActsForEncounterType(id));
                    return e;
                }
            }
        }
        return null;
    }
    public boolean updateEncounterType(EncounterType e) throws SQLException {
        String sql = "UPDATE EncounterType SET name=?, narration=?, notes=?, icon=?, isBoss=?, character_id=?, discovered_in_run_id=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,e.getName()); st.setString(2,e.getNarration()); st.setString(3,e.getNotes()); st.setString(4,e.getIcon());
            st.setInt(5, e.isBoss()?1:0);
            if (e.getCharacter()!=null) st.setInt(6,e.getCharacter().getId()); else st.setNull(6, Types.INTEGER);
            if (e.getDiscoveredInRun()!=null) st.setInt(7,e.getDiscoveredInRun().getId()); else st.setNull(7, Types.INTEGER);
            st.setInt(8,e.getId());
            boolean ok = st.executeUpdate()>0;
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM EncounterType_Act WHERE encountertype_id=?")) { del.setInt(1,e.getId()); del.executeUpdate(); }
            if (e.getAppearsInActs()!=null) for (Act a : e.getAppearsInActs()) addEncounterTypeToAct(e.getId(), a.getId());
            return ok;
        }
    }
    public boolean deleteEncounterType(int id) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM EncounterType WHERE id=?")) { st.setInt(1,id); return st.executeUpdate()>0; }
    }
    public void addEncounterTypeToAct(int encounterId, int actId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT IGNORE INTO EncounterType_Act (encountertype_id, act_id) VALUES (?,?)")) {
            st.setInt(1, encounterId); st.setInt(2, actId); st.executeUpdate();
        }
    }
    public List<Act> getActsForEncounterType(int encounterId) throws SQLException {
        List<Act> out=new ArrayList<>();
        String sql="SELECT a.* FROM Act a JOIN EncounterType_Act ea ON a.id=ea.act_id WHERE ea.encountertype_id=?";
        try (PreparedStatement st=conn.prepareStatement(sql)) {
            st.setInt(1, encounterId);
            try (ResultSet rs=st.executeQuery()) { while(rs.next()){ Act a=new Act(); a.setId(rs.getInt("id")); a.setNumber(rs.getInt("number")); a.setDescription(rs.getString("description")); a.setNotes(rs.getString("notes")); out.add(a);} }
        }
        return out;
    }

    // -------------------- Utility: list simple entities --------------------
    public List<Puzzle> listPuzzles() throws SQLException {
        List<Puzzle> out = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Puzzle")) {
            while(rs.next()){
                Puzzle p = new Puzzle();
                p.setId(rs.getInt("id")); p.setName(rs.getString("name")); p.setNotes(rs.getString("notes")); p.setReward(rs.getString("reward"));
                out.add(p);
            }
        }
        return out;
    }

    public List<Sigil> listSigils() throws SQLException {
        List<Sigil> out = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Sigil")) {
            while(rs.next()){
                Sigil s = new Sigil();
                s.setId(rs.getInt("id")); s.setName(rs.getString("name")); s.setDescription(rs.getString("description")); s.setIcon(rs.getString("icon")); s.setNotes(rs.getString("notes"));
                out.add(s);
            }
        }
        return out;
    }

    // (Add more list helpers as needed)
}
