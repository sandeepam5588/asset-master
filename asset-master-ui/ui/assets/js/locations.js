async function init() {
  const location_card_container_el = document.getElementById(
    "location-cards-container"
  );

  const add_location_form_el = document.getElementById("add-location-form");
  const location_name_input_el = document.getElementById("input-location-name");

  const user_id = localStorage.getItem("user_id");

  if (!user_id || user_id === "") {
    return (window.location.href = "/login.html");
  }

  // 1. LOAD ASSETS ON UI
  const location_responses = await axios({
    method: "GET",
    url: `${asset_master_config.url}/asset-master/location-list`,
  });

  if (location_responses.data instanceof Array) {
    location_responses.data.forEach((cur) => {
      const location_card_HTML = `<div class="asset-types__card">
      <img
        src="/assets/img/image-location.jpeg"
        alt="Asset Type Image"
        class="asset-types__image"
      />

      <div class="asset-types__description-box">
        <h2 class="asset-types__name">${cur.location}</h2>
        <p class="asset-types__id">${cur.locationID}</p>
      </div>
    </div>`;

      if (location_card_container_el) {
        location_card_container_el.insertAdjacentHTML(
          "beforeend",
          location_card_HTML
        );
      }
    });
  }

  // 2. Logic for adding new asset type
  if (add_location_form_el) {
    add_location_form_el.addEventListener("submit", async (e) => {
      e.preventDefault();

      const location_name = location_name_input_el.value;
      const user_id = localStorage.getItem("user_id");

      try {
        const response = await axios({
          method: "POST",
          url: `${asset_master_config.url}/asset-master/add-location`,
          data: {
            location: location_name,
            userID: user_id,
          },
        });

        alert("Location successfully added");
        window.location.reload();
      } catch (error) {
        if (error.response.data.code instanceof Array) {
          if (error.response.data.code.indexOf("U_002") !== -1) {
            alert("User Does not exist");

            window.location.href = "/login.html";
            return;
          }
        }
        alert(error.response.data.messages.join(" && "));
      }
    });
  }
}

init();
