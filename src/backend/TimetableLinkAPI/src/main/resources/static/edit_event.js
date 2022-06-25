let currentEventName

async function start () {
  try {
    const response = await fetch('/events/names')
    const data = await response.json()
    createEventsNameList(data)
  } catch (e) {
    alert('There was a problem fetching the events\' names.')
  }
}

function createEventsNameList (nameList) {
  document.getElementById('event-name').innerHTML = `
  <select onchange='loadByName(this.value)'>
    <option>Choose an event</option>
    ${Object.keys(nameList).map(name => {
    return `<option>${name}</option>`
  }).join('')}
  </select>
  `
}

let btn = document.getElementById('delete-btn')

async function loadByName (name) {
  if (name !== 'Choose an event') {
    btn.removeAttribute('disabled')
    console.log(name)
    let link = `/events/${name}`
    const response = await fetch(link)
    const data = await response.json()
    if (data.length === 0)
      alert('Error')
    console.log(data)
  } else {
    btn.setAttribute('disabled', 'disabled')
  }
}


btn.addEventListener('click', (event) => {
  event.preventDefault()
  fetch(`/events/${currentEventName}`, {
    method: 'DELETE',
    body: JSON.stringify({
      'eventId': currentEventName
    }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(() => {
      alert('Event is deleted successfully!');
    })
    .catch(error => console.log(error));
})

start()