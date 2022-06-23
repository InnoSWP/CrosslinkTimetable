
async function start() {
    try {
        const response = await fetch("/events/names")
        const data = await response.json()
        createMailNameList(data)
    } catch (e) {
        console.log('There was a problem fetching the mailing list\'s names.')
    }
}

function createMailNameList(nameList) {
    document.getElementById("listName").innerHTML = `
    <select onchange="loadByName(this.value)">
        <option>Choose a mailing list</option>
        ${Object.keys(nameList).map(name => {
        return `<option>${name}</option>`
    }).join('')}
    </select>
    `
}

async function loadByName(name: string) {
    if (name !== 'Choose a mailing list') {
        let link = `/mailingLists/${name}`
        const response = await fetch(link)
        const data = await response.json()
        console.log(data)
    }
}