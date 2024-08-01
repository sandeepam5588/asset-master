async function init() {
  const form_el = document.getElementById("login-form");
  const email_input_el = document.getElementById("email");
  const password_input_el = document.getElementById("password");
  const submit_button_el = document.getElementById("submit-btn");

  // Check if the user is already logged in: Redirect to home if already logged in.
  const _user_id = localStorage.getItem("user_id");

  if (_user_id && _user_id !== "") {
    window.location.href = `/`;
  }

  if (form_el) {
    form_el.addEventListener("submit", async (e) => {
      e.preventDefault();

      const email = email_input_el.value;
      const password = password_input_el.value;

      try {
        const response = await axios({
          method: "POST",
          url: `${asset_master_config.url}/asset-master/login`,
          data: {
            userName: email,
            password: password,
          },
        });

        console.log("this is the user: ", response.data);

        localStorage.setItem("user_id", response.data.userID);
        localStorage.setItem("user_name", response.data.userName);
        alert("User successfully logged in!");

        window.location.href = `/`;
      } catch (error) {
        if (error.response.data.code instanceof Array) {
          alert("Invalid Email or Password");
        }
      }
    });
  }
}

init();
