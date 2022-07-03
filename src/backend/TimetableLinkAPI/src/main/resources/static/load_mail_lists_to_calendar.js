async function start() {
    const mailResponse = await fetch('/mailingLists/names', {
        // headers: {
        //     'Authorization': currentToken
        // }
    });
    const nameArray = await mailResponse.json();
    loadCheckbox(nameArray);
}

function loadCheckbox(nameArray) {
    let cb = document.getElementById("cb");
    let cnt = 0;

    for (name of nameArray) {
        let checkbox = document.createElement('input');
        checkbox.type = "checkbox";
        checkbox.id = `cb${cnt}`;

        let br = document.createElement('br');
        let label = document.createElement('label')
        label.htmlFor = `cb${cnt++}`;
        label.appendChild(document.createTextNode(name))

        cb.append(checkbox);
        cb.appendChild(label);
        cb.append(br);
    }
}

// console always displays "unchecked"
let checkboxes = document.querySelectorAll('.loadedByScript');
for (let checkbox of checkboxes) {
    checkbox.addEventListener('click', () => {
        if (checkbox.checked === true) {
            console.log(this.value);
        } else {
            console.log('unchecked')
        }
    })
}

start().then()