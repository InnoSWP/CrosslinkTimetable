/**
 * Fetch mailing lists
 */
async function start () {
    try {
        const response = await fetch('/mailingLists/names')
        const data = await response.json()
        createMailNameList(data)
    } catch (e) {
        //alert('There was a problem fetching the mailing list\'s names.')
    }
}

/**
 * Load mailing list in selector form
 */
function createMailNameList (nameList) {
    let hold = document.getElementById("list-name");
    let checkbox = document.createElement('input');

    nameList.forEach(name => {
        checkbox.type = "checkbox";
        checkbox.name = "mailing_lists_checkbox";
        checkbox.id = `checkbox-${name["textIdentifier"]}`;

        let label = document.createElement('label');

        let tn = document.createTextNode(name["textIdentifier"]);
        label.htmlFor="mailing-lists-checkbox";
        label.appendChild(tn);
        hold.appendChild(label);
        hold.appendChild(checkbox);
    })

}

// /**
//  * Load chosen mailing list
//  */
// async function loadByName (name) {
//     if (name !== 'Choose a mailing list') {
//
//         let link2 = `/mailingLists/${name}/emails`
//         const response2 = await fetch(link2)
//         let mailsArray = await response2.json();
//
//         console.log(mailsArray);
//         currentMailingListEmails = mailsArray;
//         let areaStr = ''
//         for (let i = 0; i < mailsArray.length; i++) {
//             areaStr = areaStr + mailsArray[i] + ' '
//         }
//     }
// }

start().then()