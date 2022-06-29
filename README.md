[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT)

# Cross Link for University Innopolis schedules

---

This application is a combination of all Innopolis University timetables. With it, DoE will create events that will be sent to the calendars of students and teachers of the university. Moreover, this application combines all the events created on Moodle and MS Exchange and also sends them to the Outlook calendars of students and teachers.

![Demo for DoE](pictures/DemoCalendar.png)

## Important Links

- [Usecase Diagram](https://drive.google.com/file/d/1nr23I5055SIXLq0PMvGGDPZAzz01xiW4/view?usp=sharing)
- [User Story](https://docs.google.com/spreadsheets/d/12BQN_QRp9IU6oKfjrk3xJsV7YisZib4y3s285RwqUo4/edit?usp=sharing)
- [Product Backlog](https://docs.google.com/document/d/1eF4ok6R33ai33qpmHXXPxxG4ZWCH8k8phtmZDnNVtxg/edit?usp=sharing)
- [API Design](https://app.swaggerhub.com/apis/Timetable2/timetable/1.0.0)
- [Mock Server](https://www.postman.com/orange-astronaut-888988/workspace/timetable-api/collection/21222264-0d7b6da3-1e13-4bd9-af09-11720e694a00?ctx=documentation)

## Why this important?

In University Innopolis there a lot of links that have some events on them(e.g. Moodle, Outlook, MS Exchange). So, our application unite all these timetables and send them to Outlook Calendar, because every participant of University Innopolis has Outlook account.



## Developer Guide

### Branching Rules
- Keep branch names relevant to the sprint task (i.e. LoginFeature, EventFrontend)
- Maintain separate branch for separate tasks

### Commits
- Commit message format - `[Commit Type] - [Brief]`
    Commit types
    - Patch
    - Feature
    - Minor Change
- Keep commit to their respective branch (i.e. `Patch - Fixed frontend taskbar responsiveness` should not be commited to `LoginFeature` branch)

## Pushing
- Pushing to master is forbidden, branches would be merged later
- To be accepted for a merge, code should either pass unittest (if applicable) or be checked for bugs by at least one other developer
