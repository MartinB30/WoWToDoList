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


       
// Function to open the edit modal
function openEditModal(taskId) {
    // Fetch task details by taskId and populate the modal inputs
    fetch('/api/tasks/' + taskId)
        .then(response => response.json())
        .then(task => {
            document.getElementById('editTaskName').value = task.taskName;
            document.getElementById('editDescription').value = task.description;

            // Set the data-task-id attribute on the modal
            document.getElementById('editTaskModal').setAttribute('data-task-id', taskId);
        })
        .catch(error => console.error('Error:', error));

    // Show the modal
    document.getElementById('editTaskModal').style.display = 'block';
}

// Function to close the edit modal
function closeEditModal() {
    // Hide the modal
    document.getElementById('editTaskModal').style.display = 'none';
}

// Function to save changes to the edited task
function saveEditedTask() {
    // Fetch the task ID from the modal's data attribute
    var taskId = document.getElementById('editTaskModal').getAttribute('data-task-id');

    // Check if taskId is null or undefined
    if (taskId === null || taskId === undefined) {
        console.error('Invalid task ID: ' + taskId);
        return;
    }

    var editedTaskName = document.getElementById('editTaskName').value;
    var editedDescription = document.getElementById('editDescription').value;

    // Update the task in the database
    fetch('/api/tasks/' + taskId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            taskName: editedTaskName,
            description: editedDescription,
        }),
    })
    .then(response => {
        if (response.ok) {
            // Close the modal and refresh the page or update the task list
            closeEditModal();
            location.reload();
        } else {
            // Handle error
        }
    })
    .catch(error => console.error('Error:', error));
}
