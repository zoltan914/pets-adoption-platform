# DataConfig.java Class Summary

## Overview
`DataConfig.java` is a Spring Boot test class located at `src/test/java/com/devtiro/pets/manual/DataConfig.java` that serves as a **data seeding utility** for the Pet Adoption Platform. It preloads the Elasticsearch database with comprehensive sample data to facilitate testing and demonstration of the application.

## Purpose
This class provides a convenient way to populate the database with realistic sample data, including:
- User accounts (staff and regular users)
- Pet listings with various species, ages, and sizes
- Pet photos
- Medical records
- Geographic locations across the San Francisco Bay Area

## Key Features

### 1. **Data Seeding**
- Creates **45+ pet listings** with diverse characteristics
- Generates **2 test users** (one staff member, one regular user)
- Adds **multiple photos per pet** (2-3 photos each)
- Creates **comprehensive medical records** for pets

### 2. **Pet Diversity**
The sample data includes:

#### Dogs (25 pets)
- **20 regular dogs** covering:
  - Various breeds: Golden Retriever, Labrador, German Shepherd, Beagle, Corgi, etc.
  - Age range: 0.7 to 5.0 years
  - All sizes: Small, Medium, Large
  - Different temperaments: energetic, calm, friendly, protective

- **5 senior/calm dogs** specifically for low-energy matches:
  - Ages: 7.0 to 9.0 years
  - Calm temperaments
  - Perfect for apartments and quiet homes

#### Cats (15 pets)
- Various breeds: Siamese, Persian, Tabby, Calico, Tuxedo
- Age range: 0.5 to 4.0 years
- Different personalities: playful, independent, affectionate, calm

#### Other Species (5 pets)
- **Rabbits**: Mini Lop, Dutch, Netherland Dwarf
- **Birds**: Parakeets
- Age range: 0.3 to 2.0 years

### 3. **Geographic Distribution**
All pets are located in the **San Francisco Bay Area** with specific coordinates:
- San Francisco
- Oakland
- Berkeley
- Palo Alto
- Redwood City
- Fremont
- San Jose

Each pet has:
- Realistic street addresses
- Latitude/longitude coordinates
- City, state, and ZIP code

### 4. **Comprehensive Pet Profiles**
Each pet includes:
- **Basic Information**: Name, species, age, size
- **Description**: Detailed personality and characteristics
- **Status**: All set to AVAILABLE
- **Location Data**: Address and geographic coordinates
- **Staff Information**: Associated shelter staff member
- **Photos**: 2-3 photos per pet with realistic Unsplash URLs
- **Medical Records**: Vaccinations, spay/neuter records, health checks

### 5. **Test Users**

#### Staff User
- **Username**: `staff@shelter.com`
- **Password**: `password123`
- **Role**: STAFF
- **Name**: John Staff
- **Phone**: (555) 123-4567
- Can manage pet listings and medical records

#### Regular User
- **Username**: `user@example.com`
- **Password**: `password123`
- **Role**: USER
- **Name**: Jane User
- **Phone**: (555) 987-6543
- Can browse pets and submit applications

### 6. **Medical Records**
The system automatically generates medical records for pets:
- **Vaccinations**: DHPP for dogs, FVRCP for cats
- **Spay/Neuter Records**: For adult pets
- **Health Checks**: Annual wellness exams
- Each record includes: veterinarian name, appointment date, notes

## Class Structure

### Annotations
- `@SpringBootTest`: Loads full Spring application context
- `@Slf4j`: Provides logging capabilities
- `@Rollback(false)`: Ensures data persists after test execution

### Dependencies
The class autowires:
- `PetRepository`: Pet data management
- `UserRepository`: User account management
- `PhotoRepository`: Photo management
- `MedicalRecordRepository`: Medical record management
- `AuthService`: User authentication and registration
- `PasswordEncoder`: Password encryption

### Main Method: `loadSampleData()`

**Workflow**:
1. **Clear existing data**: Removes all pets, users, photos, and medical records
2. **Create users**: Generates staff and regular user accounts
3. **Create pets**: Builds 45+ pet listings with complete profiles
4. **Add photos**: Attaches 2-3 photos to each pet
5. **Add medical records**: Creates vaccination, spay/neuter, and health check records
6. **Save to database**: Persists all data to Elasticsearch

## Helper Methods

### `createStaffUser()`
Creates a shelter staff member with STAFF role and full permissions.

### `createRegularUser()`
Creates a standard user account with USER role for browsing and applications.

### `createPet()`
Generates a pet entity with:
- Basic information (name, species, age, size)
- Geographic location and address
- Staff association
- Status set to AVAILABLE

**Parameters**:
- `createdBy`: Staff user who manages the pet
- `name`: Pet's name
- `species`: Dog, Cat, Rabbit, or Bird
- `ageInYears`: Age as decimal (e.g., 1.5 for 1 year 6 months)
- `size`: SMALL, MEDIUM, or LARGE
- `location`: Coordinates as "latitude,longitude"
- `description`: Detailed personality and characteristics

### `generateAddress()`
Creates realistic Bay Area addresses based on latitude/longitude coordinates.

### `getPhotoUrl()`
Returns appropriate Unsplash photo URLs based on species:
- Dogs: Friendly dog photos
- Cats: Cute cat photos
- Rabbits: Adorable rabbit photos
- Other: General pet photos

### `createPhoto()`
Builds a photo entity linked to a specific pet.

### `addMedicalRecords()`
Generates comprehensive medical histories:
- **Vaccinations**: For every other pet (50%)
- **Spay/Neuter**: For adult pets (33%)
- **Health Checks**: For every fourth pet (25%)

### `createMedicalRecord()`
Creates individual medical record entries with all details.

## How to Run

### Method 1: Using Maven
```bash
mvn test -Dtest=DataConfig#loadSampleData
```

### Method 2: Using IDE
1. Open `DataConfig.java` in your IDE
2. Right-click on the `loadSampleData()` method
3. Select "Run" or "Debug"

### Method 3: Using JUnit
Navigate to the test class and execute the test method directly.

## Expected Output

After running successfully, you'll see:
```
=== Loading sample data ===
Cleared existing data
Created 2 users: staff@shelter.com (STAFF), user@example.com (USER)
Created 45 pets with 90 photos
Added medical records for all pets
=== Sample data loading completed ===
```

## Use Cases

### 1. **Development & Testing**
- Quickly populate database for feature development
- Test search and filtering with diverse data
- Validate geographic queries with real coordinates

### 2. **Demonstrations**
- Showcase platform capabilities to stakeholders
- Present realistic pet adoption scenarios
- Display variety of pet profiles and medical records

### 3. **User Acceptance Testing (UAT)**
- Provide testers with comprehensive sample data
- Test adoption workflows end-to-end
- Validate search functionality across different criteria

### 4. **Performance Testing**
- Load database with substantial data (45+ pets)
- Test pagination and sorting performance
- Validate Elasticsearch query efficiency

### 5. **UI/UX Testing**
- Ensure UI handles different pet types properly
- Test responsive design with various content lengths
- Validate image loading and display

## Data Statistics

After running `loadSampleData()`:
- **Users**: 2 (1 staff, 1 regular)
- **Pets**: 45+
  - Dogs: 25 (20 active + 5 senior/calm)
  - Cats: 15
  - Other (Rabbits, Birds): 5+
- **Photos**: 90+ (2-3 per pet)
- **Medical Records**: ~60+ entries
- **Locations**: 10+ unique Bay Area addresses

## Important Notes

### 1. **Data Persistence**
The `@Rollback(false)` annotation ensures data persists after the test runs. The data will remain in Elasticsearch until manually deleted or the test is run again.

### 2. **Idempotency**
The method is idempotent—running it multiple times will:
1. Delete all existing data
2. Recreate fresh sample data
Safe to run repeatedly without data duplication.

### 3. **Credentials**
Default passwords are simple for testing purposes:
- **All test users**: `password123`
- **Change these** in production environments

### 4. **Photo URLs**
Uses Unsplash placeholder URLs. For production:
- Upload actual pet photos
- Store in cloud storage (AWS S3, Cloudinary, etc.)
- Update URLs accordingly

### 5. **Geographic Data**
All coordinates are in the San Francisco Bay Area. To add pets from other locations:
1. Add new coordinate ranges in `createPet()` calls
2. Update `generateAddress()` method with new cities

## Benefits

1. **Time-Saving**: No manual data entry needed
2. **Consistency**: Same baseline data for all developers
3. **Realistic**: Diverse, production-like sample data
4. **Comprehensive**: Covers all entity types and relationships
5. **Maintainable**: Easy to update and extend
6. **Reusable**: Can be run repeatedly without issues

## Extending the Data

To add more sample data:

### Add More Pets
```java
pets.add(createPet(staffUser, "NewPet", Species.DOG, 2.0, PetSize.MEDIUM, 
    "37.7749,-122.4194", "Description here"));
```

### Add More Species
Update the `Species` enum and add corresponding pets.

### Add Different Locations
Extend the coordinate ranges and update `generateAddress()` method.

### Add More Medical Record Types
Create new medical record types in `addMedicalRecords()` method.

## Troubleshooting

### Issue: Data not persisting
**Solution**: Ensure `@Rollback(false)` is present and Elasticsearch is running.

### Issue: Duplicate key errors
**Solution**: The method clears all data first. If errors persist, manually clear Elasticsearch indices.

### Issue: Elasticsearch connection refused
**Solution**: Start Elasticsearch using `docker-compose up -d` before running the test.

### Issue: Authentication errors
**Solution**: Ensure `AuthService` and `PasswordEncoder` beans are properly configured.

## Conclusion

`DataConfig.java` is an essential utility for the Pet Adoption Platform that provides quick, consistent, and comprehensive sample data loading. It's particularly valuable for development, testing, and demonstration purposes, significantly reducing setup time and ensuring all team members work with the same baseline data.
