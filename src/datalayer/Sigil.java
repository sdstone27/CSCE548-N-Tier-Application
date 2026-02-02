import java.util.List;
import java.util.Objects;

public class Sigil {
    private int id;
    private String name;
    private String description;
    private String icon;
    private String notes;
    private Run discoveredInRun;
    private List<Act> appearsInActs;

    public Sigil() {}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getIcon(){return icon;} public void setIcon(String icon){this.icon=icon;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
    public Run getDiscoveredInRun(){return discoveredInRun;} public void setDiscoveredInRun(Run discoveredInRun){this.discoveredInRun=discoveredInRun;}
    public List<Act> getAppearsInActs(){return appearsInActs;} public void setAppearsInActs(List<Act> appearsInActs){this.appearsInActs=appearsInActs;}
    @Override public String toString(){ return "Sigil{id="+id+", name="+name+", notes="+notes+"}";}
    @Override public boolean equals(Object o){ return (o instanceof Sigil)&&((Sigil)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}