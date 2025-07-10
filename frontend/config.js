// Configuration for different environments
const config = {
    // Development environment (localhost)
    development: {
        API_BASE_URL: 'http://localhost:8080/api'
    },
    // Production environment (Render)
    production: {
        API_BASE_URL: 'https://day-finder-app.onrender.com/api'
    }
};

// Determine current environment
const isLocalhost = window.location.hostname === 'localhost' || 
                   window.location.hostname === '127.0.0.1' ||
                   window.location.protocol === 'file:';

const currentConfig = isLocalhost ? config.development : config.production;

// Export the configuration
window.APP_CONFIG = currentConfig; 