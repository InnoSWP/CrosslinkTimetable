let currentMailingListName

/**
 * Fetch mailing lists
 */
async function start () {
    try {
        const response = await fetch('/mailingLists/names')
        const data = await response.json()
        createMailNameList(data)
    } catch (e) {
        alert('There was a problem fetching the mailing list\'s names.')
    }
}

/**
 * Load mailing list in selector form
 */
function createMailNameList (nameList) {
    document.getElementById('list-name').innerHTML = `
  <select id='select' onchange='loadByName(this.value)'>
    <option>Choose a mailing list</option>
    ${Object.values(nameList).map(name => {
        return `<option>${name}</option>`
    }).join('')}
  </select>
  `
}

/**
 * Load chosen mailing list
 */
async function loadByName (name) {
    if (name !== 'Choose a mailing list') {
        currentMailingListName = name;
    }
}