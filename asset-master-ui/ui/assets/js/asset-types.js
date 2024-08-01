async function init() {
  const asset_type_card_container_el = document.getElementById(
    "asset-type-card-container"
  );

  const add_asset_type_form_el = document.getElementById("add-asset-type-form");
  const asset_type_name_input_el = document.getElementById("asset-type-name");

  const user_id = localStorage.getItem("user_id");

  if (!user_id || user_id === "") {
    return (window.location.href = "/login.html");
  }

  // 1. LOAD ASSETS ON UI
  const asset_types_response = await axios({
    method: "GET",
    url: `${asset_master_config.url}/asset-master/asset-type-list`,
  });

  if (asset_types_response.data instanceof Array) {
    asset_types_response.data.forEach((cur) => {
      const asset_card_HTML = `<div class="asset-types__card">
      <img
        src="/assets/img/360_F_385956366_Zih7xDcSLqDxiJRYUfG5ZHNoFCSLMRjm.jpg"
        alt="Asset Type Image"
        class="asset-types__image"
      />

      <div class="asset-types__description-box">
        <h2 class="asset-types__name">${cur.assetType}</h2>
        <p class="asset-types__id">${cur.assetTypeID}</p>
      </div>
    </div>`;

      if (asset_type_card_container_el) {
        asset_type_card_container_el.insertAdjacentHTML(
          "beforeend",
          asset_card_HTML
        );
      }
    });
  }

  // 2. Logic for adding new asset type
  if (add_asset_type_form_el) {
    add_asset_type_form_el.addEventListener("submit", async (e) => {
      e.preventDefault();

      const asset_type_name = asset_type_name_input_el.value;
      const user_id = localStorage.getItem("user_id");

      try {
        const response = await axios({
          method: "POST",
          url: `${asset_master_config.url}/asset-master/add-asset-type`,
          data: {
            assetType: asset_type_name,
            userID: user_id,
          },
        });

        alert("Asset type successfully added");
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
