import java.util.List;
import java.util.Objects;

public class Card {
    private int id;
    private String name;
    private String narration;
    private String notes;
    private Integer power; // nullable
    private Integer health;
    private String cost;
    private List<Sigil> sigils;
    private boolean isRare;
    private boolean isUnique;
    private List<Tribe> tribes;
    private Run discoveredInRun;
    private List<Act> appearsInActs;

    public Card() {}
    public Card(String name){ this.name=name; }
    // getters/setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getNarration(){return narration;} public void setNarration(String narration){this.narration=narration;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
    public Integer getPower(){return power;} public void setPower(Integer power){this.power=power;}
    public Integer getHealth(){return health;} public void setHealth(Integer health){this.health=health;}
    public String getCost(){return cost;} public void setCost(String cost){this.cost=cost;}
    public List<Sigil> getSigils(){return sigils;} public void setSigils(List<Sigil> sigils){this.sigils=sigils;}
    public boolean isRare(){return isRare;} public void setRare(boolean rare){isRare=rare;}
    public boolean isUnique(){return isUnique;} public void setUnique(boolean unique){isUnique=unique;}
    public List<Tribe> getTribes(){return tribes;} public void setTribes(List<Tribe> tribes){this.tribes=tribes;}
    public Run getDiscoveredInRun(){return discoveredInRun;} public void setDiscoveredInRun(Run discoveredInRun){this.discoveredInRun=discoveredInRun;}
    public List<Act> getAppearsInActs(){return appearsInActs;} public void setAppearsInActs(List<Act> appearsInActs){this.appearsInActs=appearsInActs;}
    @Override public String toString(){ return "Card{id="+id+", name="+name+", cost="+cost+", sigil count="+sigils.size()+"}";}
    @Override public boolean equals(Object o){ return (o instanceof Card)&&((Card)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}
