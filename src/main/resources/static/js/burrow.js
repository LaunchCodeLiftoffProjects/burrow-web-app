function deleteFunction() {
    if (confirm("Are you sure?")) {
        document.getElementById("delete-room").submit();
    } else {
        window.history.go(-1);
    }
}