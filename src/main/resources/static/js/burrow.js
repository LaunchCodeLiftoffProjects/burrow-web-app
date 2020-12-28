function deleteRoom() {
    let room = $('#roomId').val();
    if (confirm(`Remove ${room}?`)) {
        document.getElementById("delete-room").submit();
    }
}

unction deleteProperty() {
    if (confirm("Are you sure?")) {
        document.getElementById("delete-property").submit();
    }
}