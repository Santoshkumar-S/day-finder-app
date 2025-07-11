# Day Finder Web App - Optimized Version

A modern, high-performance web application for finding the day of the week for any given date. Built with Spring Boot backend and vanilla JavaScript frontend.

---

## 🚦 Branching & Deployment

- **Local Development:**
  - The `master` branch is used for all local development. Run both backend and frontend from this branch.
- **Production Deployment:**
  - The frontend is deployed to GitHub Pages from a separate branch dedicated to production. See `frontend/GITHUB_PAGES_DEPLOYMENT.md` for details.
  - The backend deployment process is documented in `backend/RENDER_DEPLOYMENT.md`.

---

## 🚀 Features

### Backend Optimizations

- **Service Layer Architecture**: Clean separation of concerns with dedicated service layer
- **Caching**: In-memory caching using Caffeine for frequently requested dates
- **Rate Limiting**: Protection against API abuse (60 requests per minute per IP)
- **Input Validation**: Comprehensive date format and range validation
- **Error Handling**: Structured error responses with proper HTTP status codes
- **Logging**: Detailed logging for debugging and monitoring
- **Health Check**: `/api/health` endpoint for monitoring
- **Compression**: Gzip compression for all responses
- **Security**: HTTP-only cookies and proper CORS configuration

### Frontend Optimizations

- **Real-time Validation**: Instant feedback with debounced input validation
- **Keyboard Shortcuts**:
  - `Enter` to submit
  - `Ctrl/Cmd + Enter` to submit
  - `Escape` to reset
- **Loading States**: Visual feedback during API calls
- **Error Handling**: Comprehensive error messages and user feedback
- **Theme Persistence**: Dark/light mode preference saved in localStorage
- **Responsive Design**: Mobile-friendly interface
- **Performance**: Optimized CSS with reduced repaints and reflows

---

## 🛠️ Technology Stack

### Backend

- **Spring Boot 3.5.3** - Modern Java framework
- **Java 17** - Latest LTS version
- **Caffeine Cache** - High-performance caching
- **Maven** - Dependency management
- **JUnit 5** - Unit testing

### Frontend

- **Vanilla JavaScript** - No framework dependencies
- **CSS3** - Modern styling with CSS variables
- **HTML5** - Semantic markup
- **Local Storage** - Theme persistence

---

## 📦 Installation & Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Modern web browser

### Backend Setup

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`

See `backend/RENDER_DEPLOYMENT.md` for production deployment instructions.

### Frontend Setup (Local Development)

```bash
cd frontend
# Open index.html directly in your browser
# OR serve with a local server:
python -m http.server 8000
# Then visit http://localhost:8000
```

For production deployment to GitHub Pages, see `frontend/GITHUB_PAGES_DEPLOYMENT.md`.

---

## 🧪 Testing

### Backend Tests

```bash
cd backend
./mvnw test
```

### Manual Testing

1. Open the frontend in your browser
2. Enter a date in DD-MM-YYYY format (e.g., 25-12-2024)
3. Click "Find Day" or press Enter
4. View the result and full date display

---

## 🔧 API Endpoints

### GET /api/day

Find the day of the week for a given date.

**Parameters:**

- `date` (required): Date in DD-MM-YYYY format

**Response:**

```json
{
  "input": "25-12-2024",
  "day": "Wednesday",
  "timestamp": "2024-01-15T10:30:00",
  "success": true
}
```

### GET /api/health

Health check endpoint.

**Response:**

```
Day Finder API is running!
```

---

## ⚠️ Error Messages (Console & Network Tab)

The following error messages may be displayed to users in the browser console or network tab:

**Frontend Error Messages:**

- ⚠️ Please enter a date.
- ⚠️ Please use DD-MM-YYYY format (e.g., 25-12-2024)
- ⚠️ Please enter a valid date
- ❌ Invalid date format. (fallback if backend does not provide a specific error)
- ❌ Failed to connect to the server.  
  Please check if the backend is running.
- ❌ An unexpected error occurred. Please try again.
- ⚠️ Backend is not available. Some features may not work.

**Backend Error Messages (sent in JSON, shown in frontend):**

- "Invalid date format. Use DD-MM-YYYY."
- "Date must be between 1900 and 2100."
- "Invalid date. Please check the date format and validity."
- "An unexpected error occurred. Please try again." (for server errors)

**How these appear:**

- If the backend returns a specific error (e.g., invalid format, out of range), it is shown to the user.
- If the backend is unreachable, the frontend shows:  
  ❌ Failed to connect to the server.  
  Please check if the backend is running.
- For any other unexpected error, the frontend shows:  
  ❌ An unexpected error occurred. Please try again.

---

## 🎯 Performance Optimizations

### Backend Performance

- **Caching**: Frequently requested dates are cached in memory
- **Rate Limiting**: Prevents abuse and ensures fair usage
- **Compression**: All responses are gzipped for faster transmission
- **Validation**: Early validation prevents unnecessary processing
- **Logging**: Structured logging for performance monitoring

### Frontend Performance

- **Debouncing**: Input validation is debounced to reduce API calls
- **CSS Optimization**: Reduced repaints and reflows
- **Lazy Loading**: Theme preference loaded on demand
- **Error Boundaries**: Graceful error handling prevents crashes

---

## 🔒 Security Features

- **Input Validation**: Server-side validation prevents injection attacks
- **Rate Limiting**: Prevents brute force and abuse
- **CORS Configuration**: Proper cross-origin resource sharing
- **HTTP Headers**: Security headers for XSS protection

---

## 📱 Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

---

## 🐛 Troubleshooting

### Common Issues

1. **Backend not starting**

   - Check if port 8080 is available
   - Ensure Java 17+ is installed
   - Verify Maven dependencies are downloaded

2. **Frontend not connecting to backend**

   - Ensure backend is running on localhost:8080
   - Check browser console for CORS errors
   - Verify network connectivity

3. **Date validation errors**
   - Use DD-MM-YYYY format (e.g., 25-12-2024)
   - Ensure date is between 1900-2100
   - Check for valid dates (e.g., no February 30)

---

## 📈 Monitoring

The application includes comprehensive logging:

- Request/response logging
- Error tracking
- Performance metrics
- Rate limiting events

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

---

## 📄 License

This project is open source and available under the MIT License.
