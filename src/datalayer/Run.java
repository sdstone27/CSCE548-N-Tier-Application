import java.util.Objects;

public class Run {
    private int id;
    private int number;
    private Act act; // reference
    private String notes;
    public Run() {}
    public Run(int number, Act act, String notes){ this.number=number; this.act=act; this.notes=notes;}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getNumber(){return number;} public void setNumber(int number){this.number=number;}
    public Act getAct(){return act;} public void setAct(Act act){this.act=act;}
    public String getNotes(){return notes;} public void setNotes(String notes){this.notes=notes;}
    @Override public String toString(){ return "Run{id="+id+", number="+number+", act="+act+", notes="+notes+"}";}
    @Override public boolean equals(Object o){ return (o instanceof Run) && ((Run)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}
