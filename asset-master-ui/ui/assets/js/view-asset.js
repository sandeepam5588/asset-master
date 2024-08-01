(async function () {
  const history_list_el = document.getElementById("asset-history-list");

  const details_card_elements = {
    asset_name: document.getElementById("details-asset-name"),
    asset_description: document.getElementById("details-asset-description"),
    asset_type: document.getElementById("details-asset-type"),
    asset_date: document.getElementById("details-asset-date"),
    asset_from_location: document.getElementById("details-asset-from-location"),
    asset_to_location: document.getElementById("details-asset-to-location"),
    asset_status: document.getElementById("details-asset-status"),
    asset_username: document.getElementById("details-asset-username"),
  };

  // Extract view_id from the location target
  const view_id = window.location.href.split("#")[1];

  if (!view_id) {
    window.location.href = "/assets.html";
  }

  try {
    const asset_details_response = await axios({
      method: "GET",
      url: `${asset_master_config.url}/asset-master/get-asset-history/${view_id}`,
    });

    // Add Details to Card
    details_card_elements.asset_name.innerText =
      asset_details_response.data[0].assetName;
    details_card_elements.asset_description.innerText =
      asset_details_response.data[0].description;
    details_card_elements.asset_type.innerText =
      asset_details_response.data[0].assetType;
    details_card_elements.asset_date.innerText =
      asset_details_response.data[0].transactionDate.split("T")[0];
    // details_card_elements.asset_from_location.innerText = asset_details_response.data[0].fromLocationName;
    details_card_elements.asset_to_location.innerText =
      asset_details_response.data[0].toLocationName;
    details_card_elements.asset_status.innerText =
      asset_details_response.data[0].updateStatus;
    details_card_elements.asset_username.innerText =
      asset_details_response.data[0].userName;

    // Add History Items in List
    asset_details_response.data.forEach((cur) => {
      const history_HTML = `<div class="asset-history__row">
        <div class="asset-history__cell">${
          cur.fromLocationName ? cur.fromLocationName : "Inventory"
        }</div>

        <div class="asset-history__cell">${cur.toLocationName}</div>

        <div class="asset-history__cell">${
          cur.transactionDate.split("T")[0]
        }</div>

        <div class="asset-history__cell">${cur.updateStatus}</div>
      </div>`;

      if (history_list_el) {
        history_list_el.insertAdjacentHTML("beforeend", history_HTML);
      }
    });
  } catch (error) {
    if (
      error.response.data.code instanceof Array &&
      error.response.data.code.indexOf("U_014") !== -1
    ) {
      alert("Asset with that id does not exist");
      window.location.href = "/assets.html";
    }
  }
})();
