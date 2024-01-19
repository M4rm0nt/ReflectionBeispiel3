import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Mensch person = new Mensch(
                33,
                "Celine",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(person);
        System.out.println(json);
    }
}
