let btn = document.getElementById("createBtn");
btn.addEventListener('click', (event) => {
    event.preventDefault();
    let mailsStr = document.getElementById("mails").value;
    let mailsArray = mailsStr.split(" ");
    let mailsName = document.getElementById("mailingListName").value;
    console.log(mailsStr)
    console.log(mailsName)
    for (let i = 0; i < mailsArray.length; i++)
        console.log(mailsArray[i])
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
            alert("Mailing list is added successfully!");
        })
        .catch(error => console.log(error));
})