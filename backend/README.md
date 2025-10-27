# HappyHouse Backend - Java Spring Boot

Anonymous roommate communication application backend built with Spring Boot, MongoDB, and JWT authentication.

## 🏗️ Project Structure

```
happyhouse-backend/
├── src/main/java/com/happyhouse/
│   ├── HappyHouseApplication.java       # Main application class
│   ├── config/
│   │   └── SecurityConfig.java          # Spring Security configuration
│   ├── controller/
│   │   ├── AuthController.java          # Authentication endpoints
│   │   └── HealthController.java        # Health check endpoint
│   ├── dto/
│   │   ├── LoginRequest.java            # Login request DTO
│   │   ├── SignupRequest.java           # Signup request DTO
│   │   └── AuthResponse.java            # Authentication response DTO
│   ├── exception/
│   │   ├── BadRequestException.java     # Custom exception
│   │   ├── ResourceNotFoundException.java
│   │   └── GlobalExceptionHandler.java  # Global exception handler
│   ├── model/
│   │   └── User.java                    # User entity model
│   ├── repository/
│   │   └── UserRepository.java          # MongoDB repository
│   ├── security/
│   │   ├── CustomUserDetailsService.java
│   │   └── JwtAuthenticationFilter.java # JWT filter
│   ├── service/
│   │   ├── AuthService.java             # Authentication business logic
│   │   └── AnonymousUsernameScheduler.java
│   └── util/
│       ├── AnonymousUsernameGenerator.java
│       └── JwtUtil.java                 # JWT utility class
└── src/main/resources/
    └── application.properties            # Application configuration
```

## 🚀 Features

- ✅ **Email/Password Authentication** - Secure login with BCrypt password hashing
- ✅ **Google OAuth Integration** - Sign in with Google account
- ✅ **Guest Registration** - Anonymous guest access without credentials
- ✅ **JWT Token Authentication** - Stateless authentication with access and refresh tokens
- ✅ **Anonymous Username Generation** - Daily regeneration at midnight
- ✅ **MongoDB Integration** - NoSQL database for user data
- ✅ **CORS Configuration** - Cross-origin support for frontend
- ✅ **Global Exception Handling** - Centralized error handling
- ✅ **Input Validation** - Request validation with Jakarta Validation

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **MongoDB 7.0+** (running locally or remote)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🛠️ Setup Instructions

### 1. Install MongoDB

**On macOS:**
```bash
brew tap mongodb/brew
brew install mongodb-community@7.0
brew services start mongodb-community@7.0
```

**On Ubuntu:**
```bash
wget -qO - https://www.mongodb.org/static/pgp/server-7.0.asc | sudo apt-key add -
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list
sudo apt-get update
sudo apt-get install -y mongodb-org
sudo systemctl start mongod
```

**On Windows:**
Download and install from: https://www.mongodb.com/try/download/community

### 2. Configure Application

Edit `src/main/resources/application.properties`:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/happyhouse

# JWT Secret (Change this to a secure random string!)
jwt.secret=your-super-secret-key-change-this-to-something-very-secure-at-least-256-bits-long

# Google OAuth (Optional - for Google Sign In)
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET

# CORS (Add your frontend URL)
cors.allowed.origins=http://localhost:3000,http://localhost:5173
```

### 3. Build the Project

```bash
cd happyhouse-backend
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:5000`

## 📡 API Endpoints

### Authentication Endpoints

#### 1. **Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": "507f1f77bcf86cd799439011",
  "email": "user@example.com",
  "anonymousUsername": "HappyPanda42",
  "userType": "REGULAR"
}
```

#### 2. **Signup**
```http
POST /api/auth/signup
Content-Type: application/json

{
  "email": "newuser@example.com",
  "password": "password123"
}
```

#### 3. **Guest Registration**
```http
POST /api/auth/guest
```

#### 4. **Google OAuth Login**
```http
POST /api/auth/google
Content-Type: application/json

{
  "googleId": "123456789",
  "email": "user@gmail.com"
}
```

#### 5. **Refresh Token**
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 6. **Get Current User** (Protected)
```http
GET /api/auth/me
Authorization: Bearer <your-jwt-token>
```

### Health Check Endpoint

```http
GET /api/health
```

**Response:**
```json
{
  "status": "OK",
  "message": "HappyHouse Backend is running!",
  "timestamp": "2025-10-27T10:30:00",
  "service": "happyhouse-backend",
  "version": "1.0.0"
}
```

## 🔒 Authentication Flow

1. User sends login credentials to `/api/auth/login`
2. Backend validates credentials
3. Backend generates JWT access token (24 hours) and refresh token (7 days)
4. Frontend stores tokens (localStorage or httpOnly cookies)
5. Frontend includes token in Authorization header: `Bearer <token>`
6. Backend validates token on protected endpoints
7. When access token expires, use refresh token to get new access token

## 🎭 Anonymous Username System

- Each user gets an automatically generated anonymous username (e.g., "HappyPanda42")
- Username is regenerated every day at midnight (00:00:00 UTC)
- Scheduler runs automatically using Spring's `@Scheduled` annotation
- Users maintain anonymity in group chats and forums

## 🧪 Testing with curl

**Login:**
```bash
curl -X POST http://localhost:5000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

**Signup:**
```bash
curl -X POST http://localhost:5000/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"newuser@example.com","password":"password123"}'
```

**Guest Registration:**
```bash
curl -X POST http://localhost:5000/api/auth/guest
```

**Health Check:**
```bash
curl http://localhost:5000/api/health
```

## 🔐 Security Features

- **Password Hashing**: BCrypt with salt
- **JWT Tokens**: HS256 algorithm
- **CORS**: Configurable allowed origins
- **Input Validation**: Jakarta Validation annotations
- **Stateless Sessions**: No server-side session storage
- **Token Expiration**: Access token (24h), Refresh token (7 days)

## 🐛 Troubleshooting

### MongoDB Connection Issues
```bash
# Check if MongoDB is running
mongosh

# Or check service status
brew services list  # macOS
sudo systemctl status mongod  # Linux
```

### Port Already in Use
```bash
# Find process using port 5000
lsof -i :5000

# Kill the process
kill -9 <PID>
```

### Build Errors
```bash
# Clean and rebuild
mvn clean install -U
```

## 📚 Next Steps

1. ✅ Login use case implemented
2. ⬜ Implement other use cases:
   - Private Group Chat Creation
   - Post Creation
   - Forum Commenting
   - Polling system
3. ⬜ Add unit and integration tests
4. ⬜ Deploy to production (AWS, Heroku, etc.)

## 📝 Notes

- JWT secret in `application.properties` should be changed to a secure random string in production
- Consider using environment variables for sensitive configuration
- Anonymous username regeneration happens at midnight UTC (configurable in cron expression)
- Google OAuth requires setting up credentials in Google Cloud Console

## 👥 Team

- Arianna Wilde
- Drew Marting
- Holly Campbell
- Keira Schneider
- Leili Nelson

---

**Happy House** - Anonymous Roommate Communication 🏠
