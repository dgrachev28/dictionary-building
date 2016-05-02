$(document).ready(function () {

    var classes = {
        searchSectionButton: 'search-section__button',
        searchSectionInput: 'search-section__input',
        verbInfoSectionVerb: 'verb-info-section__verb',
        sentenceSectionSentence: 'sentence-section__sentence',
        wordDescriptionSection: 'words-description-section'
    };

    var $searchSectionButton,
        $searchSectionInput,
        $verbInfoSectionVerb,
        $sentenceSectionSentence,
        $wordDescriptionSection;

    var actantColors = ['#9CB9FB', '#90F7A6', '#FFF084', '#FB9CB2'];
    var actantColorCounter = 0;
    var verbDescription;

    function initVars() {
        $searchSectionButton = $("." + classes.searchSectionButton);
        $searchSectionInput = $("." + classes.searchSectionInput);
        $verbInfoSectionVerb = $("." + classes.verbInfoSectionVerb);
        $sentenceSectionSentence = $("." + classes.sentenceSectionSentence);
        $wordDescriptionSection = $("." + classes.wordDescriptionSection);
    }

    function bindEvents() {
        $searchSectionButton.on('click', searchSectionButtonClickHandler);
    }

    function searchSectionButtonClickHandler() {

        ajax({
            method: 'GET',
            url: '/findEntrance',
            data: {verb: $searchSectionInput.val()},
            success: function (data) {
                console.log(data);
                verbDescription = data;
                $verbInfoSectionVerb.html(data.verb);
                $sentenceSectionSentence.empty();

                for (var i = 0; i < data.sentenceWords.length; i++) {
                    var sentenceWord = data.sentenceWords[i];
                    $sentenceSectionSentence.append(getSentenceWordSpan(sentenceWord));
                }

                var $sentenceSectionWords = $('.' + classes.sentenceSectionSentence + ' span');
                var $verb = $sentenceSectionWords[data.verbPosition];
                $($verb).addClass('sentence-section__word_main-verb');


                $wordDescriptionSection.empty();
                setVerbDescription(data);
                handleActants(data, $sentenceSectionWords);
            }
        });
    }

    function getSentenceWordSpan(sentenceWord) {
        return '<span class="sentence-section__word">' + sentenceWord.word + '</span>' + sentenceWord.delimiter;
    }

    function setVerbDescription(data) {
        var wordTitle = getVerbPropertyHtml(data.verb);
        $wordDescriptionSection.append(wordTitle);
        var sd = $wordDescriptionSection.find(':last-child');
        sd.find('[name="transitivity"]').val(data.transitivity);
        sd.find('[name="perfection"]').val(data.perfection);
    }

    function handleActants(data, $sentenceSectionWords) {
        var actants = data.actants;
        for (var i = 0; i < actants.length; i++) {
            var actant = actants[i];
            var cssNextColor = getCssNextColor();
            $($sentenceSectionWords[actant.pretextPosition]).css(cssNextColor);
            $($sentenceSectionWords[actant.nounPosition]).css(cssNextColor);
        }

        addActantsProperty();
    }

    function addActantsProperty() {
        var actants = verbDescription.actants;
        for (var i = 0; i < actants.length; i++) {
            var actant = actants[i];

            var pretext = verbDescription.sentenceWords[actant.pretextPosition].word;
            var noun = verbDescription.sentenceWords[actant.nounPosition].word;

            var actantHtml = getActantPropertyHtml(pretext + " " + noun);
            $wordDescriptionSection.append(actantHtml);

            initSelect();
        }
    }


    function initSelect() {
        $(".jsSelect").select2({
            minimumResultsForSearch: -1
        });
    }

    function getCssNextColor() {
        if (++actantColorCounter == actantColors.length) {
            actantColorCounter = 0;
        }
        return {backgroundColor: actantColors[actantColorCounter]};
    }

    function getVerbPropertyHtml(wordTitle) {
        return '<div class="words-description-section__item">' +
            '<div class="words-description-section__title">' + wordTitle + '</div>' +
            '<div class="words-description-section__list">' +
            '<div class="words-description-section__list-item">' +
            '<div class="words-description-section__label">Переходность</div>' +
            '<select name="transitivity" class="words-description-section__select jsSelect">' +
            '<option value="п" selected>переходный (п)</option>' +
            '<option value="нп">непереходный (нп)</option>' +
            '<option value="п/нп">смешанный (п/нп)</option>' +
            '</select>' +
            '</div>' +
            '<div class="words-description-section__list-item">' +
            '<div class="words-description-section__label">Вид</div>' +
            '<select name="" class="words-description-section__select jsSelect">' +
            '<option value="св">совершенный (св)</option>' +
            '<option value="нсв">несовершенный (нсв)</option>' +
            '<option value="св/нсв">смешанный (св/нсв)</option>' +
            '</select>' +
            '</div>' +
            '</div>' +
            '</div>';
    }


    function getActantPropertyHtml(actantTitle) {
        return '<div class="words-description-section__item">' +
            '<div class="words-description-section__title">' + actantTitle + '</div>' +
            '<div class="words-description-section__list">' +
            '<div class="words-description-section__list-item">' +
            '<div class="words-description-section__label">Падеж</div>' +
            '<select name="" class="words-description-section__select jsSelect">' +
            '<option value="" selected>именительный</option>' +
            '<option value="">родительный</option>' +
            '<option value="">дательный</option>' +
            '<option value="">винительный</option>' +
            '</select>' +
            '</div>' +
            '</div>' +
            '</div>';
    }

    function init() {
        initVars();
        bindEvents();
    }


    function ajax(config) {
        var successFn = config.success;
        config.success = function (response) {
            if (response.success) {
                successFn.call(this, response.data);
            } else {
                alert("Error: " + response.errorMsg);
            }
        };

        $.ajax(config);
    }

    // Начало работы
    init();

});
