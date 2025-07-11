# Deploying Frontend to GitHub Pages

This guide will help you deploy your frontend application to GitHub Pages.

## Prerequisites

- A GitHub account
- Your code pushed to a GitHub repository
- Your backend deployed to Render (or another hosting service)

## Step-by-Step Deployment

### 1. API Configuration ✅

The production API URL has been configured in `config.js`:

```javascript
// In frontend/config.js
production: {
  API_BASE_URL: "https://day-finder-app.onrender.com/api";
}
```

### 2. Push Your Code to GitHub

Push your code to the master branch:

```bash
git add .
git commit -m "feat: Add GitHub Pages deployment configuration"
git push origin master
```

### 3. Enable GitHub Pages

1. Go to your GitHub repository
2. Click on **Settings** tab
3. Scroll down to **Pages** section (in the left sidebar)
4. Under **Source**, select **Deploy from a branch**
5. Choose **gh-pages** branch (will be created automatically)
6. Select **/(root)** folder
7. Click **Save**

### 4. Branch Strategy

This setup uses a **separate branch strategy**:

- **`master` branch**: Contains your full project (frontend + backend) for local development
- **`gh-pages` branch**: Contains only frontend files, automatically created and updated by GitHub Actions

#### How it works:

1. You work on the `master` branch with your full project
2. When you push changes to `master` that affect the frontend, GitHub Actions automatically:
   - Creates/updates the `gh-pages` branch
   - Copies only the frontend files to the root of `gh-pages`
   - Deploys to GitHub Pages

#### Benefits:

- Keep your local development setup intact (localhost:8080)
- Automatic deployment when frontend changes
- Clean separation between development and production
- No manual branch management needed

#### GitHub Actions Workflow

The `.github/workflows/deploy.yml` file is already configured to:

- Trigger on pushes to `master` branch
- Only run when frontend files change
- Automatically create and update the `gh-pages` branch
- Deploy to GitHub Pages

### 5. Access Your Deployed Site

Once deployed, your site will be available at:
`https://your-username.github.io/your-repo-name/`

## Important Configuration Notes

### CORS Configuration

Make sure your backend (Render) allows requests from your GitHub Pages domain. Update your Spring Boot application to include:

```java
@CrossOrigin(origins = {"https://your-username.github.io", "http://localhost:3000"})
```

### Environment Detection

The `config.js` file automatically detects the environment:

- **Localhost**: Uses `http://localhost:8080/api`
- **GitHub Pages**: Uses your Render URL

### Custom Domain (Optional)

If you want to use a custom domain:

1. In GitHub repository settings → Pages
2. Enter your custom domain
3. Add a `CNAME` file in your repository root with your domain
4. Configure DNS settings with your domain provider

## Troubleshooting

### Common Issues

1. **API calls failing**:

   - Check CORS configuration on backend
   - Verify API URL in `config.js`
   - Check browser console for errors

2. **Page not loading**:

   - Check GitHub Pages settings
   - Verify branch and folder selection
   - Check Actions tab for deployment status

3. **Styling issues**:
   - Ensure all CSS files are properly linked
   - Check for relative path issues

### Debugging

1. Open browser developer tools
2. Check Console for JavaScript errors
3. Check Network tab for failed API calls
4. Verify the correct API URL is being used

## Local Testing

Test your production build locally:

```bash
# Serve the frontend directory
cd frontend
python -m http.server 8000
# or
npx serve .

# Open http://localhost:8000
```

## Performance Optimization

- Consider minifying CSS and JavaScript for production
- Optimize images and assets
- Enable GitHub Pages caching headers

## Security Considerations

- Never commit API keys or sensitive data
- Use environment variables for configuration
- Enable HTTPS (GitHub Pages provides this automatically)
