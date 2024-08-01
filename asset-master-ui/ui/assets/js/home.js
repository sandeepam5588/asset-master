(function () {
  const user_id = localStorage.getItem("user_id");
  const user_name = localStorage.getItem("user_name");

  if (!user_id || user_id === "") {
    window.location.href = "/login.html";
    return;
  }

  const greeting_text_el = document.getElementById("greeting-text");

  if (greeting_text_el) {
    greeting_text_el.textContent = `Hello ${user_name ? user_name : ""}!`;
  }
})();
