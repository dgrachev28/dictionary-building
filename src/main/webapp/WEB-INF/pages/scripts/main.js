$(document).ready(function () {

    var classes = {
        topPanelButton: "top-panel__enter__button",
        topPanelText: "top-panel__enter__text"
    };

    var $topPanelButton,
        $topPanelText;

    function initVars() {
        $topPanelButton = $("." + classes.topPanelButton);
        $topPanelText = $("." + classes.topPanelText);
    }


    function bindEvents() {
        $topPanelButton.on("click", topPanelButtonClickHandler);
    }


    function topPanelButtonClickHandler() {

        $.ajax({
            method: 'GET',
            url: '/findEntrance',
            data: {verb: $topPanelText.val()},
            success: function (data) {
                console.log(data);
            }
        });
    }
    
    function init() {
        initVars();
        bindEvents();


    }


    // Начало работы
    init();

});
