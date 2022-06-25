let currentMailingListName

/**
 * Fetch mailing lists
 */
async function start() {
    try {
        const response = await fetch("/mailingLists/names")
        const data = await response.json()
        createMailNameList(data)
    } catch (e) {
        alert('There was a problem fetching the mailing list\'s names.')
    }
}

/**
 * Load mailing list in selector form
 */
function createMailNameList(nameList) {
    document.getElementById("listName").innerHTML = `
    <select id="select" onchange="loadByName(this.value)">
        <option>Choose a mailing list</option>
        ${Object.values(nameList).map(name => {
        return `<option>${name}</option>`
    }).join('')}
    </select>
    `
}

let deleteMailingListBtn = document.getElementById("deleteMailingListBtn");
let deleteEmailsBtn = document.getElementById("deleteEmailsBtn");

/**
 * Load chosen mailing list
 */
async function loadByName(name) {
    if (name !== 'Choose a mailing list') {
        deleteMailingListBtn.removeAttribute("disabled")
        deleteEmailsBtn.removeAttribute("disabled");
        currentMailingListName = name;
        let link1 = `/mailingLists/${name}`
        const response1 = await fetch(link1)
        const data1 = await response1.json()

        let link2 = `/mailingLists/${name}/emails`
        const response2 = await fetch(link2)
        const data2 = await response2.json()

        let mailsArray = data2;
        let areaStr = '';
        for (let i = 0; i < mailsArray.length; i++)
            areaStr = areaStr + mailsArray[i] + " ";
        document.getElementById("emailsArea").innerHTML = areaStr;
    } else {
        deleteMailingListBtn.setAttribute('disabled', 'disabled')
    }
}

/**
 * Delete a mailing list
 */
deleteMailingListBtn.addEventListener('click', (event) => {
    let isAgree = confirm(`Delete the "${currentMailingListName}" mailing list?`)
    if (isAgree) {
        event.preventDefault();
        fetch(`/mailingLists/${currentMailingListName}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then (response => response.json())
            .then (() => {
            })
            .catch(error => console.log(error));
    }
})

/**
 * Delete email(s) from a mailing list
 */
deleteEmailsBtn.addEventListener('click', (event) => {
    let isAgree = confirm(`\Delete emails from the ${currentMailingListName} mailing list?`)
    if (isAgree) {
        event.preventDefault();
        let mailsStr = document.getElementById("emailsArea").value;
        let mailsArray = mailsStr.split(" ").filter(el => el !== "");
        fetch(`/mailingLists/${currentMailingListName}/emails/delete`, {
            method: 'PATCH',
            body: JSON.stringify(
                mailsArray),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then (response => response.json())
            .then (() => {
            })
            .catch(error => console.log(error));
    }
})

/**
 * Add email(s) to a mailing list
 */
let addMailsBtn = document.getElementById("addMailsBtn");
addMailsBtn.addEventListener('click', (event) => {
    event.preventDefault();
    let mailsStr = document.getElementById("mails").value;
    let mailsArray = mailsStr.split(" ").filter(el => el !== "");
    if (validateEmails(mailsArray)) {
        fetch(`/mailingLists/${currentMailingListName}/emails/add`, {
            method: 'PATCH',
            body: JSON.stringify(
                mailsArray),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then (response => response.json())
            .then (() => {

            })
            .catch(error => console.log(error));
    } else
        alert("Emails are not in correct form. Try again");
})

function validateEmails(mailsArray) {
    for (let email of mailsArray) {
        if (!isEmail(email))
            return false;
    }
    return true;
}

function isEmail(str) {
    let pattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
    return !!(str.match(pattern));
}

/**
 * Change a mailing list's name
 */
let newMailingListNameBtn = document.getElementById("newMailingListNameBtn");
addMailsBtn.addEventListener('click', (event) => {
    event.preventDefault();
    let newName = document.getElementById("newMailingListName").value;
    fetch(`/mailingLists`, {
        method: 'PUT',
        body: JSON.stringify({
            'emails': mailsArray,
            'textIdentifier': mailsName}),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then (response => response.json())
        .then (() => {
        })
        .catch(error => console.log(error));
})

start()