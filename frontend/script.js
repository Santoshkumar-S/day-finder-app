const inputEl = document.getElementById("dateInput");
const resultEl = document.getElementById("result");
const fullDateEl = document.getElementById("fullDate");

// Clear result when user clicks inside input
inputEl.addEventListener("focus", () => {
  resultEl.textContent = "";
  fullDateEl.textContent = "";
  resultEl.classList.remove("shake");
});

function findDay() {
  const inputEl = document.getElementById("dateInput");
  const resultEl = document.getElementById("result");
  const fullDateEl = document.getElementById("fullDate");

  const date = inputEl.value.trim();
  resultEl.textContent = "";
  fullDateEl.textContent = "";

  if (!date) {
    resultEl.textContent = "⚠️ Please enter a date.";
    return;
  }

  resultEl.textContent = "⏳ Finding day...";

  setTimeout(() => {
    fetch(`http://localhost:8080/api/day?date=${date}`)
      .then(res => res.json())
      .then(data => {
        if (data.day) {
          resultEl.textContent = `✅ Day: ${data.day}`;
          resultEl.classList.add("shake");

          // Convert DD-MM-YYYY → 9 July 2025
          const [dd, mm, yyyy] = date.split("-");
          const full = new Date(`${yyyy}-${mm}-${dd}`);
          const options = { day: "numeric", month: "long", year: "numeric" };
          fullDateEl.textContent = `📆 ${full.toLocaleDateString("en-GB", options)}`;
        } else {
          resultEl.textContent = data.error || "Invalid date format.";
        }
      })
      .catch(() => {
        resultEl.textContent = "❌ Failed to connect to backend.";
      });
  }, 400);
}

function resetFields() {
  inputEl.value = "";
  resultEl.textContent = "";
  fullDateEl.textContent = "";
  inputEl.focus();
}