let buttonGenerate = document.getElementById('buttonGenerate');
buttonGenerate.addEventListener("click", generate);


function generate() {
    let thisDate = new Date("2021/01/01").toISOString();


    $.ajax({
        type: 'GET',
        // dataType: 'text',
        data: {date: thisDate},
        // async: true,
        url: '/generaTabella',
        contentType: 'text'
    }).done(function (response) {
        let risposte = response;
        caricamento();


    }).fail(function (err) {
        console.log(err);
        console.log('error');
    })
}