let currentMailingListName

async function start() {
    try {
        const response = await fetch("/mailingLists/names")
        const data = await response.json()
        console.log(data)
        console.log(Object.values(data))
        createMailNameList(data)
    } catch (e) {
        alert('There was a problem fetching the mailing list\'s names.')
    }
}

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

async function loadByName(name) {
    if (name !== 'Choose a mailing list') {
        deleteMailingListBtn.removeAttribute("disabled")
        deleteEmailsBtn.removeAttribute("disabled");
        currentMailingListName = name;
        console.log(name);
        let link1 = `/mailingLists/${name}`
        const response1 = await fetch(link1)
        const data1 = await response1.json()

        let link2 = `/mailingLists/${name}/emails`
        const response2 = await fetch(link2)
        const data2 = await response2.json()

        console.log(data1)
        let mailsArray = data2;
        let areaStr = '';
        for (let i = 0; i < mailsArray.length; i++)
            areaStr = areaStr + mailsArray[i] + " ";
        document.getElementById("emailsArea").innerHTML = areaStr;
    } else {
        deleteMailingListBtn.setAttribute('disabled', 'disabled')
    }
}

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
                alert("Mailing list is deleted successfully!");
            })
            .catch(error => console.log(error));
    }
})

deleteEmailsBtn.addEventListener('click', (event) => {
    let isAgree = confirm(`\Delete emails from the ${currentMailingListName} mailing list?`)
    if (isAgree) {
        event.preventDefault();
        let mailsStr = document.getElementById("emailsArea").value;
        let mailsArray = mailsStr.split(" ");
        fetch(`/mailingLists/${currentMailingListName}/emails/delete`, {
            method: 'PATCH',
            body: JSON.stringify({
                mailsArray}),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then (response => response.json())
            .then (() => {
                alert("Emails are deleted successfully!");
            })
            .catch(error => console.log(error));
    }
})

let addMailsBtn = document.getElementById("addMailsBtn");
addMailsBtn.addEventListener('click', (event) => {
    event.preventDefault();
    let mailsStr = document.getElementById("mails").value;
    let mailsArray = mailsStr.split(" ");
    for (let i = 0; i < mailsArray.length; i++)
        console.log(mailsArray[i])
    fetch(`/mailingLists/${currentMailingListName}/emails/add`, {
        method: 'PATCH',
        body: JSON.stringify({
            mailsArray}),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then (response => response.json())
        .then (() => {
            alert(`Emails are added to "${currentMailingListName}" mailing list successfully!`);
        })
        .catch(error => console.log(error));
})

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
            alert(`Emails are added to "${currentMailingListName}" mailing list successfully!`);
        })
        .catch(error => console.log(error));
})

start()