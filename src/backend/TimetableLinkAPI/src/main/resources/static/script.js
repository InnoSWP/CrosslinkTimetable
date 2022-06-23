!function() {

  let today = moment();

  function Calendar(selector, events) {
    this.el = document.querySelector(selector);
    this.events = events;
    this.current = moment().date(1);
    this.draw();
    //   let current = document.querySelector('.today');
    //   if(current) {
    //     let self = this;

    //     window.setTimeout(function() {
    //       self.openDay(current);
    //     }, 500);
    //   }

  }

  Calendar.prototype.draw = function() {
    //Create Header
    this.drawHeader();

    //Draw Month
    this.drawMonth();

    this.drawLegend();
  }

  Calendar.prototype.drawHeader = function() {
    let self = this;
    if(!this.header) {
      //Create the header elements
      this.header = createElement('div', 'header');
      this.header.className = 'header';

      this.title = createElement('h1');
      this.title.setAttribute('id', 'monthname');

      let right = createElement('div', 'right');
      right.addEventListener('click', function() { self.nextMonth(); });

      let left = createElement('div', 'left');
      left.addEventListener('click', function() { self.prevMonth(); });

      //Append the Elements
      this.header.appendChild(this.title);
      this.header.appendChild(right);
      this.header.appendChild(left);
      this.el.appendChild(this.header);
    }

    this.title.innerHTML = this.current.format('MMMM YYYY');
  }

  Calendar.prototype.drawMonth = function() {
    let self = this;

    this.events.forEach(function(ev) {
      d = Math.random() * (29 - 1) + 1
      ev.date = self.current.clone().date(d);
    });


    if(this.month) {
      this.oldMonth = this.month;
      this.oldMonth.className = 'month out ' + (self.next ? 'next' : 'prev');
      this.oldMonth.addEventListener('webkitAnimationEnd', function() {
        self.oldMonth.parentNode.removeChild(self.oldMonth);
        self.month = createElement('div', 'month');
        self.backFill();
        self.currentMonth();
        self.fowardFill();
        self.el.appendChild(self.month);
        window.setTimeout(function() {
          self.month.className = 'month in ' + (self.next ? 'next' : 'prev');
        }, 16);
      });
    } else {
      this.month = createElement('div', 'month');
      this.el.appendChild(this.month);
      this.backFill();
      this.currentMonth();
      this.fowardFill();
      this.month.className = 'month new';
    }
  }

  Calendar.prototype.backFill = function() {
    let clone = this.current.clone();
    let dayOfWeek = clone.day();

    if(!dayOfWeek) { return; }

    clone.subtract('days', dayOfWeek+1);

    for(let i = dayOfWeek; i > 0 ; i--) {
      this.drawDay(clone.add('days', 1));
    }
  }

  Calendar.prototype.fowardFill = function() {
    let clone = this.current.clone().add('months', 1).subtract('days', 1);
    let dayOfWeek = clone.day();

    if(dayOfWeek === 6) { return; }

    for(let i = dayOfWeek; i < 6 ; i++) {
      this.drawDay(clone.add('days', 1));
    }
  }

  Calendar.prototype.currentMonth = function() {
    let clone = this.current.clone();

    while(clone.month() === this.current.month()) {
      this.drawDay(clone);
      clone.add('days', 1);
    }
  }

  Calendar.prototype.getWeek = function(day) {
    if(!this.week || day.day() === 0) {
      this.week = createElement('div', 'week');
      this.month.appendChild(this.week);
    }
  }

  Calendar.prototype.drawDay = function(day) {
    let self = this;
    this.getWeek(day);

    //Outer Day
    let outer = createElement('div', this.getDayClass(day));
    outer.addEventListener('click', function() {
      self.openDay(this);
    });

    //Day Name
    let name = createElement('div', 'day-name', day.format('ddd'));

    //Day Number
    let number = createElement('div', 'day-number', day.format('DD'));


    //Events
    let events = createElement('div', 'day-events');
    this.drawEvents(day, events);

    outer.appendChild(name);
    outer.appendChild(number);
    outer.appendChild(events);
    this.week.appendChild(outer);
  }

  Calendar.prototype.drawEvents = function(day, element) {
    if(day.month() === this.current.month()) {
      let todaysEvents = this.events.reduce(function(memo, ev) {
        if(ev.date.isSame(day, 'day')) {
          memo.push(ev);
        }
        return memo;
      }, []);


      todaysEvents.forEach(function(ev) {
        let evSpan = createElement('span', ev.color);
        element.appendChild(evSpan);
      });
    }
  }

  Calendar.prototype.getDayClass = function(day) {
    classes = ['day'];
    if(day.month() !== this.current.month()) {
      classes.push('other');
    } else if (today.isSame(day, 'day')) {
      classes.push('today');
    }
    return classes.join(' ');
  }

  Calendar.prototype.openDay = function(el) {
    let details, arrow;
    let dayNumber = +el.querySelectorAll('.day-number')[0].innerText || +el.querySelectorAll('.day-number')[0].textContent;
    let day = this.current.clone().date(dayNumber);


    let currentOpened = document.querySelector('.details');

    //Check to see if there is an open detais box on the current row
    //if (true){ //(currentOpened && currentOpened.parentNode === el.parentNode) {
      //   details = currentOpened;
      //   arrow = document.querySelector('.arrow');
      // } else {
      //Close the open events on differnt week row
      //currentOpened && currentOpened.parentNode.removeChild(currentOpened);
      if(currentOpened) {
        currentOpened.addEventListener('webkitAnimationEnd', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.addEventListener('oanimationend', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.addEventListener('msAnimationEnd', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.addEventListener('animationend', function() {
          currentOpened.parentNode.removeChild(currentOpened);
        });
        currentOpened.className = 'details out';
      }

      //Create the Details Container
      details = createElement('div', 'details in');
      details.setAttribute('id', 'datecontainer');
      details.setAttribute('data-date', dayNumber);

      //Create the arrow
      arrow = createElement('div', 'arrow');

      //Create the event wrapper

      details.appendChild(arrow);
      el.parentNode.appendChild(details);
    //}

    let todaysEvents = this.events.reduce(function(memo, ev) {
      if(ev.date.isSame(day, 'day')) {
        memo.push(ev);
      }
      return memo;
    }, []);

    this.renderEvents(todaysEvents, details);

    arrow.style.left = el.offsetLeft - el.parentNode.offsetLeft + 27 + 'px';
  }

  Calendar.prototype.renderEvents = function(events, ele) {
    //Remove any events in the current details element
    let currentWrapper = ele.querySelector('.events');
    let wrapper = createElement('div', 'events in' + (currentWrapper ? ' new' : ''));

    events.forEach(function(ev) {
      let div = createElement('div', 'event');
      let square = createElement('div', 'event-category ' + ev.color);
      let span = createElement('span', '', ev.eventName);

      div.appendChild(square);
      div.appendChild(span);
      wrapper.appendChild(div);
    });

    if(!events.length) {
      let div = createElement('div', 'event empty');
      let br = createElement('br');
      let eventForm = createElement("form");
      eventForm.setAttribute("method", "post");
      let eventTitle = createElement('input', 'eventtitle');
      eventTitle.setAttribute('id', 'eventtitle');
      eventTitle.setAttribute('type', 'text');
      eventTitle.setAttribute('placeholder', 'Event Title');
      let eventLocation = createElement('input', 'eventlocation');
      eventLocation.setAttribute('id', 'eventlocation');
      eventLocation.setAttribute('type', 'text');
      eventLocation.setAttribute('placeholder', 'Event Location');
      let times = [
        '00:00', '00:30', '01:00', '01:30', '02:00', '02:30', '03:00',
        '03:30', '04:00', '04:30', '05:00', '05:30', '06:00', '06:30',
        '07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00',
        '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30',
        '14:00', '14:30', '15:00', '15:30', '16:00', '16:30', '17:00',
        '17:30', '18:00', '18:30', '19:00', '19:30', '20:00', '20:30',
        '21:00', '21:30', '22:00', '22:30', '23:00', '23:30'
      ]
      const eventStart = createElement('select', 'eventstarttime');
      eventStart.appendChild(new Option('Start', 'Start', true));
      eventStart.setAttribute('id', 'eventstarttime');
      const eventEnd = createElement('select', 'eventendtime');
      eventEnd.appendChild(new Option('End', 'End', true));
      eventEnd.setAttribute('id', 'eventendtime');
      times.forEach(function(item, _){
        eventStart.appendChild(new Option(item, item));
        eventEnd.appendChild(new Option(item, item));
      });
      const submitForm = createElement('button', 'eventSubmit');
      submitForm.innerText = '➔';
      eventForm.appendChild(eventTitle);
      eventForm.appendChild(eventLocation);
      eventForm.appendChild(br.cloneNode());
      eventForm.appendChild(eventStart);
      eventForm.appendChild(eventEnd);
      eventForm.appendChild(submitForm);
      div.appendChild(eventForm);
      wrapper.appendChild(div);
      submitForm.addEventListener('click', (event) => {
        event.preventDefault();
        let date = document.getElementById('datecontainer').dataset['date'];
        let startTime = document.getElementById('eventstarttime').value;
        let endTime = document.getElementById('eventendtime').value;
        let name = document.getElementById('eventtitle').value;
        let location = document.getElementById('eventlocation').value;
        let dict = {
          "January": '01', "February": "02", "March": "03", "April": "04", "May": "05", "June": "06", "July": "07",
          "August": "08", "September": "09", "October": "10", "November": "11", "December": "12"
        };
        let monyear = document.getElementById('monthname').innerHTML.split(" ");
        console.log(monyear);
        let month = dict[monyear[0]];
        let year = monyear[1];
        let formattedStart = `${year}-${month}-${date}T${startTime}:00.000Z`;

        let formattedEnd = `${year}-${month}-${date}T${endTime}:00.000Z`;

        fetch("http://localhost:8080/events", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({"name": name,
            "location": location,
            "startDate": formattedStart,
            "endDate": formattedEnd})
        })
            .then (response => response.json())
            .then (() => {
              document.getElementsByClassName('event empty')[0].innerHTML = "Event Added Successfully!";
            })
            .catch(error => console.log(error));
      });
    }

    if(currentWrapper) {
      currentWrapper.className = 'events out';
      currentWrapper.addEventListener('webkitAnimationEnd', function() {
        currentWrapper.parentNode.removeChild(currentWrapper);
        ele.appendChild(wrapper);
      });
      currentWrapper.addEventListener('oanimationend', function() {
        currentWrapper.parentNode.removeChild(currentWrapper);
        ele.appendChild(wrapper);
      });
      currentWrapper.addEventListener('msAnimationEnd', function() {
        currentWrapper.parentNode.removeChild(currentWrapper);
        ele.appendChild(wrapper);
      });
      currentWrapper.addEventListener('animationend', function() {
        currentWrapper.parentNode.removeChild(currentWrapper);
        ele.appendChild(wrapper);
      });
    } else {
      ele.appendChild(wrapper);
    }
  }

  Calendar.prototype.drawLegend = function() {
    let legend = createElement('div', 'legend');
    let calendars = this.events.map(function(e) {
      return e.calendar + '|' + e.color;
    }).reduce(function(memo, e) {
      if(memo.indexOf(e) === -1) {
        memo.push(e);
      }
      return memo;
    }, []).forEach(function(e) {
      let parts = e.split('|');
      let entry = createElement('span', 'entry ' +  parts[1], parts[0]);
      legend.appendChild(entry);
    });
    this.el.appendChild(legend);
  }

  Calendar.prototype.nextMonth = function() {
    this.current.add('months', 1);
    this.next = true;
    this.draw();
  }

  Calendar.prototype.prevMonth = function() {
    this.current.subtract('months', 1);
    this.next = false;
    this.draw();
  }

  window.Calendar = Calendar;

  function createElement(tagName, className, innerText) {
    let ele = document.createElement(tagName);
    if(className) {
      ele.className = className;
    }
    if(innerText) {
      ele.innderText = ele.textContent = innerText;
    }
    return ele;
  }
}();

!function() {
  let data = [
  ];



  function addDate(ev) {

  }

  let calendar = new Calendar('#calendar', data);

}();

