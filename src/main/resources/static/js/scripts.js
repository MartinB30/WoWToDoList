function toggleFavoriteStatus(characterId, currentFavoriteStatus) { //used in index.html to change favorite status
    console.log('toggleFavoriteStatus called with:', characterId, currentFavoriteStatus);

    fetch('/api/character/' + characterId + '/favorite', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: characterId,
            favorite: !currentFavoriteStatus,
        }),
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {

        }
    })
    .catch(error => console.error('Error:', error));
}

function addCharacterFromApiToDatabase(name, server) {//used in profile.html
         let data = {
            "name": name,
            "server": server
        };

        fetch('/api/character', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            window.location.reload();
        })
        .catch(error => console.error('Error:', error));
    }

   function createTask() { //create a new task in taskListCharacterId
           var taskName = document.getElementById('taskName').value;
           var description = document.getElementById('description').value;
           var characterId = Number(window.location.pathname.split('/').pop());

           var newTask = {
               taskName: taskName,
               description: description,
               isCompleted: false,
               warcraftCharacter: {
                   id: characterId
               }
           };

           fetch('/api/tasks', {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json',
               },
               body: JSON.stringify(newTask),
           })
           .then(response => {
               if (response.ok) {
                   location.reload();
               } else {

               }
           })
           .catch(error => console.error('Error:', error));
       }

