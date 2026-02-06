# Pet Adoption Platform

A comprehensive web-based platform connecting potential pet adopters with animal shelters, enabling users to browse, favorite, and apply to adopt pets while providing shelter staff with tools to manage listings, process applications, and coordinate meet-and-greets.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Implementation Status](#-implementation-status)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Loading Sample Data](#-loading-sample-data)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Search Implementations](#-search-implementations)
- [Project Structure](#project-structure)
- [User Stories](#user-stories)
- [Security](#security)
- [Contributing](#contributing)
- [Acknowledgements](#-acknowledgments)

## 🎯 Overview

The Pet Adoption Platform streamlines the adoption process through detailed pet profiles, application tracking, and shelter verification systems. It focuses on image and form management to provide a seamless experience for both adopters and shelter staff.

### Key Definitions

- **Shelter Staff**: Personnel authorized by an animal shelter to manage pet listings, process adoption applications, and coordinate with potential adopters.
- **Meet-and-Greet**: A scheduled in-person visit between a potential adopter and a pet they're interested in adopting, supervised by shelter staff.
- **Application Status**: The current state of an adoption application (pending, approved, or rejected).

## ✨ Features

### ✅ Implemented Features

### For Potential Adopters
- 🔍 **Advanced Search & Filtering**: Search pets by species, age, size, and location
- 📍 **Location-Based Search**: Find pets near you with geolocation support
- 📸 **Detailed Pet Profiles**: View multiple photos and comprehensive information about each pet
- 📝 **Adoption Applications**: Submit and track adoption applications
- 💾 **Draft Applications**: Save application progress and complete later
- 📊 **Application Tracking**: Monitor the status of your applications

### For Shelter Staff
- 📋 **Pet Listing Management**: Create, update, and delete pet listings
- 🖼️ **Photo Management**: Upload and manage multiple photos per pet
- 📁 **Medical Records**: Add and update comprehensive medical history
- 🔄 **Status Updates**: Update pet availability status (Available, On Hold, Adopted)
- 🔐 **Secure Authentication**: Role-based access control for shelter staff

### 🔴 Planned Features (Not Yet Implemented)
- 📝 **Adoption Applications**: Submit and track adoption applications
- 💾 **Draft Applications**: Save application progress and complete later
- 📊 **Application Tracking**: Monitor the status of your applications
- 💗 **Favorites/Wishlist**: Save favorite pets for later viewing
- 📅 **Meet-and-Greet Scheduling**: Schedule visits with pets

### Technical Features
- 🔎 **Elasticsearch Integration**: Fast, scalable search with advanced filtering
- 🔀 **Dual Search Implementations**: Choose between Criteria Query (Spring Data) or Native Client (low-level)
- 🔐 **JWT Authentication**: Secure authentication with access and refresh tokens
- 💨 **Caching**: Improved performance with Caffeine cache
- 📊 **Audit Trail**: Track creation and modification of records
- 🌐 **RESTful API**: Well-documented API endpoints


## ✅ Implementation Status

### Current Implementation

| Feature Category              | Status | Details                                           |
|-------------------------------|--------|---------------------------------------------------|
| **Pet Browsing & Search**     | ✅ Complete | Full search with filters, pagination, geolocation |
| **Pet Management**            | ✅ Complete | CRUD operations for pets (Staff only)             |
| **Photo Management**          | ✅ Complete | Upload, view, delete multiple photos per pet      |
| **Medical Records**           | ✅ Complete | Track vaccinations, health checks, procedures     |
| **User Authentication**       | ✅ Complete | JWT-based auth with roles (USER, STAFF)           |
| **Search Implementations**    | ✅ Complete | Two approaches: Criteria Query & Native Client    |
| **Data Seeding**              | ✅ Complete | Test utility with 45+ sample pets                 |
| **Adoption Applications**     | ✅ Complete | Managing applications for pets                    |
| **Notifications**             | 🟡 Not Implemented | Planned (TODO state)                              |
| **Meet-and-Greet Scheduling** | 🔴 Not Implemented | Not yet planned (Nice to have)                    |
| **Favorites/Wishlist**        | 🔴 Not Implemented | Not yet planned (Nice to have)                    |



### API Endpoints Status

**✅ Available Endpoints:**
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh access token
- `GET /api/pets` - Get all pets
- `GET /api/pets/{id}` - Get pet by ID
- `POST /api/pets` - Create pet (Staff)
- `PUT /api/pets/{id}` - Update pet (Staff)
- `PATCH /api/pets/{id}/status` - Update status (Staff)
- `DELETE /api/pets/{id}` - Delete pet (Staff)
- `GET /api/pets/search` - Advanced search with filters
- `POST /api/photos/{petId}` - Upload photos (Staff)
- `GET /api/photos/{petId}` - Get pet photos
- `DELETE /api/photos/{photoId}` - Delete Photo By ID (Staff)
- `GET /api/medical-records/{petId}` - Get all medical records for a pet (Staff)
- `POST /api/medical-records/{petId}` - Add medical record (Staff)
- `PUT /api/medical-records/{medicalRecordId}` - Update medical record (Staff)
- `POST /api/applications` - Create application
- `PUT /api/applications/{applicationId}` - Update a draft application
- `POST /api/applications/{applicationId}/submit` - Submit a draft application
- `POST /api/applications/{applicationId}/withdraw` - Withdraw a submitted application
- `DELETE /api/applications/{applicationId}` - Delete a draft application
- `GET /api/applications/{applicationId}` - Get a specific application by ID
- `GET /api/applications/my-applications` - Get user's applications
- `GET /api/applications/pet/{petId}` - Get all applications for a specific pet (Staff)
- `GET /api/applications/status/{status}` -  Get all applications by status (Staff)
- `GET /api/applications` - Get all applications (Staff)
- `PATCH /api/applications/{applicationId}/status` - Update application status (Staff)


**🔴 Not Available (Planned):**
- `POST /api/favorites` - Add pet to favorites
- `GET /api/favorites` - Get user's favorite pets
- `DELETE /api/favorites/{petId}` - Remove from favorites

## 🛠 Technology Stack

### Backend
- **Java 25**
- **Spring Boot 4.0.2**
  - Spring Security
  - Spring Data Elasticsearch
  - Spring Validation
  - Spring Cache
- **Elasticsearch 9.2.3**: Search and filtering engine
- **JWT (JSON Web Tokens)**: Authentication and authorization
- **MapStruct 1.6.3**: Object mapping
- **Lombok 1.18.42**: Reduce boilerplate code
- **Caffeine Cache**: High-performance caching

### Infrastructure
- **Docker & Docker Compose**: Containerization
- **Maven**: Build and dependency management

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 25** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**
- **Git** (for cloning the repository)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd pets-adoption-platform
```

### 2. Start Infrastructure Services

The project uses Docker Compose to run Elasticsearch:

```bash
docker-compose up -d
```

This will start:
- Elasticsearch on `http://localhost:9200`

Verify Elasticsearch is running:

```bash
curl http://localhost:9200
```

### 3. Configure Application

The application can be configured using environment variables or by modifying `src/main/resources/application.yaml`. See [Configuration](#configuration) section for details.

### 4. Build the Application

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` (default port).

## 🌱 Loading Sample Data

Before running the application, you can preload the database with comprehensive sample data using the included test utility. This is especially useful for development, testing, and demonstrations.

### What Gets Loaded

The data seeder creates:
- **45+ pet listings** (25 dogs, 15 cats, 5+ other species)
- **2 test user accounts** (staff and regular user)
- **90+ pet photos** (2-3 per pet)
- **60+ medical records** (vaccinations, health checks, spay/neuter records)
- **Geographic data** across the San Francisco Bay Area

### How to Run

**Option 1: Using Maven**
```bash
mvn test -Dtest=DataConfig#loadSampleData
```

**Option 2: Using IDE**
1. Navigate to `src/test/java/com/devtiro/pets/manual/DataConfig.java`
2. Right-click on the `loadSampleData()` method
3. Select **Run** or **Debug**

### Test User Credentials

After loading sample data, you can log in with:

**Staff Account** (can manage all pets and listings):
- Email: `staff@shelter.com`
- Password: `password123`

**Regular User Account** (can browse and apply for adoption):
- Email: `user@example.com`
- Password: `password123`

### Features of Sample Data

- **Diverse Pet Profiles**: Various breeds, ages (0.3 to 9 years), and sizes
- **Realistic Descriptions**: Detailed personality traits and characteristics
- **Geographic Distribution**: Pets located across Bay Area cities
- **Complete Medical Records**: Vaccination history, health checks, and procedures
- **Multiple Photos**: Each pet has 2-3 photos from Unsplash

### Important Notes

⚠️ **Running the test will clear all existing data** and replace it with fresh sample data.

✅ The test is **idempotent** - you can run it multiple times safely.

🔒 Default passwords are for **testing only** - change them for production use.

For detailed information about the data seeding utility, see the [DataConfig Summary](DataConfig_Summary.md).

## ⚙️ Configuration

### Application Properties

The application is configured via `src/main/resources/application.yaml`. Key configurations include:

```yaml
spring:
  elasticsearch:
    uris: ${ELASTICSEARCH_URL:http://localhost:9200}
    username: ${ELASTICSEARCH_USERNAME:}
    password: ${ELASTICSEARCH_PASSWORD:}

jwt:
  secret: ${JWT_SECRET:your-secret-key}
  access-token-expiration: ${JWT_ACCESS_EXPIRATION:3600000}  # 1 hour
  refresh-token-expiration: ${JWT_REFRESH_EXPIRATION:604800000}  # 7 days
```

### Environment Variables

You can override default configurations using environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `ELASTICSEARCH_URL` | Elasticsearch connection URL | `http://localhost:9200` |
| `ELASTICSEARCH_USERNAME` | Elasticsearch username (if auth enabled) | - |
| `ELASTICSEARCH_PASSWORD` | Elasticsearch password (if auth enabled) | - |
| `JWT_SECRET` | Secret key for JWT token generation | Default provided |
| `JWT_ACCESS_EXPIRATION` | Access token expiration time (ms) | `3600000` (1 hour) |
| `JWT_REFRESH_EXPIRATION` | Refresh token expiration time (ms) | `604800000` (7 days) |

### Production Configuration

For production deployments:

1. **Always set a custom JWT_SECRET**:
   ```bash
   export JWT_SECRET="your-secure-random-secret-key"
   ```

2. **Configure Elasticsearch with authentication** if required
3. **Use HTTPS** for all API communications
4. **Set appropriate token expiration times** based on security requirements

## 📚 API Documentation

The project includes a Postman collection for API testing: `Pet Adoption Platform API Final.postman_collection.json`

### Import Postman Collection

1. Open Postman
2. Click **Import**
3. Select the `Pet Adoption Platform API Final.postman_collection.json` file
4. The collection will be imported with all available endpoints

### Main API Endpoints

#### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/refresh` - Refresh access token

#### Pets
- `GET /api/pets` - Get all pets (with filtering)
- `GET /api/pets/{id}` - Get pet by ID
- `POST /api/pets` - Create new pet listing (Staff only)
- `PUT /api/pets/{id}` - Update pet information (Staff only)
- `PATCH /api/pets/{id}/status` - Update pet status (Staff only)
- `DELETE /api/pets/{id}` - Delete pet listing (Staff only)
- `GET /api/pets/search` - Advanced search with filters

#### Photos
- `POST /api/photos/{petId}` - Upload photos (Staff)
- `GET /api/photos/{petId}` - Get pet photos
- `DELETE /api/photos/{photoId}` - Delete Photo By ID (Staff)

#### Medical Records
- `GET /api/medical-records/{petId}` - Get All medical records for a pet (Staff)
- `POST /api/medical-records/{petId}` - Add medical record (Staff)
- `PUT /api/medical-records/{medicalRecordId}` - Update medical record (Staff)
- 
#### Applications
- `POST /api/applications` - Create application
- `PUT /api/applications/{applicationId}` - Update a draft application
- `POST /api/applications/{applicationId}/submit` - Submit a draft application
- `POST /api/applications/{applicationId}/withdraw` - Withdraw a submitted application
- `DELETE /api/applications/{applicationId}` - Delete a draft application
- `GET /api/applications/{applicationId}` - Get a specific application by ID
- `GET /api/applications/my-applications` - Get user's applications
- `GET /api/applications/pet/{petId}` - Get all applications for a specific pet (Staff)
- `GET /api/applications/status/{status}` -  Get all applications by status (Staff)
- `GET /api/applications` - Get all applications (Staff)
- `PATCH /api/applications/{applicationId}/status` - Update application status (Staff)

## 🔍 Search Implementations

The Pet Adoption Platform provides **two different search implementations** for querying pets based on various criteria. Both implementations offer the same functionality but use different approaches to interact with Elasticsearch.

For detailed information about the data seeding utility, see the [Search Implementation Guide](Search_Implementation_Guide.md).

### Available Implementations

#### 1. **Criteria Query Service** (Spring Data Elasticsearch)
**Service**: `PetSearchCriteriaQueryService`  
**Qualifier**: `@Qualifier("criteria")`

Uses Spring Data Elasticsearch's high-level Criteria API for building search queries.

**Features**:
- ✅ Type-safe query building with Criteria API
- ✅ Spring Data abstractions (easier to use)
- ✅ Automatic distance calculation using Haversine formula
- ✅ Cleaner, more readable code
- ✅ Built-in pagination and sorting support
- ✅ Better integration with Spring ecosystem

**Best For**:
- Developers familiar with Spring Data
- Rapid development and prototyping
- Standard search requirements
- Teams preferring higher-level abstractions

**Code Example**:
```java
@Service("criteria")
public class PetSearchCriteriaQueryService implements PetSearchService {
    private final ElasticsearchOperations elasticsearchOperations;
    
    // Uses Criteria API for query building
    Criteria criteria = Criteria.where("species").is(Species.DOG)
        .and(Criteria.where("age").greaterThanEqual(1))
        .and(Criteria.where("location").within(point, distance));
}
```

#### 2. **Native Elasticsearch Client Service** (Low-Level Client)
**Service**: `PetSearchNativeElasticClientService`  
**Qualifier**: `@Qualifier("native")`

Uses the native Elasticsearch Java client for direct, low-level access to Elasticsearch.

**Features**:
- ✅ Full control over Elasticsearch queries
- ✅ Access to all Elasticsearch features
- ✅ Better performance for complex queries
- ✅ Direct JSON DSL equivalent syntax
- ✅ More flexibility and customization
- ✅ Distance values automatically from Elasticsearch sort results

**Best For**:
- Complex search requirements
- Performance-critical applications
- Teams with strong Elasticsearch expertise
- When you need fine-grained control
- Advanced Elasticsearch features

**Code Example**:
```java
@Service("native")
public class PetSearchNativeElasticClientService implements PetSearchService {
    private final ElasticsearchClient elasticsearchClient;
    
    // Uses native Elasticsearch BoolQuery
    BoolQuery.Builder bool = new BoolQuery.Builder()
        .must(m -> m.term(t -> t.field("species").value("DOG")))
        .must(m -> m.range(r -> r.field("age").gte(JsonData.of(1))))
        .filter(f -> f.geoDistance(g -> g.field("location")...));
}
```

### Search Capabilities

Both implementations support:

| Feature | Description | Example |
|---------|-------------|---------|
| **Species Filter** | Filter by animal type | `species=DOG`, `species=CAT` |
| **Size Filter** | Filter by pet size | `petSize=SMALL`, `MEDIUM`, `LARGE` |
| **Age Range** | Filter by minimum/maximum age | `minAge=1&maxAge=5` |
| **Geolocation** | Search by distance from location | `lat=37.7749&lon=-122.4194&distance=10` |
| **Status Filter** | Automatically filters AVAILABLE pets | Built-in |
| **Pagination** | Page through results | `page=0&size=20` |
| **Sorting** | Sort by any field | `sort=age,desc` |
| **Distance Sorting** | Sort by proximity to location | `sort=distance,asc` |

### Switching Between Implementations

To switch between the two search implementations, modify the `@Qualifier` annotation in `PetServiceImpl`:

**File**: `src/main/java/com/devtiro/pets/services/impl/PetServiceImpl.java`

#### Use Criteria Query (Default):
```java
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    
    @Qualifier("criteria")  // ← Use Spring Data Criteria API
    private final PetSearchService petSearchService;
    
    // ... rest of the code
}
```

#### Use Native Client:
```java
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    
    @Qualifier("native")  // ← Use Native Elasticsearch Client
    private final PetSearchService petSearchService;
    
    // ... rest of the code
}
```

### Search Request Example

**Basic Search**:
```http
GET /api/pets/search?species=DOG&petSize=MEDIUM&minAge=1&maxAge=5
```

**Geolocation Search** (10km radius from San Francisco):
```http
GET /api/pets/search?lat=37.7749&lon=-122.4194&distance=10&sort=distance,asc
```

**Combined Search with Pagination**:
```http
GET /api/pets/search?species=CAT&petSize=SMALL&lat=37.7749&lon=-122.4194&distance=25&page=0&size=20&sort=age,desc
```

The postman collection contains additional tests as well.

### Distance Calculation

Both implementations calculate distance from the search location to each pet:

**Criteria Query**: Uses Haversine formula in Java
```java
private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    // Haversine formula implementation
    // Returns distance in kilometers
}
```

**Native Client**: Uses Elasticsearch's built-in distance calculation
```java
// Distance is automatically calculated by Elasticsearch
// Retrieved from sort results
FieldValue distanceValue = hit.sort().getLast();
dto.setDistance(distanceValue.doubleValue());
```

### Common Interface

Both implementations follow the `PetSearchService` interface:

```java
public interface PetSearchService {
    Page<PetDto> searchPets(PetSearchRequest request, Pageable pageable);
}
```

This allows seamless switching between implementations without changing any controller or client code—just update the `@Qualifier` annotation.

## 📁 Project Structure

```
pets-adoption-platform/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/devtiro/pets/
│   │   │       ├── config/                 # Configuration classes
│   │   │       │   ├── AuditConfig.java
│   │   │       │   ├── CacheConfig.java
│   │   │       │   ├── ElasticsearchConfig.java
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── controllers/            # REST controllers
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── PetController.java
│   │   │       │   ├── PhotoController.java
│   │   │       │   └── MedicalRecordController.java
│   │   │       ├── domain/                 # Domain models and DTOs
│   │   │       │   ├── entity/             # JPA entities
│   │   │       │   └── dto/                # Data Transfer Objects
│   │   │       ├── exceptions/             # Custom exceptions
│   │   │       ├── mappers/                # MapStruct mappers
│   │   │       ├── repositories/           # Data repositories
│   │   │       ├── security/               # Security components
│   │   │       │   ├── JwtService.java
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   └── CustomUserDetailsService.java
│   │   │       ├── services/               # Business logic
│   │   │       │   ├── PetSearchService.java      # Search interface
│   │   │       │   └── impl/               # Service implementations
│   │   │       │       ├── PetSearchCriteriaQueryService.java    # Criteria API search
│   │   │       │       ├── PetSearchNativeElasticClientService.java  # Native client search
│   │   │       │       ├── PetServiceImpl.java
│   │   │       │       ├── PhotoServiceImpl.java
│   │   │       │       └── MedicalRecordServiceImpl.java
│   │   │       └── PetsAdoptionPlatformApplication.java
│   │   └── resources/
│   │       └── application.yaml            # Application configuration
│   └── test/                               # Test files
│       └── java/com/devtiro/pets/manual/
│           └── DataConfig.java             # Sample data seeder utility
├── docker-compose.yml                       # Docker services configuration
├── pom.xml                                  # Maven configuration
├── lombok.config                            # Lombok configuration
├── Pet Adoption Platform API Final.postman_collection.json
├── DataConfig_Summary.md                    # Data seeding documentation
└── README.md                                # This file
```

## 👥 User Stories

### Browse Available Pets
**As a** potential adopter  
**I want to** search and filter pets by species, age, size, and location  
**So that** I can find pets that match my preferences and circumstances

**Acceptance Criteria:**
- ✅ Users can view a paginated list of available pets
- ✅ Users can filter pets by species (dog, cat, etc.)
- ✅ Users can filter pets by age range
- ✅ Users can filter pets by size (small, medium, large)
- ✅ Users can filter pets by distance from their location
- ✅ Users can see basic pet information in the listing
- ✅ Users can sort results by various criteria

### Manage Pet Listings
**As a** shelter staff member  
**I want to** create, update, and delete pet listings with detailed information  
**So that** I can keep potential adopters informed about available pets

**Acceptance Criteria:**
- ✅ Staff can create new pet listings with required information
- ✅ Staff can upload multiple photos per pet
- ✅ Staff can edit existing pet information
- ✅ Staff can update pet status (Available, On Hold, Adopted)
- ✅ Staff can add and update medical history
- ✅ Staff can delete pet listings when necessary

### Submit Adoption Application
**As a** potential adopter  
**I want to** submit an adoption application for a specific pet  
**So that** I can begin the process of adopting them


**Acceptance Criteria:**
- ✅ Users can fill out an adoption application form
- ✅ Users must provide personal contact information
- ✅ Users must provide living situation details
- ✅ Users can save applications as drafts
- 🟡 Users receive confirmation when application is submitted (Todo state)
- ✅ Users can track application status in their account
- ✅ Users can't submit multiple applications for the same pet


## 🔐 Security

### Authentication & Authorization

The platform uses JWT (JSON Web Tokens) for authentication:

1. **Registration**: Users register with email and password
2. **Login**: Users receive an access token (short-lived) and refresh token (long-lived)
3. **Access Token**: Used for authenticating API requests (1 hour expiration)
4. **Refresh Token**: Used to obtain new access tokens (7 days expiration)

### Role-Based Access Control

- **USER**: Can browse pets, submit applications, save favorites
- **STAFF**: All USER permissions + manage pet listings, process applications

### Security Headers

The application includes security configurations:
- CORS configuration
- CSRF protection
- Custom authentication entry points
- Access denied handlers

## 🎁 Bonus Features (Future Enhancements)

- 🤖 **AI-Powered Matching**: Suggest pets based on user preferences and living situation
- 📹 **Virtual Tours**: Live video tours for potential adopters
- 🏥 **Veterinary Integration**: Automated medical history updates from vet systems
- 💬 **Post-Adoption Support**: Resources, training tips, and direct messaging
- 🏠 **Foster Care System**: Track temporary pet placements

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Follow Java coding conventions
- Use Lombok annotations to reduce boilerplate
- Write unit tests for new features
- Update documentation as needed

## 📄 Acknowledgments

- <h3>Devtiro for project brief, and youtube videos (www.youtube.com/@devtiro)</h3>

