# Login Use Case Implementation

## Overview

This document describes how the **Login use case** from the HappyHouse requirements has been implemented in the Java Spring Boot backend.

## Use Case: Login

### Actors
- **Primary Actor**: Logged-in User (Roommate)
- **Secondary Actor**: Google API (for OAuth)

### Preconditions
- User has already registered with email/password OR
- User has a Google account

### Main Flow

#### Email/Password Login

1. **User provides credentials**
   - Frontend sends POST request to `/api/auth/login` with email and password
   - Request is validated (email format, password not empty)

2. **System authenticates user**
   - `AuthController` receives the request
   - `AuthService.login()` is called
   - Spring Security's `AuthenticationManager` validates credentials
   - Password is checked against BCrypt hashed password in database

3. **System retrieves user data**
   - User is fetched from MongoDB via `UserRepository`
   - User's anonymous username is checked for regeneration

4. **System checks anonymous username freshness**
   - If username was generated before today, generate new one
   - Update `anonymousUsername` and `anonymousUsernameGeneratedAt` fields
   - Save to database

5. **System generates tokens**
   - `JwtUtil` generates access token (24 hours expiration)
   - `JwtUtil` generates refresh token (7 days expiration)
   - Tokens contain: email, userId, issuedAt, expiration

6. **System returns authentication response**
   - Returns `AuthResponse` with:
     - Access token
     - Refresh token
     - User ID
     - Email
     - Anonymous username
     - User type (GUEST, REGULAR, ADMIN)

### Alternative Flows

#### Google OAuth Login

1. **User clicks "Continue with Google"**
   - Frontend redirects to Google OAuth
   - User authenticates with Google
   - Google returns googleId and email

2. **Frontend sends Google data to backend**
   - POST request to `/api/auth/google` with googleId and email

3. **System checks if user exists**
   - Query database for user with matching googleId
   - If not found, create new user with:
     - Email from Google
     - GoogleId
     - Generated anonymous username
     - UserType: REGULAR
     - AuthProvider: GOOGLE

4. **System generates tokens and returns response**
   - Same as steps 5-6 in main flow

#### Guest Registration

1. **User clicks "Register as Guest"**
   - Frontend sends POST request to `/api/auth/guest`

2. **System creates guest user**
   - Generate unique email: `guest_<timestamp>@happyhouse.local`
   - No password required
   - UserType: GUEST
   - AuthProvider: LOCAL
   - Generate anonymous username

3. **System generates tokens and returns response**
   - Same token generation as regular login

### Extensions

**3a. Invalid credentials**
- Spring Security throws `BadCredentialsException`
- `GlobalExceptionHandler` catches it
- Returns 401 Unauthorized with message: "Invalid email or password"

**3b. User not found**
- Repository returns empty Optional
- `ResourceNotFoundException` is thrown
- Returns 404 Not Found with message: "User not found"

**3c. Email not registered**
- Database query returns no results
- `UsernameNotFoundException` is thrown
- Returns 404 Not Found

## Implementation Details

### 1. Data Models

**User.java**
```java
@Document(collection = "users")
public class User {
    @Id private String id;
    @Indexed(unique = true) private String email;
    private String password;  // BCrypt hashed
    private String anonymousUsername;
    private LocalDateTime anonymousUsernameGeneratedAt;
    private UserType userType;  // GUEST, REGULAR, ADMIN
    private AuthProvider authProvider;  // LOCAL, GOOGLE
    private String googleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> groupChatIds;
    private List<String> postIds;
    private boolean isActive;
}
```

### 2. Request/Response DTOs

**LoginRequest.java**
```java
public class LoginRequest {
    @NotBlank @Email private String email;
    @NotBlank private String password;
}
```

**AuthResponse.java**
```java
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String userId;
    private String email;
    private String anonymousUsername;
    private User.UserType userType;
}
```

### 3. API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/login` | Email/password login | No |
| POST | `/api/auth/signup` | Create new account | No |
| POST | `/api/auth/guest` | Guest registration | No |
| POST | `/api/auth/google` | Google OAuth login | No |
| POST | `/api/auth/refresh` | Refresh access token | No |
| GET | `/api/auth/me` | Get current user | Yes |

### 4. Security Flow

```
Client Request
    ↓
CORS Filter (allow origins)
    ↓
JWT Authentication Filter
    ↓
    ├─ Extract JWT from Authorization header
    ├─ Validate token with JwtUtil
    ├─ Load user from CustomUserDetailsService
    └─ Set authentication in SecurityContext
    ↓
Controller (AuthController)
    ↓
Service Layer (AuthService)
    ↓
Repository (UserRepository → MongoDB)
    ↓
Response to Client
```

### 5. JWT Token Structure

**Access Token (expires in 24 hours):**
```json
{
  "sub": "user@example.com",
  "userId": "507f1f77bcf86cd799439011",
  "iat": 1698765432,
  "exp": 1698851832
}
```

**Refresh Token (expires in 7 days):**
```json
{
  "sub": "user@example.com",
  "userId": "507f1f77bcf86cd799439011",
  "type": "refresh",
  "iat": 1698765432,
  "exp": 1699370232
}
```

### 6. Anonymous Username Generation

**Examples:**
- `HappyPanda42`
- `CheerfulDolphin99`
- `PlayfulRabbit567`

**Regeneration Logic:**
- Checked on every login
- If `anonymousUsernameGeneratedAt` is before current date, generate new username
- Scheduled task runs at midnight UTC to regenerate all usernames
- Scheduler uses cron: `0 0 0 * * ?`

### 7. Password Security

- Passwords hashed with BCrypt (cost factor 10)
- Salt automatically generated per password
- Example: `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`
- Plain text passwords never stored

## Testing the Login Use Case

### 1. Start MongoDB
```bash
brew services start mongodb-community@7.0  # macOS
sudo systemctl start mongod  # Linux
```

### 2. Run the Backend
```bash
cd happyhouse-backend
mvn spring-boot:run
```

### 3. Test Login Endpoint

**Create a user first:**
```bash
curl -X POST http://localhost:5000/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Login with the user:**
```bash
curl -X POST http://localhost:5000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": "6543fb7e8d4f2a1b3c4d5e6f",
  "email": "test@example.com",
  "anonymousUsername": "HappyPanda42",
  "userType": "REGULAR"
}
```

**Test invalid credentials:**
```bash
curl -X POST http://localhost:5000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "wrongpassword"
  }'
```

**Expected Error:**
```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": "2025-10-27T10:30:00"
}
```

### 4. Test Guest Registration
```bash
curl -X POST http://localhost:5000/api/auth/guest
```

### 5. Use the Token
```bash
curl -X GET http://localhost:5000/api/auth/me \
  -H "Authorization: Bearer <your-token-here>"
```

## Integration with Frontend

Update your `Login.jsx` to call the backend:

```javascript
const handleSignIn = async () => {
  try {
    const response = await fetch('http://localhost:5000/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    });
    
    if (!response.ok) {
      throw new Error('Login failed');
    }
    
    const data = await response.json();
    
    // Store token
    localStorage.setItem('token', data.token);
    localStorage.setItem('refreshToken', data.refreshToken);
    localStorage.setItem('userId', data.userId);
    localStorage.setItem('anonymousUsername', data.anonymousUsername);
    
    // Navigate to home
    navigate('/neighborhood');
  } catch (error) {
    console.error('Login error:', error);
    alert('Invalid credentials');
  }
};
```

## Database Schema

**Users Collection:**
```json
{
  "_id": "507f1f77bcf86cd799439011",
  "email": "user@example.com",
  "password": "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy",
  "anonymousUsername": "HappyPanda42",
  "anonymousUsernameGeneratedAt": "2025-10-27T00:00:00",
  "userType": "REGULAR",
  "authProvider": "LOCAL",
  "googleId": null,
  "createdAt": "2025-10-20T14:30:00",
  "updatedAt": "2025-10-27T00:00:01",
  "groupChatIds": [],
  "postIds": [],
  "isActive": true
}
```

## Summary

✅ **Implemented:**
- Email/password authentication
- Google OAuth support (structure ready)
- Guest registration
- JWT token generation
- Anonymous username system with daily regeneration
- Password hashing with BCrypt
- Token refresh mechanism
- CORS configuration
- Global error handling
- Input validation

✅ **Matches Requirements:**
- System creates profile with anonymous display name
- System refreshes anonymous display name every day at 12:00 am
- System validates credentials
- System generates JWT tokens
- System supports multiple authentication methods

This implementation fully satisfies the Login use case requirements from the HappyHouse project document!
