let currentMailingListName

/**
 * Fetch mailing lists
 */
async function start () {
  try {
    const response = await fetch('/mailingLists/names')
    const data = await response.json()
    createMailNameList(data)
  } catch (e) {
    alert('There was a problem fetching the mailing list\'s names.')
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
const addMailsBtn = document.getElementById('add-mails-btn')
const newMailingListNameBtn = document.getElementById('new-mailing-list-name-btn')

/**
 * Load chosen mailing list
 */
async function loadByName (name) {
  if (name !== 'Choose a mailing list') {
    deleteMailingListBtn.removeAttribute('disabled')
    deleteEmailsBtn.removeAttribute('disabled')
    addMailsBtn.removeAttribute('disabled')
    newMailingListNameBtn.removeAttribute('disabled')
    currentMailingListName = name

    let link2 = `/mailingLists/${name}/emails`
    const response2 = await fetch(link2)
    let mailsArray = await response2.json()
    let areaStr = ''
    for (let i = 0; i < mailsArray.length; i++) {
      areaStr = areaStr + mailsArray[i] + ' '
    }
    document.getElementById('emails-area').innerHTML = areaStr
  } else {
    deleteMailingListBtn.setAttribute('disabled', 'disabled')
    deleteEmailsBtn.setAttribute('disabled', 'disabled')
    addMailsBtn.setAttribute('disabled', 'disabled')
    newMailingListNameBtn.setAttribute('disabled', 'disabled')
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
      .then(response => response.json())
      .then(() => {
      })
      .catch(error => console.log(error))
  }
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
    .then(response => response.json())
    .then(() => {
    })
    .catch(error => console.log(error))
}

/**
 * Delete email(s) from a mailing list
 */
deleteEmailsBtn.addEventListener('click', (event) => {
  const isAgree = confirm(`\Delete emails from the ${currentMailingListName} mailing list?`)
  if (isAgree) {
    event.preventDefault()
    const mailsStr = document.getElementById('emails-area').value
    const mailsArray = mailsStr.split(' ').filter(el => el !== '')
    fetching(`/mailingLists/${currentMailingListName}/emails/delete`, mailsArray)
  }
})

/**
 * Add email(s) to a mailing list
 */
addMailsBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const mailsStr = document.getElementById('mails').value
  const mailsArray = mailsStr.split(' ').filter(el => el !== '')
  if (validateEmails(mailsArray)) {
    fetching(`/mailingLists/${currentMailingListName}/emails/add`, mailsArray)
  } else {
    alert('Emails are not in correct form. Try again')
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

/**
 * Change/update a mailing list's name
 */
newMailingListNameBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const newName = document.getElementById('new-mailing-list-name').value
  fetch(`mailingLists/${currentMailingListName}?newTextIdentifier=${newName}`, {
    method: 'PATCH'
  })
    .then(response => response.json())
    .then(() => {
    })
    .catch(error => console.log(error))
})

start()
