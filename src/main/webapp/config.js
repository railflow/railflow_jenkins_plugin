function uploadLicense(descriptorUrl) {
    try {
        const fileSelector = document.getElementById("railflow-license-chooser");
        const data = new FormData();
        data.append("licenseFile", fileSelector.files[0])
        const request = new XMLHttpRequest();
        request.open("POST", descriptorUrl + "/uploadLicenseFile");
        request.setRequestHeader("Jenkins-Crumb", crumb.value);
        const errorDiv = document.getElementById("railflow-license-upload-error");
        const spinner = document.getElementById("railflow-license-upload-spinner");
        errorDiv.classList.remove("error", "success");
        errorDiv.style.display = "none";
        spinner.style.display = "block";
        request.onload = (e) => {
            spinner.style.display = "none";
            if (e.target.status >= 200 && e.target.status < 400) {
                const responseJson = JSON.parse(e.target.responseText);
                setLicenseInfo(responseJson.data);
                showMessage(errorDiv, "success", responseJson.data.message);
            } else {
                showMessage(errorDiv, "error", e.target.responseText);
            }
        }
        request.onerror = (e) => {
            spinner.style.display = "none";
            errorDiv.style.display = "none";
        }
        request.send(data);
    } catch (e) {
        console.error(e);
    }
}

function showMessage(div, style, msg) {
    div.style.display = "block";
    div.classList.add(style);
    div.innerHTML = msg;
}

function activateLicense(descriptorUrl) {
    try {
        const parameters = {};
        parameters["licenseKey"] = document.getElementById('railflow-license-key').value;
        const spinner = document.getElementById("railflow-license-activate-spinner");
        const errorDiv = document.getElementById("railflow-license-activate-error");
        errorDiv.classList.remove("error", "success");
        errorDiv.style.display = "none";
        spinner.style.display = "block";

        new Ajax.Request(descriptorUrl + "/activateLicenseKey", {
            parameters: parameters,
            onComplete: function (rsp) {
                spinner.style.display = "none";
                if (rsp.status >= 200 && rsp.status < 400) {
                    setLicenseInfo(rsp.responseJSON.data);
                    showMessage(errorDiv, "success", rsp.responseJSON.data.message)
                } else {
                    showMessage(errorDiv, "error", rsp.responseText);
                }
            }
        });
    } catch (e) {
        console.error(e);
    }
}

function setLicenseInfo(data) {
    document.getElementById("expirationDate").value = data.expirationDate ? data.expirationDate : "N/A";
    document.getElementById("licenseContent").value = data.licenseContent;
    document.getElementById("onlineActivation").checked = (data.onlineActivation === 'true');
    document.getElementById("trial").checked = (data.trial === 'true');
}
