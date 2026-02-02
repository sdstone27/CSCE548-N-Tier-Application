import java.util.Objects;
public class Tribe {
    private int id;
    private String name;
    private String narration;
    private String notes;
    public Tribe() {}
    public Tribe(String name, String narration, String notes){ this.name=name; this.narration=narration; this.notes=notes;}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public String getNarration(){return narration;} public void setNarration(String n){this.narration=n;}
    public String getNotes(){return notes;} public void setNotes(String n){this.notes=n;}
    @Override public String toString(){ return "Tribe{id="+id+", name="+name+", notes="+notes+"}";}
    @Override public boolean equals(Object o){ return (o instanceof Tribe)&&((Tribe)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}