(function () {
  const singup_form_elements = {
    form: document.getElementById("signup-form"),
    name: document.getElementById("name"),
    email: document.getElementById("email"),
    password: document.getElementById("password"),
    confirm_password: document.getElementById("confirm-password"),
  };

  if (singup_form_elements.form) {
    singup_form_elements.form.addEventListener("submit", async (e) => {
      e.preventDefault();

      const signup_values = {
        name: singup_form_elements.name.value,
        email: singup_form_elements.email.value,
        password: singup_form_elements.password.value,
        confirm_password: singup_form_elements.confirm_password.value,
      };

      //   console.log("these are the values: ", {
      //     name: signup_values.name,
      //     email: signup_values.email,
      //     password: signup_values.password,
      //     confirm_password: signup_values.confirm_password,
      //   });

      if (
        !signup_values.name ||
        !signup_values.email ||
        !signup_values.password ||
        !signup_values.confirm_password
      ) {
        alert(
          "Missing requied fields. Please make sure all fields are entered!"
        );
        return;
      }

      // Check if passwords are correct:
      if (
        signup_values.password !== signup_values.confirm_password ||
        signup_values.password.length === 0
      ) {
        alert("Passwords do not match");
        return;
      }

      try {
        const signup_response = await axios({
          method: "POST",
          url: `${asset_master_config.url}/asset-master/create-account`,
          data: {
            userName: signup_values.name,
            email: signup_values.email,
            password: signup_values.password,
          },
        });

        alert("Signup Successful. Please login now.");
        window.location.href = "/login.html";
        return;
      } catch (error) {
        if (
          error.response.data.code instanceof Array &&
          error.response.data.code.indexOf("U_007") !== -1
        ) {
          alert(
            "Email is already registered. Please login or try another email"
          );
          return;
        }
      }
    });
  }
})();
