let btn = document.getElementById("createBtn");
btn.addEventListener('click', (event) => {
    event.preventDefault();
    let mailsStr = document.getElementById("mails").value;
    let mailsArray = mailsStr.split(" ");
    let mailsName = document.getElementById("mailingListName").value;

    fetch("/mailingLists", {
        method: 'POST',
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