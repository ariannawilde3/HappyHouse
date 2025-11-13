package com.happyhouse.util;

import java.security.SecureRandom;

public class AnonymousUsernameGenerator {

    private AnonymousUsernameGenerator() {
        throw new IllegalStateException("Utility class");
    }
    
    private static final String[] ADJECTIVES = {
        "Happy", "Cheerful", "Sunny", "Bright", "Joyful", "Lively", "Pleasant",
        "Friendly", "Kind", "Gentle", "Calm", "Peaceful", "Quiet", "Cozy",
        "Warm", "Cool", "Fresh", "Clean", "Neat", "Tidy", "Swift", "Quick",
        "Smart", "Wise", "Clever", "Creative", "Artistic", "Musical", "Dancing",
        "Singing", "Laughing", "Smiling", "Giggling", "Playful", "Fun", "Energetic",
        "Active", "Dynamic", "Bold", "Brave", "Strong", "Mighty", "Noble",
        "Royal", "Grand", "Elegant", "Graceful", "Charming", "Lovely", "Sweet"
    };
    
    private static final String[] NOUNS = {
        "Panda", "Koala", "Rabbit", "Dolphin", "Penguin", "Butterfly", "Hummingbird",
        "Fox", "Deer", "Owl", "Eagle", "Swan", "Dove", "Robin", "Sparrow",
        "Cat", "Dog", "Bear", "Tiger", "Lion", "Elephant", "Giraffe", "Zebra",
        "Horse", "Unicorn", "Dragon", "Phoenix", "Star", "Moon", "Sun", "Cloud",
        "Rainbow", "Ocean", "River", "Mountain", "Forest", "Meadow", "Garden",
        "Flower", "Rose", "Lily", "Daisy", "Tulip", "Orchid", "Sunflower",
        "Maple", "Oak", "Pine", "Willow", "Cherry", "Plum", "Apple", "Peach"
    };
    
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Generates a random anonymous username in the format "AdjectiveNoun123"
     * Example: "HappyPanda42", "CheerfulDolphin99"
     * 
     * @return A randomly generated anonymous username
     */
    public static String generate() {
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        int number = random.nextInt(1000); // 0-999
        
        return adjective + noun + number;
    }
    
    /**
     * Generates multiple unique anonymous usernames
     * 
     * @param count The number of usernames to generate
     * @return An array of unique anonymous usernames
     */
    public static String[] generateMultiple(int count) {
        String[] usernames = new String[count];
        for (int i = 0; i < count; i++) {
            usernames[i] = generate();
        }
        return usernames;
    }
}
