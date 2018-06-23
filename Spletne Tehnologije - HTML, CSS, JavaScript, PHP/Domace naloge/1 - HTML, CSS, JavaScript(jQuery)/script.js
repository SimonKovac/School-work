"use strict"

function deleteContact(event) {
    const table = document.getElementById("contacttable");
    const currentRowIndex = event.target.parentNode.parentNode.rowIndex;
    table.deleteRow(currentRowIndex);
    const editSelectName = document.getElementById("ename")
    editSelectName.options.remove(currentRowIndex);
    if(nameSelect.options[nameSelect.selectedIndex].value == "") disableEditForm();
    storeTable();
}

function showCreateForm() {
    $('#createform').show();
    $('#showcreate').hide();
    hideEditForm();
}

function hideCreateForm() {
    $('#createform').hide();
    $('#showcreate').show();
    clearCreateForm();
}

function showEditForm() {
    $('#editform').show();
    $('#showedit').hide();
    hideCreateForm();
    const nameSelect = document.getElementById("ename")
    if(nameSelect.options[nameSelect.selectedIndex].value != "") enableEditForm();
}

function hideEditForm() {
    $('#editform').hide();
    $('#showedit').show();
}

function disableEditForm() {
    document.getElementById("enumber").disabled = true;
    document.getElementById("eaddress").disabled = true;
    document.getElementById("eemail").disabled = true;
}

function enableEditForm() {
    document.getElementById("enumber").disabled = false;
    document.getElementById("eaddress").disabled = false;
    document.getElementById("eemail").disabled = false;
}

function clearCreateForm() {
    document.getElementById("cname").value = "";
    document.getElementById("cnumber").value = "";
    document.getElementById("caddress").value = "";
    document.getElementById("cemail").value = "";
}

function clearEditForm() {
    document.getElementById("ename").value = "";
    document.getElementById("enumber").value = "";
    document.getElementById("eaddress").value = "";
    document.getElementById("eemail").value = "";
}

function addContact(contact) {
    const table = document.getElementById("contacttable");
    const tr = document.createElement("tr");
    
    ["name", "number", "address", "email"].forEach(attribute => {
        let td = document.createElement("td");
        td.innerText = contact[attribute];
        if(attribute === "email" || attribute === "address") td.classList.add("collapsable");
        tr.appendChild(td);
    });

    const deleteButton = document.createElement("button");
    deleteButton.innerText = "X";
    deleteButton.addEventListener("click", deleteContact);
    const deleteButtonCell = document.createElement("td");
    deleteButtonCell.classList.add("button");
    deleteButtonCell.appendChild(deleteButton);
    tr.appendChild(deleteButtonCell);
    table.appendChild(tr)

    const editSelectName = document.getElementById("ename");
    const newOption = document.createElement("option");
    newOption.text = contact.name;
    editSelectName.add(newOption);
    hideCreateForm();
    storeTable();
}

function clickCreate() {
    const name = document.getElementById("cname").value;
    const number = document.getElementById("cnumber").value;
    const address = document.getElementById("caddress").value;
    const email = document.getElementById("cemail").value;

    const contact = {
        name: name,
        number: number,
        address: address,
        email: email
    }
    if (validateCreate()){
        addContact(contact);
    }
    
}

function clickEdit() {
    const editSelect = document.getElementById("ename");
    const currentRowIndex = editSelect.selectedIndex;
    const name = editSelect.options[currentRowIndex];
    const number = document.getElementById("enumber").value;
    const address = document.getElementById("eaddress").value;
    const email = document.getElementById("eemail").value;

    const contact = {
        name: name,
        number: number,
        address: address,
        email: email
    }

    if(validateEdit()){
        const table = document.getElementById("contacttable");
        const row = table.rows[currentRowIndex];

        row.cells[1].innerText = number || row.cells[1].innerText;
        row.cells[2].innerText = address || row.cells[2].innerText;
        row.cells[3].innerText = email || row.cells[3].innerText;
        
        hideEditForm();
        storeTable();
    }
}

function validateCreate(){
    const name = document.getElementById("cname").value;
    const number = parseInt(document.getElementById("cnumber").value);
    const address = document.getElementById("caddress").value;
    const email = document.getElementById("cemail").value;
    let valid = true;

    if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))){
        document.getElementById("cemailerror").innerText = "Invalid email";
        valid = false;
    } else{
        document.getElementById("cemailerror").innerText = "";
    }

    if (isNaN(number) || number.toString().length != 8) {
        document.getElementById("cnumbererror").innerText = "Invalid number";
        valid = false;
    } else {
        document.getElementById("cnumbererror").innerText = "";
    }

    return valid;
}

function validateEdit(){
    const number = parseInt(document.getElementById("enumber").value);
    const address = document.getElementById("eaddress").value;
    const email = document.getElementById("eemail").value;
    let valid = true;

    if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))){
        document.getElementById("eemailerror").innerText = "Invalid email";
        valid = false;
    } else{
        document.getElementById("eemailerror").innerText = "";
    }

    if (isNaN(number) || number.toString().length != 8) {
        document.getElementById("enumbererror").innerText = "Invalid number";
        valid = false;
    } else {
        document.getElementById("enumbererror").innerText = "";
    }

    return valid;
}

function storeTable() {
    if (typeof(Storage) !== "undefined") {
        let contacts = {};
        const table = document.getElementById("contacttable");
        const attributes = ["name", "number", "address", "email"];
        for(let i = 0, row; row = table.rows[i]; i++){
            let newContact = {};
            for(let j = 0; j < 4; j++){
                newContact[attributes[j]] = row.cells[j].innerText;
            }
            contacts[i] = newContact;
        }
        console.log(JSON.stringify(contacts));
        localStorage.setItem("contacttable", JSON.stringify(contacts));
    }
}

function fetchTable() {
    if (typeof(Storage) !== "undefined") {
        let contacts = JSON.parse(localStorage.getItem("contacttable"));
        if (contacts !== null) {
            for(let i = 0, row; row = contacts[i]; i++){
                const contact = {
                    name: row["name"],
                    number: row["number"],
                    address: row["address"],
                    email: row["email"]
                }
                addContact(contact);
            }
        }
    }
}

$(function() {
    $('#createform').hide();

    $('#showcreate').click(showCreateForm);

    $('#createsubmit').click(clickCreate);

    $('#createcancel').click(hideCreateForm);

    $('#editform').hide();

    disableEditForm();

    $('#showedit').click(showEditForm);

    $('#editsubmit').click(clickEdit);

    $('#ename').change(enableEditForm);
    
    $('#editcancel').click(hideEditForm);

    fetchTable();
});