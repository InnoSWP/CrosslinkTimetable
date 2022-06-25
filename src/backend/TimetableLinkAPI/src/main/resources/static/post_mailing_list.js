const btn = document.getElementById('create-btn');
btn.addEventListener('click', (event) => {
    event.preventDefault();
    let mailsStr = document.getElementById('mails').value;
    let mailsArray = mailsStr.split(' ').filter(el => el !== '')
    if (validateEmails(mailsArray)) {
        let mailsName = document.getElementById('mailing-list-name').value;


        fetch('/mailingLists', {
            method: 'POST',
            body: JSON.stringify({
                'emails': mailsArray,
                'textIdentifier': mailsName
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(() => {

            })
            .catch(error => console.log(error));
    } else {
        alert('Emails are not in correct form. Try again');
    }
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