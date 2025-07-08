function findDay() {
  const input = document.getElementById("dateInput").value;
  const result = document.getElementById("result");

  if (!input) {
    result.textContent = "Please select a date.";
    return;
  }
0
  const day = new Date(input).toLocaleDateString("en-US", { weekday: 'long' });
  result.textContent = `That day is a ${day}`;
}
