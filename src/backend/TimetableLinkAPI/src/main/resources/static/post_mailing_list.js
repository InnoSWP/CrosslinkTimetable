const btn = document.getElementById('create-btn')
btn.addEventListener('click', (event) => {
  event.preventDefault()
  const mailsStr = document.getElementById('mails').value
  const mailsArray = mailsStr.split(' ').filter(el => el !== '')
  if (validateEmails(mailsArray)) {
    const mailsName = document.getElementById('mailing-list-name').value

    fetch('/mailingLists', {
      method: 'POST',
      body: JSON.stringify({
        emails: mailsArray,
        textIdentifier: mailsName
      }),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then(() => {

      })
      .catch(error => console.log(error))
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
  const pattern = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@innopolis.university$/
  return !!(str.match(pattern))
}

function enableDisable(txt1, txt2) {
  if (txt1.value === "" || txt2.value=== "")
      btn.setAttribute('disabled', 'disabled')
  else
    btn.removeAttribute('disabled')
}