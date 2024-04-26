let wsProtocol = "ws://";
if(window.location.protocol === "https:") {
    wsProtocol = "wss://";
}
const wsUri = wsProtocol + document.location.host + document.location.pathname + "/endpoint";
const websocket = new WebSocket(wsUri);

websocket.onopen = function(event) {
    $("#errorText").addClass("d-none");
    console.log("WS Open: " + wsUri);
}

websocket.onclose = function(event) {
    displayError("Lost internet connection");
}

websocket.onerror= function(event) {
    displayError("ERROR: " + event.data + ". See browser's developer console for more information.");
}

websocket.onmessage = function(event) {
    // runs when a message is received
    // console.log(event.data);
    updateTextArea(event.data, "in");
}

function updateTextArea(data, inOut) {
    // Parse the data as JSON so the fields can be accessed
    var json = JSON.parse(data);
    // Use the JSON notation to retrieve the data fields
    var name = json.name;
    var message = json.message;
    // Build the text to display then show it
    var out = (inOut == "in") ? "<div class=\"in\">" : "<div class=\"out\">";
    out += "<p>" + message + "</p><span>";
    out += (inOut == "in") ? name  : "Me";
    out += "</span></div>"
    var textArea = document.getElementById("messages");
    textArea.innerHTML = textArea.innerHTML + out;
    // Attempt to move the scrolling of the textarea to show the lowest item
    // The effectiveness of this varies by browser
    textArea.scrollTop = textArea.scrollHeight;
}

function displayError(msg) {
    const errDiv = document.getElementById("errorText");
    errDiv.innerHTML = msg;
    $("#errorText").removeClass("d-none");
}

const messageForm = document.getElementById("messageForm");
messageForm.addEventListener("submit", function(event) {
    event.preventDefault();
    // Remove any error text
    displayError("");
    $("#errorText").addClass("d-none");

    const userName = document.getElementById("userName").value;
    if(userName === "") {
        displayError("Name is required");
        return;
    }

    const message = document.getElementById("message").value;
    if(message === "") {
        displayError("Message is required");
        return;
    }

    const json = JSON.stringify({
        "name":userName,
        "message":message
    });
    sendJson(json);
    prepMessageBox();
    updateTextArea(json, "out");
});

function isOpen(websocket) {
    return websocket.readyState === websocket.OPEN;
}

function sendJson(json) {
    // runs when a message is sent
    if(isOpen(websocket)) {
        // console.log("Sending: " + json)
        websocket.send(json);
    }
}

function prepMessageBox() {
    const message = document.getElementById("message");
    message.value = "";
    message.focus();
}