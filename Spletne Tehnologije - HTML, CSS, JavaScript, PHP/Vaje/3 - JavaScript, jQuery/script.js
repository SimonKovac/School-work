"use strict";

function domRemoveParticipant(event) {
    // TODO
    const table = document.getElementById("participant-table");
    if (confirm("Are you sure?")) table.deleteRow(event.target.parentNode.rowIndex);
}

function domAddParticipant(participant) {
    const table = document.getElementById("participant-table");
    const tr = document.createElement("tr");
    tr.ondblclick = domRemoveParticipant;
    table.appendChild(tr);

    ["first", "last", "role"].forEach(attribute => {
        let td = document.createElement("td");
        td.innerText = participant[attribute];
        tr.appendChild(td);
    });
}

function addParticipant() {
    // TODO: Get values
    const first = document.getElementById("first").value;
    const last = document.getElementById("last").value;
    const role = document.getElementById("role").value;
    
    // TODO: Set input fields to empty values
    // ...
    document.getElementById("first").value = "";
    document.getElementById("last").value = "";
    document.getElementById("role").value = "Student";
    
    // Create participant object
    const participant = {
        first: first,
        last: last,
        role: role
    };

    // Add participant to the HTML
    domAddParticipant(participant);

    // Move cursor to the first name input field
    document.getElementById("first").focus();
}

document.addEventListener("DOMContentLoaded", () => {
    // This function is run after the page contents have been loaded
    // Put your initialization code here
    document.getElementById("addButton").onclick = addParticipant;
})

// The jQuery way of doing it
$(document).ready(() => {
    // Alternatively, you can use jQuery to achieve the same result
});
