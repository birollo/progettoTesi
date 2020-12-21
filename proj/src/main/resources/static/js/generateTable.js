

let buttonGenerate = document.getElementById('buttonGenerate');
buttonGenerate.addEventListener("click", generate);
buttonGenerate.style.display = "none";

$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', toggleCheck);



function toggleCheck() {
    let isChose = choser.value;
    if (isChose === "Scegli mese") {
        buttonGenerate.style.display = "none";
    } else {
        buttonGenerate.style.display = "block";
    }
}

function generate() {
    buttonGenerate.style.display = "none";
    document.getElementById('generateLoading').style.display = "block";

    let thisDate = new Date(from.valueOf()).toISOString();


    $.ajax({
        type: 'GET',
        // dataType: 'text',
        data: {date: thisDate},
        // async: true,
        url: '/generaTabella',
        contentType: 'text'
    }).done(function (response) {
        document.getElementById('generateLoading').style.display = "none";
        buttonGenerate.style.display = "block";
        let risposte = response;
        caricamento();


    }).fail(function (err) {
        console.log(err);
        console.log('error');
    })
}