# Technology Stack

## Frontend
- **Framework:** React 18.2
- **Build Tool:** Vite 7.x
- **Language:** JavaScript (ES6+)
- **Styling:** CSS3 / TailwindCSS (to be decided)
- **State Management:** React Context API / Zustand (to be decided)
- **Routing:** React Router v6
- **HTTP Client:** Axios
- **Development Server:** Vite Dev Server (Port 5173)

## Backend
- **Runtime:** Node.js v18+
- **Framework:** Express.js
- **Language:** JavaScript (ES6+)
- **Authentication:** JWT (JSON Web Tokens)
- **Password Hashing:** bcrypt
- **Validation:** express-validator / Joi (to be decided)
- **Development Server:** Nodemon (Port 5000)

## Database
- **Database:** MongoDB / PostgreSQL (to be decided)
- **ODM/ORM:** Mongoose / Sequelize (to be decided)
- **Database Hosting:** MongoDB Atlas / Railway / Local

## Development Tools
- **Version Control:** Git & GitHub
- **API Testing:** Postman / Insomnia
- **Code Editor:** VS Code
- **Package Manager:** npm
- **Terminal:** Git Bash (Windows) / Terminal (Mac/Linux)

## Testing
- **Frontend Testing:** Vitest + React Testing Library
- **Backend Testing:** Jest + Supertest
- **E2E Testing:** Cypress / Playwright (optional)

## DevOps & Deployment
- **CI/CD:** GitHub Actions
- **Frontend Hosting:** Vercel / Netlify
- **Backend Hosting:** Railway / Render / Heroku
- **Containerization:** Docker (optional)

## Code Quality
- **Linting:** ESLint
- **Formatting:** Prettier
- **Pre-commit Hooks:** Husky (optional)

## Justification for Choices

### Why React?
- Large community and ecosystem
- Component reusability
- Virtual DOM for performance
- Excellent documentation
- Easy to learn

### Why Vite?
- Extremely fast development server
- Hot Module Replacement (HMR)
- Modern and actively maintained
- Better than Create React App
- Optimized production builds

### Why Node.js + Express?
- JavaScript on both frontend and backend (full-stack JS)
- Fast and lightweight
- Large npm ecosystem
- Perfect for REST APIs
- Non-blocking I/O

### Why MongoDB/PostgreSQL?
- **MongoDB:** 
  - Flexible schema
  - Good for rapid development
  - Easy to start with
  - JSON-like documents
- **PostgreSQL:** 
  - Relational data with relationships
  - ACID compliance
  - Data integrity
  - Complex queries

## Core Dependencies

### Backend Dependencies
```json
{
  "express": "^4.18.2",
  "dotenv": "^16.0.3",
  "cors": "^2.8.5",
  "mongoose": "^7.0.0",
  "jsonwebtoken": "^9.0.0",
  "bcrypt": "^5.1.0",
  "express-validator": "^7.0.0"
}

// backend dev dependencies 

{
  "nodemon": "^3.0.1",
  "jest": "^29.5.0",
  "supertest": "^6.3.0"
}

// frontend dev dependencies
{
  "react": "^18.2.0",
  "react-dom": "^18.2.0",
  "react-router-dom": "^6.11.0",
  "axios": "^1.4.0"
}

// frontend dev dependencies 
{
  "vite": "^7.1.7",
  "@vitejs/plugin-react": "^4.2.0",
  "vitest": "^1.0.0",
  "@testing-library/react": "^14.0.0"
}