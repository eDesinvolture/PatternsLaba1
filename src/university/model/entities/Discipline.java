package university.model.entities;

public abstract class Discipline {
    public String name;
    public EducationalProject project;
    public Discipline(String name, EducationalProject project) {
        this.name = name; this.project = project;
    }
    @Override public String toString(){
        return name;
    }
}

