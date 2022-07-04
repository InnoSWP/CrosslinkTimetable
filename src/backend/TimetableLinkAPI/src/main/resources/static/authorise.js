let loginBtn = document.getElementById('login-btn')

loginBtn.addEventListener('click', (event) => {
    event.preventDefault()

    fetch('/login', {

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
            console.log(response);
            if (response.status === 403) {
                processWrongData();
                return;
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            loginBtn.setAttribute('disabled', 'true')
            let currentToken = data["token"];
            sessionStorage.setItem("token", currentToken);
            window.location.replace("index.html");
        })
        .catch(error => console.log(error))
})

function processWrongData() {
    alert('Wrong data. Make sure you use Innopolis account. Try again...');
    document.getElementById('username').value = '';
    document.getElementById('password').value = '';
}
