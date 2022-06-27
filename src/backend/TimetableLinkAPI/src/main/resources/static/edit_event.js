let currentEventName

/**
 * Fetch events
 */
async function start () {
  try {
    const response = await fetch('/events/names')
    const data = await response.json()
    createEventsNameList(data)
  } catch (e) {
    alert('There was a problem fetching the events\' names.')
  }
}

/**
 * Load event in selector form
 */
function createEventsNameList (nameList) {
  document.getElementById('event-name').innerHTML = `
  <select onchange='loadByName(this.value)'>
    <option>Choose an event</option>
    ${Object.values(nameList).map(name => {
    return `<option>${name}</option>`
  }).join('')}
  </select>
  `
}

const deleteEventBtn = document.getElementById('delete-event-btn')
const newEventNameBtn = document.getElementById('new-event-name-btn')

/**
 * Load chosen event
 */
async function loadByName (name) {
  if (name !== 'Choose an event') {
    currentEventName = name;
    deleteEventBtn.removeAttribute('disabled')
    newEventNameBtn.removeAttribute('disabled')
    let link = `/events/${currentEventName}`

    fetch(link)
        .then(response => response.json())
        .then(data => {
          console.log(data);
          console.log("START " + data["startDate"])
          console.log("END " + data["endDate"])
        })
        .catch(error => console.log(error));
  } else {
    deleteEventBtn.setAttribute('disabled', 'disabled')
    newEventNameBtn.setAttribute('disabled', 'disabled')
  }
}

/**
 * Delete an event
 */
deleteEventBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const isAgree = confirm(`Delete the '${currentEventName}' event?`)
  if (isAgree) {
    fetch(`/events/${currentEventName}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    })
        .then(response => response.json())
        .then(() => {
        })
        .catch(error => console.log(error))
  }
})

/**
 * Change/update an event's name
 */
newEventNameBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const newName = document.getElementById('new-mailing-list-name').value
  fetch(`mailingLists/${currentMailingListName}?newTextIdentifier=${newName}`, {
    method: 'PATCH'
  })
      .then(response => response.json())
      .then(() => {
      })
      .catch(error => console.log(error))
})


start()
