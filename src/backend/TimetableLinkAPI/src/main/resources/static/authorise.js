let loginBtn = document.getElementById('login-btn')

loginBtn.addEventListener('click', (event) => {
    event.preventDefault()
    let token

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
        .then(response => response.json())
        .then(data => {
            token = data["token"];
        })
        .catch(error => console.log(error))
})