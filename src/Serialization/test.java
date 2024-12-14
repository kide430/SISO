package Serialization;
import java.io.*;

public class test {

    // Die Klasse Person implementiert Serializable, um serialisiert werden zu können.
    class Person implements Serializable {

        private static final long serialVersionUID = 1L; // Versionierung für die Serialisierung

        private String name;
        private int age;

        // Konstruktor
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getter für Name und Alter
        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        // Überschreibt toString() für die lesbare Darstellung des Objekts
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    // Serialisierungsklasse
    public class SerializeExample {
        public static void main(String[] args) {
            // Zu serialisierendes Objekt erstellen
            Person person = new Person("Alice", 25);

            // Versucht, das Objekt in die Datei "person.ser" zu schreiben
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
                oos.writeObject(person); // Objekt wird serialisiert und in die Datei geschrieben
                System.out.println("Object serialized: " + person);
            } catch (IOException e) {
                e.printStackTrace(); // Gibt Fehler aus, falls die Serialisierung fehlschlägt
            }
        }
    }

    // Deserialisierungsklasse
    class DeserializeExample {
        public static void main(String[] args) {
            // Versucht, das Objekt aus der Datei "person.ser" zu lesen
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"))) {
                Person person = (Person) ois.readObject(); // Objekt wird deserialisiert
                System.out.println("Deserialized Object: " + person);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Gibt Fehler aus, falls die Deserialisierung fehlschlägt
            }
        }
    }

}
