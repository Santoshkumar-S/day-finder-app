#!/bin/bash

# GitHub Pages Setup Script for Day Finder Frontend
echo "🚀 Setting up GitHub Pages deployment..."

# Check if we're in the right directory
if [ ! -f "index.html" ]; then
    echo "❌ Error: Please run this script from the frontend directory"
    exit 1
fi

# Get the repository name from git
REPO_NAME=$(basename -s .git `git config --get remote.origin.url`)
USERNAME=$(git config --get remote.origin.url | sed 's/.*github.com[:/]\([^/]*\).*/\1/')

echo "📋 Repository: $USERNAME/$REPO_NAME"

# Update config.js with the correct API URL
echo "🔧 Please update the API URL in config.js with your Render backend URL:"
echo "   Replace 'https://your-app-name.onrender.com/api' with your actual URL"
echo ""
echo "   Example: https://day-finder-backend-abc123.onrender.com/api"
echo ""

read -p "Enter your Render backend URL (without /api): " RENDER_URL

if [ ! -z "$RENDER_URL" ]; then
    # Remove trailing slash if present
    RENDER_URL=$(echo $RENDER_URL | sed 's/\/$//')
    
    # Update config.js
    sed -i "s|https://your-app-name.onrender.com|$RENDER_URL|g" config.js
    
    echo "✅ Updated config.js with API URL: $RENDER_URL/api"
else
    echo "⚠️  Please manually update config.js with your Render URL"
fi

echo ""
echo "📝 Next steps:"
echo "1. Push your changes to GitHub:"
echo "   git add ."
echo "   git commit -m 'feat: Configure for GitHub Pages deployment'"
echo "   git push origin master"
echo ""
echo "2. Enable GitHub Pages:"
echo "   - Go to https://github.com/$USERNAME/$REPO_NAME/settings/pages"
echo "   - Select 'Deploy from a branch'"
echo "   - Choose 'gh-pages' branch (will be created automatically)"
echo "   - Select '/(root)' folder"
echo "   - Click Save"
echo ""
echo "3. Your site will be available at:"
echo "   https://$USERNAME.github.io/$REPO_NAME/"
echo ""
echo "4. The GitHub Actions workflow will automatically:"
echo "   - Create the gh-pages branch"
echo "   - Deploy your frontend when you push changes"
echo "   - Keep your master branch for local development"
echo ""
echo "🎉 Setup complete! Follow the steps above to deploy." 