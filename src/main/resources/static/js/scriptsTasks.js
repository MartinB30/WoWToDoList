function createTask() { //create a new task in taskListCharacterId
    let taskName = document.getElementById('taskName').value;
    let description = document.getElementById('description').value;
    let characterId = Number(window.location.pathname.split('/').pop());

    let newTask = {
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

function openEditModal(taskId) {
    fetch('/api/tasks/' + taskId)
        .then(response => response.json())
        .then(task => {
            document.getElementById('editTaskName').value = task.taskName;
            document.getElementById('editDescription').value = task.description;
            document.getElementById('editTaskModal').setAttribute('data-task-id', taskId);
        })
        .catch(error => console.error('Error:', error));

    document.getElementById('editTaskModal').style.display = 'block';
}

function closeEditModal() {
    document.getElementById('editTaskModal').style.display = 'none';
}

function saveEditedTask() {
    let taskId = document.getElementById('editTaskModal').getAttribute('data-task-id');
    let editedTaskName = document.getElementById('editTaskName').value;
    let editedDescription = document.getElementById('editDescription').value;

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
                closeEditModal();
                location.reload();
            } else {
            }
        })
        .catch(error => console.error('Error:', error));
}

function toggleCompleteStatus(taskId, currentCompleteStatus) { 
    console.log('toggleCompleteStatus called with:', taskId, currentCompleteStatus);

    fetch('/api/tasks/' + taskId + '/complete', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: taskId,
            completed: !currentCompleteStatus,
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


function confirmDelete(taskId) { 
    let confirmation = confirm('Do you really want to delete the task?');
    if (confirmation) {
        deleteTask(taskId);
    }
}

function deleteTask(taskId) {
    fetch('/api/tasks/' + taskId, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                console.error('Error deleting task');
            }
        })
        .catch(error => console.error('Error:', error));
}