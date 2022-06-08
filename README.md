# Brief Intro

Project repo to link Moodle, MS Exchange, DoE updates and University Events in one centralized system.

## Important Links

- [Usecase Diagram](https://drive.google.com/file/d/1nr23I5055SIXLq0PMvGGDPZAzz01xiW4/view?usp=sharing)
- [User Story](https://docs.google.com/spreadsheets/d/12BQN_QRp9IU6oKfjrk3xJsV7YisZib4y3s285RwqUo4/edit?usp=sharing)
- [Product Backlog](https://docs.google.com/document/d/1eF4ok6R33ai33qpmHXXPxxG4ZWCH8k8phtmZDnNVtxg/edit?usp=sharing)
- [API Design](https://app.swaggerhub.com/apis/Timetable2/timetable/1.0.0)
- [Mock Server](https://www.postman.com/orange-astronaut-888988/workspace/timetable-api/collection/21222264-0d7b6da3-1e13-4bd9-af09-11720e694a00?ctx=documentation)

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