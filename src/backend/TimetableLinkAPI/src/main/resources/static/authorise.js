let loginBtn = document.getElementById('login-btn')
let currentToken

loginBtn.addEventListener('click', (event) => {
    event.preventDefault()

    let link = fetch('/login', {

        method: 'POST',
        body: JSON.stringify({
            email: document.getElementById('username').value,
            password: document.getElementById('password').value
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.status === 403) {
                processWrongData();
                return;
            }
            return response.json()
        })
        .then(data => {
            console.log(data)
            currentToken = data["token"];
            console.log(currentToken)

        })
        .catch(error => console.log(error))
        window.location.replace("index.html");
})

function processWrongData() {
    alert('Wrong data. Make sure you use Innopolis account. Try again...');
    document.getElementById('username').value = '';
    document.getElementById('password').value = '';
}

function replaceLocation() {
    // fetch(`index.html`, {
    //     method: 'GET',
    //     headers: {
    //         'Authorization': currentToken
    //     }
    // })
    //     .then(response => {
    //         return response.json();
    //     })
    //     .then(() => {
    //     })
    //     .catch(error => console.log(error))

    // localStorage.setItem('token', currentToken)
    window.replaceLocation('/index.html');

    //let url = "index.html";
    //fetch("login.html").then(res => {
    //    if (res.redirected) window.replaceLocation(res.url);
    //});
}