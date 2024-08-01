async function init() {
  const global_state = {};

  const asset_list_el = document.getElementById("asset-list");

  const update_asset_type_popup_el =
    document.getElementById("update-asset-popup");

  const form_elements = {
    add_new_asset_form: document.getElementById("add-new-asset-form"),
    asset_name: document.getElementById("asset-name"),
    asset_description: document.getElementById("asset-description"),
    asset_status_dropdown: document.getElementById("asset-status-dropdown"),
    asset_type_dropdown: document.getElementById("asset-type-dropdown"),
    asset_location_dropdown: document.getElementById("asset-location-dropdown"),
  };

  const update_form_elements = {
    asset_form: document.getElementById("update-asset-form"),
    asset_name: document.getElementById("update-asset-name"),
    asset_description: document.getElementById("update-asset-description"),
    asset_status_dropdown: document.getElementById(
      "update-asset-status-dropdown"
    ),
    asset_type_dropdown: document.getElementById("update-asset-type-dropdown"),
    asset_location_dropdown: document.getElementById(
      "update-asset-location-dropdown"
    ),
  };

  const assets_response = await axios({
    method: "GET",
    url: `${asset_master_config.url}/asset-master/asset-list`,
  });

  if (assets_response.data instanceof Array) {
    assets_response.data.forEach((cur) => {
      const HTML = `<div class="asset-list__row">
      <div class="asset-list__row-cell">${cur.assetName}</div>

      <div class="asset-list__row-cell">${cur.status}</div>

      <div class="asset-list__row-cell">${cur.assetType}</div>

      <div class="asset-list__row-cell">${cur.location}</div>

      <div
        class="asset-list__row-cell asset-list__row-cell--button-container"
      >
        <button class="asset-list__button" id="update-${cur.assetID}">Update</button>
      </div>
      <div
        class="asset-list__row-cell asset-list__row-cell--button-container"
      >
        <button class="asset-list__button" id="delete-${cur.assetID}">Delete</button>
      </div>
      <div
        class="asset-list__row-cell asset-list__row-cell--button-container"
      >
        <button class="asset-list__button" id="view-${cur.assetID}">View</button>
      </div>
    </div>`;

      asset_list_el.insertAdjacentHTML("beforeend", HTML);
    });
  }

  // LOAD ASSET TYPE DROPDOWN OPTIONS
  if (form_elements.asset_type_dropdown) {
    const asset_types_response = await axios({
      method: "GET",
      url: `${asset_master_config.url}/asset-master/asset-type-list`,
    });

    if (asset_types_response.data instanceof Array) {
      asset_types_response.data.forEach((cur_asset_type) => {
        const option = new Option(
          cur_asset_type.assetType,
          cur_asset_type.assetTypeID
        );
        form_elements.asset_type_dropdown.add(option, undefined);
      });
    }
  }

  // LOAD LOCATION DROPDOWN OPTIONS
  if (form_elements.asset_location_dropdown) {
    const asset_types_response = await axios({
      method: "GET",
      url: `${asset_master_config.url}/asset-master/location-list`,
    });

    if (asset_types_response.data instanceof Array) {
      asset_types_response.data.forEach((cur_asset_type) => {
        const option = new Option(
          cur_asset_type.location,
          cur_asset_type.locationID
        );
        form_elements.asset_location_dropdown.add(option, undefined);
      });
    }
  }

  // FOR UPDATE FORM
  // LOAD ASSET TYPE DROPDOWN OPTIONS
  if (update_form_elements.asset_type_dropdown) {
    const asset_types_response = await axios({
      method: "GET",
      url: `${asset_master_config.url}/asset-master/asset-type-list`,
    });

    if (asset_types_response.data instanceof Array) {
      asset_types_response.data.forEach((cur_asset_type) => {
        const option = new Option(
          cur_asset_type.assetType,
          cur_asset_type.assetTypeID
        );
        update_form_elements.asset_type_dropdown.add(option, undefined);
      });
    }
  }

  // FOR UPDATE FORM
  // LOAD LOCATION DROPDOWN OPTIONS
  if (update_form_elements.asset_location_dropdown) {
    const asset_types_response = await axios({
      method: "GET",
      url: `${asset_master_config.url}/asset-master/location-list`,
    });

    if (asset_types_response.data instanceof Array) {
      asset_types_response.data.forEach((cur_asset_type) => {
        const option = new Option(
          cur_asset_type.location,
          cur_asset_type.locationID
        );
        update_form_elements.asset_location_dropdown.add(option, undefined);
      });
    }
  }

  // Add Asset Event Listener and Handler
  if (form_elements.add_new_asset_form) {
    form_elements.add_new_asset_form.addEventListener("submit", async (e) => {
      e.preventDefault();

      const asset_name = form_elements.asset_name.value;
      const asset_description = form_elements.asset_description.value;
      const asset_status = form_elements.asset_status_dropdown.value;
      const asset_type = form_elements.asset_type_dropdown.value;
      const asset_location = form_elements.asset_location_dropdown.value;
      const user_id = localStorage.getItem("user_id");

      // console.log({
      //   asset_name,
      //   asset_status,
      //   asset_type,
      //   asset_location,
      //   user_id,
      // });

      if (
        !asset_name ||
        !asset_description ||
        !asset_status ||
        !asset_type ||
        !asset_location ||
        !user_id
      ) {
        return alert(
          "Missing fields. Please make sure to provide all the required fields"
        );
      }

      try {
        const response = await axios({
          method: "POST",
          url: `${asset_master_config.url}/asset-master/add-asset`,
          data: {
            assetTypeID: asset_type,
            assetName: asset_name,
            status: asset_status,
            locationID: asset_location,
            description: asset_description,
            userID: user_id,
          },
        });

        alert("New asset sucessfully created!");
        return window.location.reload();
      } catch (error) {
        alert(error.response.data.messages.join(" & "));

        if (
          error.response.data.code instanceof Array &&
          error.response.data.code.indexOf("U_002") !== -1
        ) {
          window.location.href = "/login.html";
        }
      }
    });
  }

  // Delete Asset
  if (asset_list_el) {
    asset_list_el.addEventListener("click", async (e) => {
      e.preventDefault();

      if (e.target.id.startsWith("delete-")) {
        const delete_id = e.target.id.split("-")[1];
        const user_id = localStorage.getItem("user_id");

        const asset_name =
          e.target?.parentNode?.parentNode?.firstElementChild?.innerText;

        try {
          const delete_response = await axios({
            method: "DELETE",
            url: `${asset_master_config.url}/asset-master/remove-asset/`,
            data: {
              assetID: delete_id,
              userID: user_id,
            },
          });

          alert(`Asset deleted: ${asset_name}`);
          window.location.reload();
          return;
        } catch (error) {
          alert("Error Deleting Asset");
          return;
        }
      }
    });
  }

  // Open Update Asset Popup
  if (asset_list_el) {
    asset_list_el.addEventListener("click", async (e) => {
      e.preventDefault();

      if (e.target.id.startsWith("update-")) {
        const update_id = e.target.id.split("-")[1];
        const asset_name =
          e.target?.parentNode?.parentNode?.firstElementChild?.innerText;

        global_state.asset_id = update_id;

        update_asset_type_popup_el.classList.remove([
          "update-asset-type__popup--visible",
          "update-asset-type__popup--hidden",
        ]);

        update_asset_type_popup_el.classList.add([
          "update-asset-type__popup--visible",
        ]);
      }
    });
  }

  // Update Asset Logic
  if (update_form_elements.asset_form) {
    update_form_elements.asset_form.addEventListener("submit", async (e) => {
      e.preventDefault();

      // Get all the values
      const asset_name = update_form_elements.asset_name.value;
      const asset_description = update_form_elements.asset_description.value;
      const asset_status = update_form_elements.asset_status_dropdown.value;
      const asset_type = update_form_elements.asset_type_dropdown.value;
      const asset_location = update_form_elements.asset_location_dropdown.value;
      const asset_id = global_state.asset_id;
      const user_id = localStorage.getItem("user_id");

      //   console.log("from the update form listener", {
      //     asset_name,
      //     asset_description,
      //     asset_status,
      //     asset_type,
      //     asset_location,
      //     asset_id,
      //   });

      try {
        const update_asset_response = await axios({
          method: "PUT",
          url: `${asset_master_config.url}/asset-master/update-asset`,
          data: {
            assetID: asset_id,
            assetName: asset_name,
            assetTypeID: asset_type,
            status: asset_status,
            locationID: asset_location,
            description: asset_description,
            userID: user_id,
          },
        });

        alert("Asset Successfully Updated!");
        window.location.reload();

        return;
      } catch (error) {
        if (error.response.data) {
          if (
            error.response.data.code instanceof Array &&
            error.response.data.code.indexOf("U_002") !== -1
          ) {
            alert("Invalid User");
            localStorage.setItem("user_id", "");
            window.location.href = "/login.html";
          }
        }
      }
    });
  }

  // View Asset
  if (asset_list_el) {
    asset_list_el.addEventListener("click", async (e) => {
      e.preventDefault();

      if (e.target.id.startsWith("view-")) {
        const view_id = e.target.id.split("-")[1];
        // const asset_name =
        //   e.target?.parentNode?.parentNode?.firstElementChild?.innerText;

        window.location.href = `/view-asset.html#${view_id}`;
      }
    });
  }
}

init();
