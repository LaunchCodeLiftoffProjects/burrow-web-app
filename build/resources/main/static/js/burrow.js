function deleteFunction() {
    if (confirm("Are you sure?")) {
        document.getElementById("delete-room").submit();
    } else {
        return false;
    }
}