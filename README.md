# 🚀 AI-Powered Code Explainer

A web-based application that uses AI to generate plain-English explanations of code snippets. Built with **Java Spring Boot** backend and **HTML/CSS/JavaScript** frontend.

## 📋 Features

### Core Features
- ✅ Accept code snippets in **Python** and **JavaScript**
- ✅ Generate structured AI-powered code explanations in plain English
- ✅ Store and manage multiple code snippets
- ✅ Beautiful, responsive web interface
- ✅ View full history of submitted code snippets
- ✅ Edit and delete code snippets

### Bonus Features
- ✨ AI-suggested code optimization
- ✨ Time/Space complexity analysis
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
│  - Groq API (AI-powered code analysis)              │
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
- **Groq API** - Llama 3 8B 8192 for code explanations

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
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Groq API key (required for AI explanations)

### Installation & Setup

1. **Clone/Download the project**
   ```bash
   git clone <repo-url>
   cd code-explainer-app
   ```

2. **Configure Groq API Key**
   
   ```bash
   #Update application.properties

   openai.api.key=YOUR_GROQ_API_KEY
   openai.api.model=llama3-8b-8192
   openai.api.url=https://api.groq.com/openai/v1/chat/completions
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - Open your browser and go to: **http://localhost:8080**
   - The frontend will load automatically


## 📚 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/code/explain` | Generate code explanation |
| GET | `/api/code/snippets` | Fetch all saved snippets |
| GET | `/api/code/snippets/{id}` | Fetch snippet by ID |
| PUT | `/api/code/snippets/{id}` | Update existing snippet |
| DELETE | `/api/code/snippets/{id}` | Delete snippet |
| GET | `/api/code/health` | Health check |

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
<img width="1662" height="890" alt="Screenshot 2026-06-27 184513" src="https://github.com/user-attachments/assets/0d323504-0148-42ab-9458-7e6f96e5f3a0" />
<img width="750" height="800" alt="Screenshot 2026-06-27 184523" src="https://github.com/user-attachments/assets/7441789b-1dbe-4f48-8d43-86e88904ec8c" />
<img width="757" height="787" alt="Screenshot 2026-06-27 184536" src="https://github.com/user-attachments/assets/4ba7cf77-2035-4817-89e0-5e0d1d8b6a4e" />

### Managing Snippets

- **View history** - See all submitted snippets in the history section
- **Click a snippet** - View full details in a modal
- **Edit snippet** - Click "Edit" to modify and re-explain
- **Delete snippet** - Click "Delete" to remove a snippet
<img width="1552" height="523" alt="Screenshot 2026-06-27 184548" src="https://github.com/user-attachments/assets/68ec5584-6cce-45da-a189-76bc48c9ee4f" />

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

## 📊 Database

The application uses **H2 embedded database** for storage:

- **Database URL:** `jdbc:h2:mem:codeexplainerdb`
- **Console:** Available at `http://localhost:8080/h2-console`

## 🚨 Troubleshooting

### Port 8080 Already in Use
```bash
# Change port in application.properties
server.port=8081
```
### Groq API Key Not Working
- Verify key at: https://console.groq.com/keys

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
- [ ] Syntax highlighting
- [ ] Export snippets as PDF/Markdown
- [ ] Integration with GitHub
- [ ] Code performance benchmarking
- [ ] Multi-file project analysis

## 📄 License

Open source for educational and learning purposes.

## 🤝 Support

For issues or questions:
1. Check the troubleshooting section
2. Review API endpoint documentation
3. Verify Groq API configuration

## 📞 AI Model Information

- **Primary Model:** Groq - Llama 3 8B 8192
- **Alternative Models:** GPT-4, Claude, Mistral
- **Max Tokens:** 800 per request
- **Temperature:** 0.7 (balanced creativity and accuracy)

---
## Author
Adesh Ghodekar

**Built with ❤️ using Spring Boot and AI**
