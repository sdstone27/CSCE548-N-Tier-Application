import java.util.Objects;

public class Act {
    private int id;
    private int number;
    private String description;
    private String notes;

    public Act() {}
    public Act(int number, String description, String notes) {
        this.number = number; this.description = description; this.notes = notes;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override public String toString() {
        return "Act{id=" + id + ", number=" + number + ", description='" + description + "'}";
    }
    @Override public boolean equals(Object o){ return (o instanceof Act) && ((Act)o).id==id; }
    @Override public int hashCode(){ return Objects.hash(id); }
}
