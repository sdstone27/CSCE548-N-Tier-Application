import java.util.List;
import java.util.Objects;

public class EncounterType {
    private int id;
    private String name;
    private String narration;
    private String notes;
    private String icon;
    private boolean isBoss;
    private Character character; // optional
    private Run discoveredInRun;
    private List<Act> appearsInActs;
    public EncounterType() {}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getNarration(){return narration;} public void setNarration(String narration){this.narration=narration;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
    public String getIcon(){return icon;} public void setIcon(String icon){this.icon=icon;}
    public boolean isBoss(){return isBoss;} public void setBoss(boolean boss){isBoss=boss;}
    public Character getCharacter(){return character;} public void setCharacter(Character character){this.character=character;}
    public Run getDiscoveredInRun(){return discoveredInRun;} public void setDiscoveredInRun(Run discoveredInRun){this.discoveredInRun=discoveredInRun;}
    public List<Act> getAppearsInActs(){return appearsInActs;} public void setAppearsInActs(List<Act> appearsInActs){this.appearsInActs=appearsInActs;}
    @Override public String toString(){ return "EncounterType{id="+id+", name="+name+"}"; }
    @Override public boolean equals(Object o){ return (o instanceof EncounterType)&&((EncounterType)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}
