function logout() {
  const logout_btn = document.getElementById("logout-btn");

  if (logout_btn) {
    logout_btn.addEventListener("click", (e) => {
      e.preventDefault();

      localStorage.setItem("user_id", "");
      localStorage.removeItem("user_name");
      alert("Logged Out!");
      window.location.href = "/login.html";
    });
  }
}

logout();
