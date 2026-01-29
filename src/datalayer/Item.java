import java.util.List;
import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private String narration;
    private String notes;
    private Run discoveredInRun;
    private List<Act> appearsInActs;
    public Item() {}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getNarration(){return narration;} public void setNarration(String n){this.narration=n;}
    public String getNotes(){return notes;} public void setNotes(String n){this.notes=n;}
    public Run getDiscoveredInRun(){return discoveredInRun;} public void setDiscoveredInRun(Run r){this.discoveredInRun=r;}
    public List<Act> getAppearsInActs(){return appearsInActs;} public void setAppearsInActs(List<Act> a){this.appearsInActs=a;}
    @Override public String toString(){ return "Item{id="+id+", name="+name+"}"; }
    @Override public boolean equals(Object o){ return (o instanceof Item)&&((Item)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}