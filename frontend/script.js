// DOM Elements
const inputEl = document.getElementById("dateInput");
const resultEl = document.getElementById("result");
const fullDateEl = document.getElementById("fullDate");
const themeToggle = document.getElementById("themeToggle");
const findDayBtn = document.querySelector('button[onclick="findDay()"]');
const resetBtn = document.getElementById("resetBtn");

// Info tooltip logic for banner above container
const infoBtn = document.getElementById('infoBtn');
const infoTooltip = document.getElementById('infoTooltip');
if (infoBtn && infoTooltip) {
    infoBtn.addEventListener('mouseenter', () => {
        infoTooltip.classList.add('active');
    });
    infoBtn.addEventListener('mouseleave', () => {
        infoTooltip.classList.remove('active');
        // Remove focus highlight if mouse leaves after click
        if (document.activeElement === infoBtn) {
            infoBtn.blur();
        }
    });
    infoBtn.addEventListener('focus', () => {
        infoTooltip.classList.add('active');
    });
    infoBtn.addEventListener('blur', () => {
        infoTooltip.classList.remove('active');
    });
}

// State
let dark = false;
let debounceTimer = null;
let isLoading = false;

// Constants
const API_BASE_URL = 'http://localhost:8080/api';
const DATE_REGEX = /^\d{2}-\d{2}-\d{4}$/;

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    setupEventListeners();
    setupKeyboardShortcuts();
    checkApiHealth();
    // Load saved theme preference
    const savedDarkMode = localStorage.getItem('darkMode');
    if (savedDarkMode === 'true') {
        dark = true;
        document.body.classList.add("dark-mode");
        themeToggle.textContent = "☀️";
    }
});

function setupEventListeners() {
    // Clear result when user clicks inside input
    inputEl.addEventListener("focus", clearResults);
    
    // Real-time validation with debouncing
    inputEl.addEventListener("input", debounce(validateInput, 300));
    
    // Enter key to submit
    inputEl.addEventListener("keypress", (e) => {
        if (e.key === 'Enter' && !isLoading) {
            findDay();
        }
    });

    // Restrict input to only numbers and hyphens
    inputEl.addEventListener('keypress', (e) => {
        const char = String.fromCharCode(e.which);
        if (!/[0-9\-]/.test(char)) {
            e.preventDefault();
        }
    });
    inputEl.addEventListener('paste', (e) => {
        const paste = (e.clipboardData || window.clipboardData).getData('text');
        if (!/^[-0-9]*$/.test(paste)) {
            e.preventDefault();
        }
    });

    // Theme toggle
    themeToggle.addEventListener("click", toggleTheme);
    
    // Reset button
    resetBtn.addEventListener("click", resetFields);
}

function setupKeyboardShortcuts() {
    document.addEventListener('keydown', (e) => {
        // Ctrl/Cmd + Enter to find day
        if ((e.ctrlKey || e.metaKey) && e.key === 'Enter' && !isLoading) {
            e.preventDefault();
            findDay();
        }
        
        // Escape to reset
        if (e.key === 'Escape') {
            resetFields();
        }
    });
}

function debounce(func, wait) {
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(debounceTimer);
            func(...args);
        };
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(later, wait);
    };
}

function validateInput() {
    const date = inputEl.value.trim();
    
    if (!date) {
        clearResults();
        return;
    }
    
    // Only clear errors while typing, don't show format error here
    if (!DATE_REGEX.test(date) || !isValidDate(date)) {
        clearResults();
        return;
    }
    
    clearResults();
}

function isValidDate(dateStr) {
    const [day, month, year] = dateStr.split('-').map(Number);
    const date = new Date(year, month - 1, day);
    
    return date.getFullYear() === year &&
           date.getMonth() === month - 1 &&
           date.getDate() === day &&
           year >= 1900 && year <= 2100;
}

function clearResults() {
    resultEl.textContent = "";
    fullDateEl.textContent = "";
    resultEl.classList.remove("shake", "error", "success");
}

function showError(message) {
    resultEl.textContent = message;
    resultEl.classList.add("error");
    resultEl.classList.remove("success", "shake");
}

function showSuccess(message) {
    resultEl.textContent = message;
    resultEl.classList.add("success", "shake");
    resultEl.classList.remove("error");
}

function setLoadingState(loading) {
    isLoading = loading;
    findDayBtn.disabled = loading;
    findDayBtn.textContent = loading ? "⏳ Finding..." : "Find Day";
    findDayBtn.style.opacity = loading ? "0.7" : "1";
}

async function findDay() {
    const date = inputEl.value.trim();
    
    if (!date) {
        showError("⚠️ Please enter a date.");
        return;
    }
    
    if (!DATE_REGEX.test(date)) {
        showError("⚠️ Please use DD-MM-YYYY format (e.g., 25-12-2024)");
        return;
    }
    
    if (!isValidDate(date)) {
        showError("⚠️ Please enter a valid date");
        return;
    }
    
    setLoadingState(true);
    clearResults();
    
    try {
        const response = await fetch(`${API_BASE_URL}/day?date=${encodeURIComponent(date)}`);
        const data = await response.json();
        
        if (response.ok && data.success) {
            showSuccess(`✅ Day: ${data.day}`);
            displayFullDate(date);
        } else {
            showError(data.error || "❌ Invalid date format.");
        }
    } catch (error) {
        console.error('API Error:', error);
        if (error.name === 'TypeError' && error.message.includes('fetch')) {
            showError("❌ Failed to connect to server. Please check if the backend is running.");
        } else {
            showError("❌ An unexpected error occurred. Please try again.");
        }
    } finally {
        setLoadingState(false);
    }
}

function displayFullDate(dateStr) {
    try {
        const [dd, mm, yyyy] = dateStr.split("-");
        const full = new Date(`${yyyy}-${mm}-${dd}`);
        const options = { day: "numeric", month: "long", year: "numeric" };
        fullDateEl.textContent = `📆 ${full.toLocaleDateString("en-GB", options)}`;
    } catch (error) {
        console.error('Date formatting error:', error);
    }
}

function resetFields() {
    inputEl.value = "";
    clearResults();
    inputEl.focus();
}

function toggleTheme() {
    dark = !dark;
    document.body.classList.toggle("dark-mode");
    themeToggle.textContent = dark ? "☀️" : "🌙";
    
    // Save preference
    localStorage.setItem('darkMode', dark);
    // --- Force input field to reflow/repaint for background color update ---
    setTimeout(() => {
        inputEl.style.display = 'none';
        // Force reflow
        void inputEl.offsetWidth;
        inputEl.style.display = '';
    }, 0);
}

function checkApiHealth() {
    fetch(`${API_BASE_URL}/health`)
        .then(response => {
            if (!response.ok) {
                console.warn('API health check failed');
            }
        })
        .catch(error => {
            console.warn('API health check failed:', error);
        });
}