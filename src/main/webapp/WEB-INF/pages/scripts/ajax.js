$(document).ready(function () {

    function ajax(config) {
        config.success = function (response) {
            if (response.success) {
                config.success.call(this, response.data);
            } else {
                alert("Error: " + response.errorMsg);
            }
        };

        $.ajax(config);
    }

});
