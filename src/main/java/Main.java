import enums.Geschlecht;
import models.Mensch;
import serializers.JsonSerializer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Mensch person1 = new Mensch(
                33,
                "Alice",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                LocalDateTime.now(),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        Mensch person2 = new Mensch(
                33,
                "Bob",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                LocalDateTime.now(),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        Mensch person3 = new Mensch(
                33,
                "Cedrik",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                LocalDateTime.now(),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        List<Mensch> menschen = Arrays.asList(person1, person2, person3);

        Mensch person4 = new Mensch(
                33,
                "Denise",
                LocalDateTime.of(1990, 5, 15, 0, 0),
                LocalDateTime.now(),
                List.of("Lesen", "Schwimmen"),
                Map.of("Katzen", List.of("Mila", "Chris"), "Hunde", List.of("Max", "Moritz")),
                Geschlecht.WEIBLICH
        );

        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serializeList(menschen);
        String json2 = serializer.serialize(person4);
        System.out.println(json);
        System.out.println(json2);
    }
}
