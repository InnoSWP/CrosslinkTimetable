let currentEventName
let currentMailingListName
let currentEventLocation
let currentEventStartDate
let currentEventEndDate

/**
 * Fetch events and mailing lists
 */
async function start () {
  try {
    const response = await fetch('/events/names')
    const data = await response.json()
    createEventsNameList(data)

    const mailResponse = await fetch('/mailingLists/names')
    const mailData = await mailResponse.json()
    createMailNameList(mailData)
  } catch (e) {
    alert('There was a problem fetching the events and/or mailing lists names.')
  }
}

/**
 * Load event in selector form
 */
function createEventsNameList (nameList) {
  document.getElementById('event-name').innerHTML = `
  <select onchange='loadEventByName(this.value)'>
    <option>Choose an event</option>
    ${Object.values(nameList).map(name => {
    return `<option>${name}</option>`
  }).join('')}
  </select>
  `
}

/**
 * Load mailing list in selector form
 */
function createMailNameList (nameList) {
  document.getElementById('list-name').innerHTML = `
  <select id='select' onchange='loadMailingListByName(this.value)'>
    <option>Choose a mailing list</option>
    ${Object.values(nameList).map(name => {
    return `<option>${name}</option>`
  }).join('')}
  </select>
  `
}

const deleteEventBtn = document.getElementById('delete-event-btn')
const searchEventByDateBtn = document.getElementById('search-event-by-date-btn')
const newEventNameBtn = document.getElementById('new-event-name-btn')
const newEventLocationBtn = document.getElementById('new-event-location-btn')
const newEventStartDateBtn = document.getElementById('new-event-start-date-btn')
const newEventEndDateBtn = document.getElementById('new-event-end-date-btn')

const sendInvitationToOneMailingListBtn = document.getElementById('send-invitation-to-one-mailing-list-btn');
const cancelInvitationToOneMailingListBtn = document.getElementById('cancel-invitation-to-one-mailing-list-btn')

/**
 * Load chosen event
 */
async function loadEventByName (name) {
  if (name !== 'Choose an event') {
    currentEventName = name;
    deleteEventBtn.removeAttribute('disabled')
      searchEventByDateBtn.removeAttribute('disabled')
    newEventNameBtn.removeAttribute('disabled')
      newEventLocationBtn.removeAttribute('disabled')
      newEventStartDateBtn.removeAttribute('disabled')
      newEventEndDateBtn.removeAttribute('disabled')
    let link = `/events/${currentEventName}`

    fetch(link)
        .then(response => response.json())
        .then(data => {

          currentEventLocation = data["location"]
          currentEventStartDate = data["startDate"]
          currentEventEndDate = data["endDate"]

          console.log(data);

          console.log("LOCATION " + currentEventLocation)
          console.log("START " + currentEventStartDate)
          console.log("END " + currentEventEndDate)

          document.getElementById('event-location').innerHTML = currentEventLocation
          document.getElementById('event-start-date').innerHTML = currentEventStartDate
          document.getElementById('event-end-date').innerHTML = currentEventEndDate
        })
        .catch(error => console.log(error));
  } else {
    deleteEventBtn.setAttribute('disabled', 'disabled')
    newEventNameBtn.setAttribute('disabled', 'disabled')
      newEventLocationBtn.setAttribute('disabled', 'disabled')
      newEventStartDateBtn.setAttribute('disabled', 'disabled')
      newEventEndDateBtn.setAttribute('disabled', 'disabled')
      searchEventByDateBtn.setAttribute('disabled', 'disabled')
  }
}

/**
 * Load chosen mailing list
 */
async function loadMailingListByName (name) {
  if (name !== 'Choose a mailing list') {
    currentMailingListName = name;
      sendInvitationToOneMailingListBtn.removeAttribute('disabled')
      cancelInvitationToOneMailingListBtn.removeAttribute('disabled')
  } else {
      sendInvitationToOneMailingListBtn.setAttribute('disabled', 'disabled')
      cancelInvitationToOneMailingListBtn.setAttribute('disabled', 'disabled')
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
 * Get events from time interval
 */
searchEventByDateBtn.addEventListener('click', (event) => {
    event.preventDefault()
    const startDate = document.getElementById('search-event-start-date').value
    const endDate = document.getElementById('search-event-end-date').value

    fetch(`events/eventsFromTimeInterval?start=${startDate}&end=${endDate}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log("EVENTS ARRAY FROM TIME INTERVAL: " + data)
        })
        .catch(error => console.log(error))
})



/**
 * Change/update an event's name
 */
newEventNameBtn.addEventListener('click', (event) => {
  event.preventDefault()
  const newName = document.getElementById('new-event-name').value
  fetch(`events/${currentEventName}`, {
    method: 'PUT',
      body: JSON.stringify({
              'name': newName,
              'location': currentEventLocation,
              'startDate': currentEventStartDate,
              'endDate': currentEventEndDate}
          ),
      headers: {
          'Content-Type': 'application/json'
      }
  })
      .then(response => response.json())
      .then(() => {
      })
      .catch(error => console.log(error))
})

/**
 * Change/update an event's location
 */
newEventLocationBtn.addEventListener('click', (event) => {
    event.preventDefault()
    const newLocation = document.getElementById('new-event-location').value
    fetch(`events/${currentEventName}`, {
        method: 'PUT',
        body: JSON.stringify({
            'name': currentEventName,
            'location': newLocation,
            'startDate': currentEventStartDate,
            'endDate': currentEventEndDate}
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(() => {
        })
        .catch(error => console.log(error))
})

/**
 * Change/update an event's start date
 */
newEventStartDateBtn.addEventListener('click', (event) => {
    event.preventDefault()
    const newStartDate = document.getElementById('new-event-start-date').value
    fetch(`events/${currentEventName}`, {
        method: 'PUT',
        body: JSON.stringify({
            'name': currentEventName,
            'location': currentEventLocation,
            'startDate': newStartDate,
            'endDate': currentEventEndDate}
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(() => {
        })
        .catch(error => console.log(error))
})

/**
 * Change/update an event's end date
 */
newEventStartDateBtn.addEventListener('click', (event) => {
    event.preventDefault()
    const newEndDate = document.getElementById('new-event-end-date').value
    fetch(`events/${currentEventName}`, {
        method: 'PUT',
        body: JSON.stringify({
            'name': currentEventName,
            'location': currentEventLocation,
            'startDate': currentEventStartDate,
            'endDate': newEndDate}
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(() => {
        })
        .catch(error => console.log(error))
})

/**
 * Send invitation to people from one mailing list
 */
sendInvitationToOneMailingListBtn.addEventListener('click', (event) => {
    event.preventDefault()
    fetch(`/events/${currentEventName}/invite/${currentMailingListName}`, {
        method: 'PATCH',
        body: JSON.stringify(
            currentMailingListName
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(() => {
        })
        .catch(error => console.log(error))

})

/**
 * Cancel invitation to people from one mailing list
 */
cancelInvitationToOneMailingListBtn.addEventListener('click', (event) => {
    event.preventDefault()
    fetch(`/events/${currentEventName}/cancelInvitation/${currentMailingListName}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(() => {
        })
        .catch(error => console.log(error))

})

start()
