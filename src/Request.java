public class Request {
    public String id;
    public String firstname;
    public String lastname;
    public String gender;
    public int age;
    //public Date birthdate;
    public String department;
    public String occupation;
    public String medicalCondition;

    public String key;
    //public String state;
    public String line;
    public Priority priority;

    //public Request()

    public void updateRequest(String key, String line, Priority priority) {
        this.key = key;
        this.line = line;
        this.priority = priority;
    }
}

/*
{
    "cédula": "830-75-6515",
    "nombre": "Kate",
    "apellido": "Feeley",
    "género": "Female",
    "edad": 68,
    "departamento": "Lavalleja",
    "ocupación": "jubilado",
    "condición_médica": "n/a",
//  "centro_p1": "centro A",
//  "centro_p2": "centro B",
//  "centro_p3": "centro D",
//  "vacunación_inmediata": “false"
}
 */
