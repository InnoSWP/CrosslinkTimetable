let currentMailingListName
let currentMailingListEmails

/**
 * Fetch mailing lists
 */
async function start () {
  try {
    const response = await fetch('/mailingLists/names')
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
    const response2 = await fetch(link2)
    let mailsArray = await response2.json()
    currentMailingListEmails = mailsArray;
    let areaStr = ''
    for (let i = 0; i < mailsArray.length; i++) {
      areaStr = areaStr + mailsArray[i] + ' '
    }
    document.getElementById('emails-area').innerHTML = areaStr
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
        'Content-Type': 'application/json'
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
      method: 'PATCH'
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
  event.preventDefault()

  // First, delete these emails
  fetching(`/mailingLists/${currentMailingListName}/emails/delete`, currentMailingListEmails)
  console.log(document.getElementById('emails-area').value)

  // Second, add emails from text area
  const mailsStr = document.getElementById('emails-area').value
  const mailsArray = mailsStr.split(' ').filter(el => el !== '')
  if (validateEmails(mailsArray)) {
    console.log(mailsArray)
    fetching(`/mailingLists/${currentMailingListName}/emails/add`, mailsArray)
    console.log(document.getElementById('emails-area').value)
  } else {
    alertIncorrectEmailError();
  }

})

function alertIncorrectEmailError() {
  alert('Emails are not in correct form. Try again')
}

/**
 * Import contact lists from Outlook
 */
importOutlookContactsBtn.addEventListener('click', (event) => {
  event.preventDefault()
  fetch(`/mailingLists/importOutlookMailingLists`, {
    method: 'PATCH'
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
      'Content-Type': 'application/json'
    }
  })
      .then(response => {
        return response.json();
      })
    .then(() => {
    })
    .catch(error => console.log(error))
}

/**
 * Delete email(s) from a mailing list
 */
deleteEmailsBtn.addEventListener('click', (event) => {
  const isAgree = confirm(`\Delete email(s) from the ${currentMailingListName} mailing list?`)
  if (isAgree) {
    event.preventDefault()
    const mailsStr = document.getElementById('delete-mails').value
    const mailsArray = mailsStr.split(' ').filter(el => el !== '')
    fetching(`/mailingLists/${currentMailingListName}/emails/delete`, mailsArray)
  }
})

/**
 * Add email(s) to a mailing list
 */
addEmailsBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const mailsStr = document.getElementById('add-mails-btn').value
  const mailsArray = mailsStr.split(' ').filter(el => el !== '')
  if (validateEmails(mailsArray)) {
    fetching(`/mailingLists/${currentMailingListName}/emails/add`, mailsArray)
  } else {
    alertIncorrectEmailError()
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
  const pattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/
  return !!(str.match(pattern))
}

function enableDisableNameField(txt) {
  newMailingListNameBtn.disabled = txt.value.trim() === "" || txt.value.trim() === currentMailingListName;
}

function enableDisableEmailsArea(txt) {
  changeEmailsBtn.disabled = txt.value.trim() === "" || txt.value.trim() === currentMailingListEmails;
}

function enableDisableAddEmailsArea(txt) {
  addEmailsBtn.disabled = txt.value.trim() === "";
}

function enableDisableDeleteEmailsArea(txt) {
  deleteEmailsBtn.disabled = txt.value.trim() === "";
}
start().then()
