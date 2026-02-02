import java.util.Objects;

public class Puzzle {
    private int id;
    private String name;
    private String notes;
    private String reward;
    private Act act;
    private Run discoveredInRun;

    public Puzzle() {}
    public Puzzle(String name, String notes, String reward, Act act, Run discoveredInRun){
        this.name=name; this.notes=notes; this.reward=reward; this.act=act; this.discoveredInRun=discoveredInRun;
    }
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
    public String getReward(){return reward;} public void setReward(String reward){this.reward=reward;}
    public Act getAct(){return act;} public void setAct(Act act){this.act=act;}
    public Run getDiscoveredInRun(){return discoveredInRun;} public void setDiscoveredInRun(Run discoveredInRun){this.discoveredInRun=discoveredInRun;}
    @Override public String toString(){ return "Puzzle{id="+id+", name='"+name+", reward="+reward+"}";}
    @Override public boolean equals(Object o){ return (o instanceof Puzzle)&&((Puzzle)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}