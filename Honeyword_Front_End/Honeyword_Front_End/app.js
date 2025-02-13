const Login_btn = document.querySelector("#Login-btn");
const Register_btn = document.querySelector("#Register-btn");
const container = document.querySelector(".container");

Register_btn.addEventListener("click", () => {
  container.classList.add("Register-mode");
});

Login_btn.addEventListener("click", () => {
  container.classList.remove("Register-mode");
});

function updatePlaceholder() {
  var select = document.getElementById("securityQuestionSelect");
  var selectedOption = select.options[select.selectedIndex].text;
  document.getElementById("securityAnswerInput").placeholder = selectedOption;
}

function clearPlaceholder() {
  document.getElementById("securityAnswerInput").placeholder = "";
}
