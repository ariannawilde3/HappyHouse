# HappyHouse Backend - Quick Start Guide

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB 7.0+

## Installation Steps

### 1. Install MongoDB

**macOS:**
```bash
brew tap mongodb/brew
brew install mongodb-community@7.0
brew services start mongodb-community@7.0
```

**Ubuntu:**
```bash
sudo apt-get install -y mongodb-org
sudo systemctl start mongod
```

**Windows:**
Download from: https://www.mongodb.com/try/download/community

### 2. Configure the Application

Edit `src/main/resources/application.properties` and update:

```properties
# Change JWT secret to something secure!
jwt.secret=your-super-secret-key-at-least-256-bits-long

# Update if using remote MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/happyhouse
```

### 3. Build and Run

```bash
# Navigate to project directory
cd happyhouse-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The server will start at: **http://localhost:5000**

### 4. Test the API

**Check health:**
```bash
curl http://localhost:5000/api/health
```

**Create a user:**
```bash
curl -X POST http://localhost:5000/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:5000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

You'll get a response with tokens:
```json
{
  "token": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "userId": "...",
  "email": "test@example.com",
  "anonymousUsername": "HappyPanda42",
  "userType": "REGULAR"
}
```

### 5. Connect Your Frontend

Update your React frontend's Login.jsx:

```javascript
const API_URL = 'http://localhost:5000/api';

const handleSignIn = async () => {
  try {
    const response = await fetch(`${API_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    
    const data = await response.json();
    localStorage.setItem('token', data.token);
    localStorage.setItem('anonymousUsername', data.anonymousUsername);
    
    navigate('/neighborhood');
  } catch (error) {
    console.error('Login failed:', error);
  }
};
```

## Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health check |
| POST | `/api/auth/signup` | Register new user |
| POST | `/api/auth/login` | Login |
| POST | `/api/auth/guest` | Guest registration |
| POST | `/api/auth/google` | Google OAuth |
| POST | `/api/auth/refresh` | Refresh token |
| GET | `/api/auth/me` | Get current user (requires token) |

## Troubleshooting

**MongoDB won't start:**
```bash
# macOS
brew services restart mongodb-community@7.0

# Linux
sudo systemctl restart mongod
```

**Port 5000 already in use:**
- Change port in `application.properties`: `server.port=8080`

**Build fails:**
```bash
mvn clean install -U
```

## Next Steps

1. Read the full [README.md](README.md) for detailed documentation
2. Read [LOGIN_USE_CASE.md](LOGIN_USE_CASE.md) for login implementation details
3. Implement other use cases (Group Chat, Forum, Polls)
4. Add unit tests
5. Deploy to production

## Project Structure

```
happyhouse-backend/
├── src/main/java/com/happyhouse/
│   ├── HappyHouseApplication.java   # Main app
│   ├── controller/                   # REST endpoints
│   ├── service/                      # Business logic
│   ├── repository/                   # Database access
│   ├── model/                        # Data models
│   ├── security/                     # Auth & JWT
│   └── util/                         # Utilities
└── src/main/resources/
    └── application.properties        # Configuration
```

## Important Notes

- Change JWT secret in production!
- Anonymous usernames regenerate daily at midnight
- Tokens expire: Access (24h), Refresh (7 days)
- CORS is configured for localhost:3000 and localhost:5173

## Support

For issues or questions, refer to:
- README.md - Full documentation
- LOGIN_USE_CASE.md - Login implementation details
- Your team's project document

Happy coding! 🏠
