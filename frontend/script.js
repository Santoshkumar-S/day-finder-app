const inputEl = document.getElementById("dateInput");
const resultEl = document.getElementById("result");

// Clear result when user clicks inside input
inputEl.addEventListener("focus", () => {
  resultEl.textContent = "";
  resultEl.classList.remove("shake");
});

function findDay() {
  const date = inputEl.value.trim();

  if (!date) {
    resultEl.textContent = "Please enter a date.";
    return;
  }

  resultEl.textContent = "⏳ Finding day...";
  resultEl.classList.remove("shake");

  // Wait ~1 second before fetching
  setTimeout(() => {
    fetch(`http://localhost:8080/api/day?date=${date}`)
      .then(response => response.json())
      .then(data => {
        if (data.day) {
          resultEl.textContent = `📅 Day: ${data.day}`;
          resultEl.classList.add("shake");
        } else {
          resultEl.textContent = `⚠️ ${data.error || "Invalid input."}`;
        }
      })
      .catch(() => {
        resultEl.textContent = "❌ Could not connect to backend.";
      });
  }, 1000); // 1 second delay
}