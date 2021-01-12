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

function deleteDevice() {
    let device = $('#deviceId').val();
    if (confirm(`Remove ${device}?`)) {
        document.getElementById("delete-device").submit();
    }
}

function deleteComponent() {
    let component = $('#componentId').val();
    if (confirm(`Remove ${component}?`)) {
        document.getElementById("delete-component").submit();
    }
}


$(function() {
    $( "#installDate" ).datepicker({
    });
 });