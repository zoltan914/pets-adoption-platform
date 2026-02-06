package com.devtiro.pets.manual;

import com.devtiro.pets.domain.dto.security.AuthResponse;
import com.devtiro.pets.domain.dto.security.RegisterRequest;
import com.devtiro.pets.domain.entity.*;
import com.devtiro.pets.repositories.*;
import com.devtiro.pets.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class DataConfig {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoRepository  photoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    @Rollback(false)
    public void loadSampleData() {
        log.info("=== Loading sample data ===");

        // Clear existing data
        userRepository.deleteAll();
        photoRepository.deleteAll();
        petRepository.deleteAll();
        medicalRecordRepository.deleteAll();
        applicationRepository.deleteAll();

        log.info("Cleared existing data");

        // Create users
        User staffUser = createStaffUser();
        User regularUser = createRegularUser();
        log.info("Created 2 users: {} (STAFF), {} (USER)", staffUser.getUsername(), regularUser.getUsername());

        // Create 45+ pets with variety
        List<Pet> pets = new ArrayList<>();

        // Dogs - Various breeds, ages, and sizes (20 dogs)
        pets.add(createPet(staffUser, "Max", Species.DOG, 1.5, PetSize.LARGE, "37.7749,-122.4194",
                "Friendly Golden Retriever who loves fetch and swimming. Great with kids and other dogs. House-trained and vaccinated."));
        pets.add(createPet(staffUser, "Bella", Species.DOG, 3.0, PetSize.LARGE, "37.8044,-122.2712",
                "Sweet Labrador mix with calm temperament. Wonderful with kids, loves long walks in the park."));
        pets.add(createPet(staffUser, "Rocky", Species.DOG, 4.0, PetSize.LARGE, "37.8715,-122.2730",
                "Loyal German Shepherd with excellent training. Great for active families who enjoy outdoor activities."));
        pets.add(createPet(staffUser, "Buddy", Species.DOG, 2.5, PetSize.MEDIUM, "37.4419,-122.1430",
                "Enthusiastic Beagle mix who loves adventure. Friendly with everyone, neutered and healthy."));
        pets.add(createPet(staffUser, "Charlie", Species.DOG, 0.8, PetSize.SMALL, "37.4852,-122.2364",
                "Adorable Corgi puppy, playful and smart. Eager to learn new tricks and commands."));
        pets.add(createPet(staffUser, "Daisy", Species.DOG, 2.0, PetSize.MEDIUM, "37.7749,-122.4194",
                "Gentle Cocker Spaniel, perfect lap dog. Loves cuddles and gentle play sessions."));
        pets.add(createPet(staffUser, "Duke", Species.DOG, 5.0, PetSize.LARGE, "37.8044,-122.2712",
                "Senior Rottweiler, calm and protective. Great guard dog for experienced owners."));
        pets.add(createPet(staffUser, "Lucy", Species.DOG, 1.2, PetSize.SMALL, "37.3382,-121.8863",
                "Tiny Chihuahua mix with big personality. Perfect apartment companion, loves being carried."));
        pets.add(createPet(staffUser, "Cooper", Species.DOG, 1.7, PetSize.MEDIUM, "37.8715,-122.2730",
                "Energetic Border Collie mix, needs active home. Loves agility training and running."));
        pets.add(createPet(staffUser, "Sadie", Species.DOG, 3.5, PetSize.LARGE, "37.4419,-122.1430",
                "Gentle Giant - Great Dane mix. Calm indoors, loves short walks and lounging."));
        pets.add(createPet(staffUser, "Zeus", Species.DOG, 2.3, PetSize.LARGE, "37.7749,-122.4194",
                "Athletic Siberian Husky who loves to run and play. Needs experienced owner with yard."));
        pets.add(createPet(staffUser, "Molly", Species.DOG, 1.0, PetSize.SMALL, "37.8044,-122.2712",
                "Sweet Pomeranian puppy, fluffy and friendly. Great with gentle children."));
        pets.add(createPet(staffUser, "Bear", Species.DOG, 3.0, PetSize.LARGE, "37.3382,-121.8863",
                "Fluffy Chow Chow mix, loyal and protective. Best as only pet in home."));
        pets.add(createPet(staffUser, "Rosie", Species.DOG, 0.7, PetSize.SMALL, "37.4852,-122.2364",
                "Playful Dachshund puppy, curious and brave. Loves digging and exploring new places."));
        pets.add(createPet(staffUser, "Tucker", Species.DOG, 2.0, PetSize.MEDIUM, "37.8715,-122.2730",
                "Friendly Australian Shepherd, smart and trainable. Loves learning new tricks daily."));
        pets.add(createPet(staffUser, "Maggie", Species.DOG, 4.5, PetSize.MEDIUM, "37.7749,-122.4194",
                "Senior Pit Bull mix with gentle soul. Great with kids, loves everyone she meets."));
        pets.add(createPet(staffUser, "Oliver", Species.DOG, 1.3, PetSize.SMALL, "37.4419,-122.1430",
                "Charming Shih Tzu who loves grooming and pampering. Perfect lap dog for quiet homes."));
        pets.add(createPet(staffUser, "Bailey", Species.DOG, 2.7, PetSize.LARGE, "37.8044,-122.2712",
                "Playful Boxer mix, energetic and fun-loving. Needs large yard to run and play."));
        pets.add(createPet(staffUser, "Penny", Species.DOG, 1.7, PetSize.MEDIUM, "37.3382,-121.8863",
                "Sweet Terrier mix with moderate energy. Good for first-time dog owners."));
        pets.add(createPet(staffUser, "Toby", Species.DOG, 1.2, PetSize.SMALL, "37.4852,-122.2364",
                "Cute Pug puppy who loves snuggling. Perfect for apartment living and city life."));
        pets.add(createPet(staffUser, "Cracker", Species.DOG, 14, PetSize.SMALL, "37.4853,-122.4364",
                "Cute Pit bull puppy who loves snuggling. Perfect for apartment living and city life. High energy Dog"));

        // Additional senior/calm dogs for LOW energy matches
        pets.add(createPet(staffUser, "Rusty", Species.DOG, 8.0, PetSize.MEDIUM, "37.7749,-122.4194",
                "Senior Golden mix, very calm and gentle. Perfect for quiet apartment living. Loves short walks and naps."));
        pets.add(createPet(staffUser, "Sophie", Species.DOG, 8.5, PetSize.MEDIUM, "37.8044,-122.2712",
                "Calm senior Cocker Spaniel. Great apartment dog, minimal exercise needed. Very sweet and loving."));
        pets.add(createPet(staffUser, "Winston", Species.DOG, 9.0, PetSize.LARGE, "37.3382,-121.8863",
                "Gentle senior Labrador. Low energy, perfect for someone wanting a calm companion. House-trained."));
        pets.add(createPet(staffUser, "Gracie", Species.DOG, 7.5, PetSize.MEDIUM, "37.4419,-122.1430",
                "Sweet senior Beagle mix. Very calm, loves lounging. Perfect for apartments, minimal exercise needs."));
        pets.add(createPet(staffUser, "Henry", Species.DOG, 7.0, PetSize.MEDIUM, "37.7749,-122.4194",
                "Mature Spaniel mix at 7 years. Calm temperament, great for first-time owners. Loves gentle walks."));

        // Cats - Various breeds and personalities (20 cats)
        pets.add(createPet(staffUser, "Luna", Species.CAT, 1.0, PetSize.SMALL, "37.7899,-122.3972",
                "Beautiful Siamese with striking blue eyes. Vocal and affectionate with her family."));
        pets.add(createPet(staffUser, "Whiskers", Species.CAT, 2.0, PetSize.MEDIUM, "37.8103,-122.2658",
                "Charming tabby with playful spirit. Enjoys interactive toys and window-watching."));
        pets.add(createPet(staffUser, "Mittens", Species.CAT, 0.5, PetSize.SMALL, "37.3382,-121.8863",
                "Young Persian kitten with soft gray fur. Playful yet gentle with everyone."));
        pets.add(createPet(staffUser, "Cleo", Species.CAT, 1.5, PetSize.MEDIUM, "37.5485,-121.9886",
                "Elegant black and white cat. Independent yet affectionate on her own terms."));
        pets.add(createPet(staffUser, "Shadow", Species.CAT, 3.0, PetSize.MEDIUM, "37.7749,-122.4194",
                "Sleek black cat, mysterious and loving. Prefers quiet homes without young children."));
        pets.add(createPet(staffUser, "Ginger", Species.CAT, 0.7, PetSize.SMALL, "37.8044,-122.2712",
                "Orange tabby kitten, energetic and curious. Loves to play all day long."));
        pets.add(createPet(staffUser, "Smokey", Species.CAT, 4.0, PetSize.MEDIUM, "37.3382,-121.8863",
                "Senior gray cat, calm and wise. Perfect quiet companion for adults."));
        pets.add(createPet(staffUser, "Princess", Species.CAT, 1.2, PetSize.SMALL, "37.8715,-122.2730",
                "Beautiful white Persian who requires daily grooming. Very gentle and sweet."));
        pets.add(createPet(staffUser, "Felix", Species.CAT, 1.7, PetSize.MEDIUM, "37.4419,-122.1430",
                "Tuxedo cat with playful personality. Gets along well with other cats."));
        pets.add(createPet(staffUser, "Muffin", Species.CAT, 2.5, PetSize.MEDIUM, "37.7749,-122.4194",
                "Calico cat, colorful and friendly. Loves sunbathing and treats."));
        pets.add(createPet(staffUser, "Tiger", Species.CAT, 0.8, PetSize.SMALL, "37.8044,-122.2712",
                "Striped tabby kitten and brave explorer. Very social and friendly with everyone."));
        pets.add(createPet(staffUser, "Bella", Species.CAT, 2.0, PetSize.MEDIUM, "37.4852,-122.2364",
                "Maine Coon mix, large and fluffy. Gentle giant of the cat world."));
        pets.add(createPet(staffUser, "Nala", Species.CAT, 1.3, PetSize.SMALL, "37.3382,-121.8863",
                "Sweet Ragdoll who loves being held. Perfect lap cat for cuddling sessions."));
        pets.add(createPet(staffUser, "Oscar", Species.CAT, 3.5, PetSize.MEDIUM, "37.8715,-122.2730",
                "Grumpy-looking British Shorthair who is actually very sweet. Loves routine."));
        pets.add(createPet(staffUser, "Misty", Species.CAT, 1.0, PetSize.SMALL, "37.7749,-122.4194",
                "Gray Russian Blue kitten, shy but affectionate once comfortable."));
        pets.add(createPet(staffUser, "Simba", Species.CAT, 2.3, PetSize.MEDIUM, "37.4419,-122.1430",
                "Orange Persian mix, regal and calm. Enjoys being pampered daily."));
        pets.add(createPet(staffUser, "Patches", Species.CAT, 3.0, PetSize.MEDIUM, "37.8044,-122.2712",
                "Tortoiseshell cat with unique coloring. Feisty personality but loving."));
        pets.add(createPet(staffUser, "Snowball", Species.CAT, 0.7, PetSize.SMALL, "37.3382,-121.8863",
                "Pure white kitten, playful and adorable. Loves chasing toys and laser pointers."));
        pets.add(createPet(staffUser, "Oreo", Species.CAT, 1.7, PetSize.MEDIUM, "37.7749,-122.4194",
                "Black and white tuxedo cat. Friendly and social with everyone."));
        pets.add(createPet(staffUser, "Jasper", Species.CAT, 4.5, PetSize.MEDIUM, "37.8715,-122.2730",
                "Senior orange tabby, calm and loving. Perfect for quiet home."));

        // Other Animals - Rabbits (5)
        pets.add(createPet(staffUser, "Thumper", Species.RABBIT, 0.7, PetSize.SMALL, "37.7699,-122.4210",
                "Fluffy white rabbit, gentle and calm. Great for families with gentle kids."));
        pets.add(createPet(staffUser, "Cottontail", Species.RABBIT, 0.8, PetSize.SMALL, "37.8044,-122.2712",
                "Brown and white rabbit who loves hopping around. Very friendly and social."));
        pets.add(createPet(staffUser, "Bunny", Species.RABBIT, 0.5, PetSize.SMALL, "37.3382,-121.8863",
                "Young lop-eared rabbit with adorable floppy ears. Loves fresh vegetables."));
        pets.add(createPet(staffUser, "Snowflake", Species.RABBIT, 1.0, PetSize.SMALL, "37.4852,-122.2364",
                "Angora rabbit with fluffy white fur. Requires regular grooming."));
        pets.add(createPet(staffUser, "Clover", Species.RABBIT, 1.2, PetSize.SMALL, "37.8715,-122.2730",
                "Gray dwarf rabbit, small and gentle. Perfect for apartment living."));

        // Cats - Various breeds and personalities (20 cats)
        pets.add(createPet(staffUser, "Luna", Species.CAT, 12, PetSize.SMALL, "37.7899,-122.3972",
                "Beautiful Siamese with striking blue eyes. Vocal and affectionate with her family."));
        pets.add(createPet(staffUser, "Whiskers", Species.CAT, 24, PetSize.MEDIUM, "37.8103,-122.2658",
                "Charming tabby with playful spirit. Enjoys interactive toys and window-watching."));
        pets.add(createPet(staffUser, "Mittens", Species.CAT, 6, PetSize.SMALL, "37.3382,-121.8863",
                "Young Persian kitten with soft gray fur. Playful yet gentle with everyone."));
        pets.add(createPet(staffUser, "Cleo", Species.CAT, 18, PetSize.MEDIUM, "37.5485,-121.9886",
                "Elegant black and white cat. Independent yet affectionate on her own terms."));
        pets.add(createPet(staffUser, "Shadow", Species.CAT, 36, PetSize.MEDIUM, "37.7749,-122.4194",
                "Sleek black cat, mysterious and loving. Prefers quiet homes without young children."));
        pets.add(createPet(staffUser, "Ginger", Species.CAT, 8, PetSize.SMALL, "37.8044,-122.2712",
                "Orange tabby kitten, energetic and curious. Loves to play all day long."));
        pets.add(createPet(staffUser, "Smokey", Species.CAT, 48, PetSize.MEDIUM, "37.3382,-121.8863",
                "Senior gray cat, calm and wise. Perfect quiet companion for adults."));
        pets.add(createPet(staffUser, "Princess", Species.CAT, 15, PetSize.SMALL, "37.8715,-122.2730",
                "Beautiful white Persian who requires daily grooming. Very gentle and sweet."));
        pets.add(createPet(staffUser, "Felix", Species.CAT, 20, PetSize.MEDIUM, "37.4419,-122.1430",
                "Tuxedo cat with playful personality. Gets along well with other cats."));
        pets.add(createPet(staffUser, "Muffin", Species.CAT, 30, PetSize.MEDIUM, "37.7749,-122.4194",
                "Calico cat, colorful and friendly. Loves sunbathing and treats."));
        pets.add(createPet(staffUser, "Tiger", Species.CAT, 10, PetSize.SMALL, "37.8044,-122.2712",
                "Striped tabby kitten and brave explorer. Very social and friendly with everyone."));
        pets.add(createPet(staffUser, "Bella", Species.CAT, 24, PetSize.MEDIUM, "37.4852,-122.2364",
                "Maine Coon mix, large and fluffy. Gentle giant of the cat world."));
        pets.add(createPet(staffUser, "Nala", Species.CAT, 16, PetSize.SMALL, "37.3382,-121.8863",
                "Sweet Ragdoll who loves being held. Perfect lap cat for cuddling sessions."));
        pets.add(createPet(staffUser, "Oscar", Species.CAT, 42, PetSize.MEDIUM, "37.8715,-122.2730",
                "Grumpy-looking British Shorthair who is actually very sweet. Loves routine."));
        pets.add(createPet(staffUser, "Misty", Species.CAT, 12, PetSize.SMALL, "37.7749,-122.4194",
                "Gray Russian Blue kitten, shy but affectionate once comfortable."));
        pets.add(createPet(staffUser, "Simba", Species.CAT, 28, PetSize.MEDIUM, "37.4419,-122.1430",
                "Orange Persian mix, regal and calm. Enjoys being pampered daily."));
        pets.add(createPet(staffUser, "Patches", Species.CAT, 36, PetSize.MEDIUM, "37.8044,-122.2712",
                "Tortoiseshell cat with unique coloring. Feisty personality but loving."));
        pets.add(createPet(staffUser, "Snowball", Species.CAT, 8, PetSize.SMALL, "37.3382,-121.8863",
                "Pure white kitten, playful and adorable. Loves chasing toys and laser pointers."));
        pets.add(createPet(staffUser, "Oreo", Species.CAT, 20, PetSize.MEDIUM, "37.7749,-122.4194",
                "Black and white tuxedo cat. Friendly and social with everyone."));
        pets.add(createPet(staffUser, "Jasper", Species.CAT, 54, PetSize.MEDIUM, "37.8715,-122.2730",
                "Senior orange tabby, calm and loving. Perfect for quiet home."));

        // Other Animals - Rabbits (5)
        pets.add(createPet(staffUser, "Thumper", Species.RABBIT, 8, PetSize.SMALL, "37.7699,-122.4210",
                "Fluffy white rabbit, gentle and calm. Great for families with gentle kids."));
        pets.add(createPet(staffUser, "Cottontail", Species.RABBIT, 10, PetSize.SMALL, "37.8044,-122.2712",
                "Brown and white rabbit who loves hopping around. Very friendly and social."));
        pets.add(createPet(staffUser, "Bunny", Species.RABBIT, 6, PetSize.SMALL, "37.3382,-121.8863",
                "Young lop-eared rabbit with adorable floppy ears. Loves fresh vegetables."));
        pets.add(createPet(staffUser, "Snowflake", Species.RABBIT, 12, PetSize.SMALL, "37.4852,-122.2364",
                "Angora rabbit with fluffy white fur. Requires regular grooming."));
        pets.add(createPet(staffUser, "Clover", Species.RABBIT, 14, PetSize.SMALL, "37.8715,-122.2730",
                "Gray dwarf rabbit, small and gentle. Perfect for apartment living."));

        // Add medical records to pets
        addMedicalRecords(pets, staffUser.getId());

        List<Pet> savedPets = Streamable.of(petRepository.saveAll(pets)).toList();
        List<Photo> photos = savedPets.stream()
                .map(pet -> createPhoto(getPhotoUrl(pet.getSpecies()), pet.getId()))
                .toList();

        photoRepository.saveAll(photos);

        log.info("Created {} pets successfully", savedPets.size());

        log.info("=== Sample data loaded successfully! ===");
        log.info("Total pets: {}", savedPets.size());
        log.info("  - Dogs: 25 (Small: 6, Medium: 12, Large: 7, includes 5 senior dogs)");
        log.info("  - Cats: 20 (Small: 9, Medium: 11)");
        log.info("  - Rabbits: 5 (Small: 5)");
        log.info("Login credentials:");
        log.info("  STAFF - Username: staff_member, Password: staffpass123");
        log.info("  USER  - Username: john_user, Password: password123");
    }

    /**
     * Create a STAFF user with full permissions
     */
    private User createStaffUser() {
        User staff = User.builder()
                .id("staff-001")
                .username("staff_member")
                .firstName("Staff")
                .lastName("Member")
                .phoneNumber("23030130")
                .email("staff@shelter.com")
                .password(passwordEncoder.encode("staffpass123"))
                .role(Role.STAFF)
                .enabled(true)
                .accountNonLocked(true)
                .build();
        staff.setCreatedBy("system");
        staff.setUpdatedBy("system");

        RegisterRequest registerRequest = new RegisterRequest(
                staff.getUsername(),
                staff.getEmail(),
                "staffpass123",
                staff.getFirstName(),
                staff.getLastName(),
                staff.getPhoneNumber(),
                staff.getRole()
        );
        AuthResponse registrationResponse = authService.register(
                registerRequest,
                "0:0:0:0:0:0:0:1"
        );

        staff.setRefreshToken(registrationResponse.getRefreshToken());
        LocalDateTime refreshExpiryInLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(registrationResponse.getRefreshExpiresIn()), ZoneId.systemDefault());
        staff.setRefreshTokenExpiryDate(refreshExpiryInLocalDateTime);

        User savedStaff = updateUser(staff);

        log.info("agent id: {}, email: {}, accesstoken: {}, refreshtoken: {}", savedStaff.getId(), savedStaff.getEmail(), registrationResponse.getAccessToken(), registrationResponse.getRefreshToken());
        return savedStaff;
    }

    /**
     * Create a regular USER with read-only permissions
     */
    private User createRegularUser() {
        User user = User.builder()
                .id("user-001")
                .username("john_user")
                .firstName("Regular")
                .lastName("User")
                .phoneNumber("101201200")
                .email("user@example.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.USER)
                .enabled(true)
                .accountNonLocked(true)
                .build();

        user.setCreatedBy("system");
        user.setUpdatedBy("system");

        RegisterRequest registerRequest = new RegisterRequest(
                user.getUsername(),
                user.getEmail(),
                "password123",
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getRole()
        );
        AuthResponse registrationResponse = authService.register(
                registerRequest,
                "0:0:0:0:0:0:0:1"
        );

        user.setRefreshToken(registrationResponse.getRefreshToken());
        LocalDateTime refreshExpiryInLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(registrationResponse.getRefreshExpiresIn()), ZoneId.systemDefault());
        user.setRefreshTokenExpiryDate(refreshExpiryInLocalDateTime);

        User savedUser = updateUser(user);

        log.info("agent id: {}, email: {}, accesstoken: {}, refreshtoken: {}", savedUser.getId(), savedUser.getEmail(), registrationResponse.getAccessToken(), registrationResponse.getRefreshToken());
        return savedUser;
    }

    @CacheEvict(value = "usersByEmail", key = "#user.email")
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "usersByEmail", key = "#user.email")
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * Helper method to create a pet
     * @param ageInYears Age in years (e.g., 1.5 for 1 year 6 months)
     */
    private Pet createPet(User createdBy, String name, Species species, double ageInYears,
                          PetSize size, String location, String description) {
        String[] coords = location.split(",");
        double lat = Double.parseDouble(coords[0]);
        double lon = Double.parseDouble(coords[1]);

        // Generate address based on location
        Address address = generateAddress(lat, lon);

        Pet pet = Pet.builder()
                .name(name)
                .description(description)
                .species(species)
                .staffId(createdBy.getId())
                .staffName(createdBy.getFirstName() + " " + createdBy.getLastName())
                .staffEmail(createdBy.getEmail())
                .age((int) (ageInYears))
                .petSize(size)
                .address(address)
                .location(new GeoPoint(lat, lon))
                .status(PetStatus.AVAILABLE)
                .build();

        pet.setCreatedBy("system");
        pet.setUpdatedBy("system");
        return pet;
    }

    /**
     * Generate address based on coordinates
     */
    private Address generateAddress(double lat, double lon) {
        if (lat > 37.8 && lat < 37.9) {
            return new Address("123 Telegraph Ave", "Oakland", "CA", "94609");
        } else if (lat > 37.85) {
            return new Address("456 University Ave", "Berkeley", "CA", "94702");
        } else if (lat > 37.7 && lat < 37.8) {
            return new Address("789 Market Street", "San Francisco", "CA", "94103");
        } else if (lat > 37.4 && lat < 37.5) {
            if (lon < -122.2) {
                return new Address("321 Page Mill Rd", "Palo Alto", "CA", "94304");
            } else {
                return new Address("555 Middlefield Rd", "Redwood City", "CA", "94063");
            }
        } else if (lat > 37.5) {
            return new Address("888 Central Ave", "Fremont", "CA", "94536");
        } else {
            return new Address("1001 El Camino Real", "San Jose", "CA", "95128");
        }
    }

    /**
     * Get appropriate photo URL based on species
     */
    private String getPhotoUrl(Species species) {
        return switch (species) {
            case DOG -> "https://images.unsplash.com/photo-1543466835-00a7907e9de1";
            case CAT -> "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba";
            case RABBIT -> "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308";
            default -> "https://images.unsplash.com/photo-1548199973-03cce0bbc87b";
        };
    }

    /**
     * Helper method to create a photo
     */
    private Photo createPhoto(String url, String petId) {
        return Photo.builder()
                .url(url)
                .petId(petId)
                .build();
    }

    /**
     * Add medical records to pets
     */
    private void addMedicalRecords(List<Pet> pets, String staffId) {
        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            String petId = pet.getId();

            // Add vaccination record
            if (i % 2 == 0) {
                createMedicalRecord(
                        petId,
                        staffId,
                        "Vaccination",
                        pet.getSpecies() == Species.DOG ? "DHPP vaccine" :
                                (pet.getSpecies() == Species.CAT ? "FVRCP vaccine" : "General vaccination"),
                        LocalDateTime.now().minusMonths(3),
                        "Dr. Smith",
                        "All vaccinations up to date. Next due in 1 year."
                );
            }

            // Add spay/neuter record for adults
            if (pet.getAge() > 12 && i % 3 == 0) {
                createMedicalRecord(
                        petId,
                        staffId,
                        "Spay/Neuter",
                        "Spayed/Neutered",
                        LocalDateTime.now().minusMonths(6),
                        "Dr. Johnson",
                        "Procedure successful. Fully recovered with no complications."
                );
            }

            // Add health check
            if (i % 4 == 0) {
                createMedicalRecord(
                        petId,
                        staffId,
                        "Health Check",
                        "Annual wellness exam",
                        LocalDateTime.now().minusMonths(1),
                        "Dr. Williams",
                        "Healthy and active. No issues detected. Weight is appropriate."
                );
            }

        }
    }

    /**
     * Helper method to create a medical record
     */
    private MedicalRecord createMedicalRecord(String petId, String staffId, String type, String description, LocalDateTime date,
                                              String veterinarian, String notes) {
        MedicalRecord record = MedicalRecord.builder()
                .petId(petId)
                .staffId(staffId)
                .type(type)
                .description(description)
                .appointmentDate(date)
                .veterinarian(veterinarian)
                .notes(notes)
                .build();
        return medicalRecordRepository.save(record);
    }
}
