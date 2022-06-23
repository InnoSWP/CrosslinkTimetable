
async function start() {
    try {
        const response = await fetch("/events/names")
        const data = await response.json()
        createBreedList(data)
    } catch (e) {
        console.log('There was a problem fetching the breed list.')
    }
}

function createBreedList(nameList) {
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
        const response = await fetch(link) // fetch images for a concrete breed
        const data = await response.json()
    }
}