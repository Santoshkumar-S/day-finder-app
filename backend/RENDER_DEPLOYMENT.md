# Deploying Spring Boot App to Render

This guide will help you deploy your Spring Boot application to Render using Docker.

## Prerequisites

- A Render account
- Your code pushed to a Git repository (GitHub, GitLab, etc.)

## Deployment Steps

### 1. Create a New Web Service on Render

1. Log in to your Render dashboard
2. Click "New +" and select "Web Service"
3. Connect your Git repository
4. Select the repository containing your Spring Boot application

### 2. Configure the Web Service

Use these settings:

- **Name**: `day-finder-backend` (or your preferred name)
- **Environment**: `Docker`
- **Region**: Choose the closest to your users
- **Branch**: `main` (or your default branch)
- **Root Directory**: `backend` (since your Dockerfile is in the backend folder)
- **Build Command**: Leave empty (Docker will handle this)
- **Start Command**: Leave empty (Docker will handle this)

### 3. Environment Variables (Optional)

Add these environment variables if needed:

- `SPRING_PROFILES_ACTIVE`: `production`
- `SERVER_PORT`: `8080` (Render will override this with their port)

### 4. Deploy

1. Click "Create Web Service"
2. Render will automatically build and deploy your application
3. The build process will:
   - Pull the Docker image
   - Build your Spring Boot application
   - Start the container
   - Health checks will verify the application is running

### 5. Access Your Application

Once deployed, Render will provide you with a URL like:
`https://your-app-name.onrender.com`

## Important Notes

### Port Configuration

- Render automatically assigns a port via the `PORT` environment variable
- Your application should use `System.getenv("PORT")` or configure Spring Boot to use the PORT environment variable

### Health Checks

- The Dockerfile includes a health check that pings `/actuator/health`
- Make sure your application responds to health checks

### Build Time

- The first build may take 5-10 minutes as it downloads Maven dependencies
- Subsequent builds will be faster due to Docker layer caching

## Troubleshooting

### Common Issues

1. **Build Fails**: Check the build logs in Render dashboard
2. **Application Won't Start**: Verify the Dockerfile and application.properties
3. **Health Check Fails**: Ensure the actuator endpoints are properly configured

### Logs

- View application logs in the Render dashboard
- Use `docker logs` if testing locally

## Local Testing

Before deploying to Render, test locally:

```bash
# Build the Docker image
docker build -t day-finder-backend .

# Run the container
docker run -p 8080:8080 day-finder-backend

# Or use docker-compose
docker-compose up --build
```

## Performance Optimization

- The multi-stage Dockerfile creates a smaller runtime image
- Only the JRE and application JAR are included in the final image
- Consider using Render's auto-scaling features for production workloads
