# 🚀 AI-Powered Code Explainer

A web-based application that uses AI to generate plain-English explanations of code snippets. Built with **Java Spring Boot** backend and **HTML/CSS/JavaScript** frontend.

## 📋 Features

### Core Features
- ✅ Accept code snippets in **Python** and **JavaScript**
- ✅ Generate AI-powered plain-English explanations (2-4 sentences)
- ✅ Store and manage multiple code snippets
- ✅ Beautiful, responsive web interface
- ✅ View full history of submitted code snippets
- ✅ Edit and delete code snippets

### Bonus Features
- ✨ AI-suggested code optimization
- ✨ Time/Space complexity analysis
- ✨ Syntax-highlighted code display
- ✨ Modal view for detailed snippet information
- ✨ Persistent storage using H2 database

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────┐
│           Frontend (HTML/CSS/JavaScript)            │
│  - Responsive web interface                         │
│  - Code submission form                             │
│  - Results display with tabs                        │
│  - Snippet history and management                   │
└──────────────────────┬──────────────────────────────┘
                       │ HTTP/REST API
                       ▼
┌─────────────────────────────────────────────────────┐
│         Backend (Spring Boot REST API)              │
│  - CodeExplainerController (REST endpoints)         │
│  - CodeExplainerService (business logic)            │
│  - OpenAIService (AI integration)                   │
│  - CodeSnippetRepository (data access)              │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│             Data Layer & External APIs              │
│  - H2 Embedded Database (persistent storage)        │
│  - OpenAI API (AI explanations)                     │
└─────────────────────────────────────────────────────┘
```

## 🛠️ Technology Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Data access layer
- **H2 Database** - Embedded SQL database
- **Lombok** - Reduce boilerplate code
- **OkHttp** - HTTP client
- **Jackson** - JSON processing

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling with gradients and animations
- **Vanilla JavaScript** - Interactivity and API calls

### AI
- **OpenAI API** - GPT-3.5-turbo model for code explanations

## 📦 Project Structure

```
code-explainer-app/
├── pom.xml                                 # Maven configuration
├── README.md                              # This file
├── src/
│   ├── main/
│   │   ├── java/com/codeexplainer/
│   │   │   ├── CodeExplainerApplication.java     # Main Spring Boot app
│   │   │   ├── controller/
│   │   │   │   └── CodeExplainerController.java  # REST endpoints
│   │   │   ├── service/
│   │   │   │   ├── CodeExplainerService.java     # Business logic
│   │   │   │   └── OpenAIService.java            # AI integration
│   │   │   ├── entity/
│   │   │   │   └── CodeSnippet.java              # JPA entity
│   │   │   ├── dto/
│   │   │   │   ├── ExplainCodeRequest.java       # Request DTO
│   │   │   │   └── CodeExplanationResponse.java  # Response DTO
│   │   │   └── repository/
│   │   │       └── CodeSnippetRepository.java    # Data access
│   │   └── resources/
│   │       ├── application.properties    # Configuration
│   │       └── static/
│   │           └── index.html            # Frontend UI
│   └── test/                              # Test files
└── target/                                # Build output
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- OpenAI API key (optional, but recommended)

### Installation & Setup

1. **Clone/Download the project**
   ```bash
   cd code-explainer-app
   ```

2. **Configure OpenAI API Key**
   
   **Option A: Environment Variable (Recommended)**
   ```bash
   # On Linux/Mac
   export OPENAI_API_KEY="your-api-key-here"
   
   # On Windows
   set OPENAI_API_KEY=your-api-key-here
   ```

   **Option B: Edit application.properties**
   ```bash
   # Edit src/main/resources/application.properties
   openai.api.key=your-api-key-here
   ```

   > Get your OpenAI API key from: https://platform.openai.com/api-keys

3. **Build the project**
   ```bash
   mvn clean package
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
   Or run the JAR directly:
   ```bash
   java -jar target/code-explainer-app-1.0.0.jar
   ```

5. **Access the application**
   - Open your browser and go to: **http://localhost:8080**
   - The frontend will load automatically

### Verify Installation
```bash
# Check API health
curl http://localhost:8080/api/code/health
```

Expected response:
```json
{
  "status": "UP",
  "message": "Code Explainer API is running"
}
```

## 📚 API Endpoints

### Base URL
```
http://localhost:8080/api/code
```

### 1. Explain Code
**Endpoint:** `POST /explain`

**Request Body:**
```json
{
  "code": "def hello():\n    print('Hello')",
  "language": "python",
  "title": "Hello Function",
  "includeOptimization": true,
  "includeComplexity": true
}
```

**Response:**
```json
{
  "id": 1,
  "code": "def hello():\n    print('Hello')",
  "language": "python",
  "title": "Hello Function",
  "explanation": "This Python function defines a simple hello world function...",
  "complexity": "Time Complexity: O(1), Space Complexity: O(1)",
  "optimizedCode": "def hello(name='World'):\n    print(f'Hello {name}')",
  "optimizationNotes": "AI-suggested optimization",
  "tokensUsed": 250,
  "createdAt": "2024-01-15 10:30:45"
}
```

### 2. Get All Snippets
**Endpoint:** `GET /snippets`

**Response:**
```json
[
  {
    "id": 1,
    "code": "...",
    "language": "python",
    "title": "Hello Function",
    "explanation": "...",
    "createdAt": "2024-01-15 10:30:45"
  }
]
```

### 3. Get Snippet by ID
**Endpoint:** `GET /snippets/{id}`

**Response:** Same as explain code response

### 4. Update Snippet
**Endpoint:** `PUT /snippets/{id}`

**Request Body:** Same as explain code request

### 5. Delete Snippet
**Endpoint:** `DELETE /snippets/{id}`

**Response:**
```json
{
  "message": "Snippet deleted successfully"
}
```

### 6. Health Check
**Endpoint:** `GET /health`

**Response:**
```json
{
  "status": "UP",
  "message": "Code Explainer API is running"
}
```

## 🎯 Usage Guide

### Explaining Code

1. **Navigate to the web interface** (http://localhost:8080)
2. **Fill in the form:**
   - Enter a title (optional)
   - Select language (Python or JavaScript)
   - Paste your code
   - Check options for optimization/complexity analysis
3. **Click "Explain Code"**
4. **View the results** in the explanation panel with multiple tabs

### Managing Snippets

- **View history** - See all submitted snippets in the history section
- **Click a snippet** - View full details in a modal
- **Edit snippet** - Click "Edit" to modify and re-explain
- **Delete snippet** - Click "Delete" to remove a snippet

## 🔒 Error Handling & Hallucinations

### How We Handle Hallucinations

1. **Prompt Engineering**
   - Clear, specific prompts that limit AI interpretation
   - System instructions focused on code analysis
   - Constraints on response length (2-4 sentences)

2. **Input Validation**
   - Language restriction to Python and JavaScript
   - Code length limits
   - Validation of request parameters

3. **Error Recovery**
   - Graceful fallback if API fails
   - Default explanations when API unavailable
   - Timeout handling for API requests

4. **Testing Considerations**
   - Test with known code patterns
   - Verify explanations make sense
   - Compare with manual code review
   - Monitor token usage

### Code Accuracy Best Practices

- ✅ Always review AI-generated explanations
- ✅ Verify optimization suggestions before implementing
- ✅ Don't rely solely on complexity analysis
- ✅ Use for learning, not production decisions

## 🧪 Testing

### Manual Testing

```bash
# Test health endpoint
curl http://localhost:8080/api/code/health

# Test code explanation (requires OpenAI API key)
curl -X POST http://localhost:8080/api/code/explain \
  -H "Content-Type: application/json" \
  -d '{
    "code": "x = [1, 2, 3]\ny = [i*2 for i in x]",
    "language": "python",
    "title": "List Comprehension"
  }'

# Get all snippets
curl http://localhost:8080/api/code/snippets

# Get specific snippet
curl http://localhost:8080/api/code/snippets/1

# Delete snippet
curl -X DELETE http://localhost:8080/api/code/snippets/1
```

## 🐳 Docker Deployment (Optional)

Create a `Dockerfile`:

```dockerfile
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/code-explainer-app-1.0.0.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t code-explainer .
docker run -p 8080:8080 -e OPENAI_API_KEY="your-key" code-explainer
```

## 📊 Database

The application uses **H2 embedded database** for storage:

- **Database URL:** `jdbc:h2:mem:codeexplainerdb`
- **Console:** Available at `http://localhost:8080/h2-console`
- **Username:** `sa`
- **Password:** (empty)

### Database Schema

**Table: code_snippets**
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| code | VARCHAR(5000) | Code snippet |
| language | VARCHAR(50) | python or javascript |
| explanation | VARCHAR(2000) | AI explanation |
| title | VARCHAR(500) | Snippet title |
| complexity | VARCHAR(1000) | Complexity analysis |
| optimized_code | VARCHAR(5000) | Suggested optimization |
| optimization_notes | VARCHAR(1000) | Optimization notes |
| tokens_used | INT | API tokens used |
| created_at | TIMESTAMP | Creation time |
| updated_at | TIMESTAMP | Last update time |

## 🎨 Frontend Features

- **Responsive Design** - Works on desktop and mobile
- **Gradient UI** - Modern purple gradient theme
- **Syntax Highlighting** - Code display with monospace font
- **Tab Navigation** - Organized result sections
- **Modal Details** - Full snippet details in modal
- **Loading Indicators** - Spinner during API calls
- **Error Messages** - Clear feedback for failures
- **Smooth Animations** - Hover effects and transitions

## 📝 Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:codeexplainerdb
spring.jpa.hibernate.ddl-auto=create-drop

# OpenAI
openai.api.key=${OPENAI_API_KEY:}
openai.api.model=gpt-3.5-turbo

# Logging
logging.level.com.codeexplainer=DEBUG
```

## 🚨 Troubleshooting

### Port 8080 Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### OpenAI API Key Not Working
- Verify key at: https://platform.openai.com/account/api-keys
- Check API usage and billing
- Ensure key has sufficient balance
- Application works without key (with default explanations)

### Database Issues
- H2 database resets on each restart (in-memory)
- To persist data, change to file-based H2:
  ```properties
  spring.datasource.url=jdbc:h2:file:./data/codeexplainerdb
  ```

### CORS Issues
- Already configured in Controller: `@CrossOrigin(origins = "*")`
- For production, restrict to specific domain

## 📈 Future Enhancements

- [ ] Support for more languages (Java, C++, Go, Rust)
- [ ] Code diff comparison feature
- [ ] User authentication and personal saved snippets
- [ ] Syntax highlighting in code display
- [ ] Export snippets as PDF/Markdown
- [ ] Integration with GitHub
- [ ] Code performance benchmarking
- [ ] Multi-file project analysis

## 📄 License

This project is open-source and available for educational and commercial use.

## 🤝 Support

For issues or questions:
1. Check the troubleshooting section
2. Review API endpoint documentation
3. Verify OpenAI API configuration

## 📞 AI Model Information

- **Primary Model:** GPT-3.5-turbo
- **Alternative Models:** GPT-4, Claude, Mistral
- **Max Tokens:** 500 per request
- **Temperature:** 0.7 (balanced creativity and accuracy)

---

**Built with ❤️ using Spring Boot and AI**

Happy coding! 🚀
