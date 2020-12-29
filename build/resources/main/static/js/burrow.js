function deleteRoom() {
    let room = $('#roomId').val();
    if (confirm(`Remove ${room}?`)) {
        document.getElementById("delete-room").submit();
    }
}