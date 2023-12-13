function toggleFavoriteStatus(characterId, currentFavoriteStatus) { 
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

function addCharacterFromApiToDatabase(name, server) {
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

function confirmDelete(charId) {
    let confirmation = confirm('Do you really want to delete the character?');
    if (confirmation) {
        deleteCharacter(charId);
    }
}

function deleteCharacter(charId) {
    fetch('/api/character/' + charId, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                console.error('Error deleting character, check if the character has task. Delete them first');
            }
        })
        .catch(error => console.error('Error:', error));
}
