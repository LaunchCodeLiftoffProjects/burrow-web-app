function deleteRoom() {
    let room = $('#roomId').val();
    if (confirm(`Remove ${room}?`)) {
        document.getElementById("delete-room").submit();
    }
}

function deleteProperty() {
    let property = $('#propertyId').val();
    if (confirm(`Remove ${property}?`)) {
        document.getElementById("delete-property").submit();
    }
}