import java.util.List;
import java.util.Objects;

public class Character {
    private int id;
    private String name;
    private String notes;
    private boolean canFight;
    private Run discoveredInRun;
    private List<Act> appearsInActs;
    public Character() {}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
    public boolean isCanFight(){return canFight;} public void setCanFight(boolean canFight){this.canFight=canFight;}
    public Run getDiscoveredInRun(){return discoveredInRun;} public void setDiscoveredInRun(Run discoveredInRun){this.discoveredInRun=discoveredInRun;}
    public List<Act> getAppearsInActs(){return appearsInActs;} public void setAppearsInActs(List<Act> appearsInActs){this.appearsInActs=appearsInActs;}
    @Override public String toString(){ return "Character{id="+id+", name="+name+", notes="+notes+"}";}
    @Override public boolean equals(Object o){ return (o instanceof Character)&&((Character)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}
