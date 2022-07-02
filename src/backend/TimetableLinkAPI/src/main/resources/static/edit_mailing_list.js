let currentMailingListName
let currentMailingListEmails
let currentPatchEmails
let currentAddEmails
let currentDeleteEmails
let currentToken

/**
 * Fetch mailing lists
 */
async function start () {
  try {
    const response = await fetch('/mailingLists/names', {
      headers: {
        'Authorization': currentToken
      }
    })
    const data = await response.json()
    createMailNameList(data)
  } catch (e) {
    //alert('There was a problem fetching the mailing list\'s names.')
  }
}

/**
 * Load mailing list in selector form
 */
function createMailNameList (nameList) {
  document.getElementById('list-name').innerHTML = `
  <select id='select' onchange='loadByName(this.value)'>
    <option>Choose a mailing list</option>
    ${Object.values(nameList).map(name => {
    return `<option>${name}</option>`
  }).join('')}
  </select>
  `
}

const deleteMailingListBtn = document.getElementById('delete-mailing-list-btn')
const deleteEmailsBtn = document.getElementById('delete-emails-btn')
const addEmailsBtn = document.getElementById('add-mails-btn')
const newMailingListNameBtn = document.getElementById('new-mailing-list-name-btn')
const importOutlookContactsBtn = document.getElementById('import-outlook-contacts-btn')
const changeEmailsBtn = document.getElementById('change-emails-btn')

/**
 * Load chosen mailing list
 */
async function loadByName (name) {
  if (name !== 'Choose a mailing list') {
    deleteMailingListBtn.removeAttribute('disabled')
    importOutlookContactsBtn.removeAttribute('disabled')
    currentMailingListName = name

    let link2 = `/mailingLists/${name}/emails`
    const response2 = await fetch(link2, {
      headers: {
        'Authorization': currentToken
      }
    })
    let mailsArray = await response2.json();
    console.log(mailsArray);
    currentMailingListEmails = mailsArray;
    let areaStr = ''
    for (let i = 0; i < mailsArray.length; i++) {
      areaStr = areaStr + mailsArray[i] + ' '
    }
    areaStr = areaStr.substring(0, areaStr.length-1);
    currentPatchEmails = areaStr;
    document.getElementById('emails-area').value = areaStr
    document.getElementById('mailing-list-name').value = name;
  } else {
    deleteMailingListBtn.setAttribute('disabled', 'disabled')
    importOutlookContactsBtn.setAttribute('disabled', 'disabled')
  }
}

/**
 * Delete a mailing list
 */
deleteMailingListBtn.addEventListener('click', (event) => {
  const isAgree = confirm(`Delete the '${currentMailingListName}' mailing list?`)
  if (isAgree) {
    event.preventDefault()
    fetch(`/mailingLists/${currentMailingListName}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': currentToken
      }
    })
        .then(response => {
          return response.json();
        })
      .then(() => {
      })
      .catch(error => console.log(error))
    deleteEmailsBtn.disabled = true;
  }
})

/**
 * Change/update a mailing list's name
 */
newMailingListNameBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const newName = document.getElementById('mailing-list-name').value
  if (newName === "") {
    alert("Name cannot be empty. Try again.")
  } else {
    fetch(`mailingLists/${currentMailingListName}?newTextIdentifier=${newName}`, {
      method: 'PATCH',
      headers: {
        'Authorization': currentToken
      }
    })
        .then(response => {
          return response.json();
        })
        .then(() => {
        })
        .catch(error => console.log(error))
  }
})

/**
 * Change/update emails
 */
changeEmailsBtn.addEventListener('click', (event) => {
  event.preventDefault();

  const emailsStr = document.getElementById('emails-area').value;
  const emailsArray = emailsStr.split(' ').filter(el => el !== '');

  if (validateEmails(emailsArray)) {
    fetching(`/mailingLists/${currentMailingListName}/emails/update`, emailsArray);
    changeEmailsBtn.setAttribute('disabled', 'disabled')
    currentPatchEmails = emailsStr;
  } else {
    alertIncorrectEmailError();
  }
})

/**
 * Alert wrong emails error
 */
function alertIncorrectEmailError() {
  alert('Emails are not in correct form. Make sure your set "innopolis.university" emails. Try again')
}

/**
 * Import contact lists from Outlook
 */
importOutlookContactsBtn.addEventListener('click', (event) => {
  event.preventDefault()
  fetch(`/mailingLists/importOutlookMailingLists`, {
    method: 'PATCH',
    headers: {
      'Authorization': currentToken
    }
  })
      .then(response => {
        return response.json();
      })
      .then(() => {
      })
      .catch(error => console.log(error))
  importOutlookContactsBtn.setAttribute('disabled', 'disabled')
})

function fetching (PATH, mailsArray) {
  fetch(PATH, {
    method: 'PATCH',
    body: JSON.stringify(
      mailsArray),
    headers: {
      'Content-Type': 'application/json',
      'Authorization': currentToken
    }
  })
      .then(response => response.text())
    .then((data) => {
      console.log(data);
      return data;
    })
    .catch(error => console.log(error));
}

/**
 * Add email(s) to a mailing list
 */
addEmailsBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const emailsStr = document.getElementById('add-mails').value
  const emailsArray = emailsStr.split(' ').filter(el => el !== '')

  if (validateEmails(emailsArray)) {
    fetching(`/mailingLists/${currentMailingListName}/emails/add`, emailsArray)
    addEmailsBtn.setAttribute('disabled', 'disabled')
    currentAddEmails = emailsStr;
  } else {
    alertIncorrectEmailError()
  }
})

/**
 * Delete email(s) from a mailing list
 */
deleteEmailsBtn.addEventListener('click', (event) => {
  const isAgree = confirm(`\Delete email(s) from the ${currentMailingListName} mailing list?`)

  if (isAgree) {
    event.preventDefault()
    const emailsStr = document.getElementById('delete-mails').value
    const emailsArray = emailsStr.split(' ').filter(el => el !== '')

    if (validateEmails(emailsArray)) {
      fetching(`/mailingLists/${currentMailingListName}/emails/delete`, emailsArray)
      deleteEmailsBtn.setAttribute('disabled', 'disabled')
      currentDeleteEmails = emailsStr;
    }
  }
})

function validateEmails (mailsArray) {
  for (const email of mailsArray) {
    if (!isEmail(email)) {
      return false
    }
  }
  return true
}

function isEmail (str) {
  const pattern = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@innopolis.university$/
  return !!(str.match(pattern))
}

function enableDisableNameField(txt) {
  newMailingListNameBtn.disabled = txt.value.trim() === "" || txt.value.trim() === currentMailingListName;
}

function enableDisableEmailsArea(txt) {
  changeEmailsBtn.disabled = txt.value.trim() === "" || txt.value.trim() === currentPatchEmails;
}

function enableDisableAddEmailsArea(txt) {
  addEmailsBtn.disabled = txt.value.trim() === "" || txt.value.trim() === currentAddEmails;
}

function enableDisableDeleteEmailsArea(txt) {
  deleteEmailsBtn.disabled = txt.value.trim() === "" || txt.value.trim() === currentDeleteEmails;
}
start().then()
