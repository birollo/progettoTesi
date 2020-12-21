

let buttonClear= document.getElementById('buttonClear');
buttonClear.addEventListener("click", clear);
buttonClear.style.display = "none";

$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', toggleCheck);



function toggleCheck() {
    let isChose = choser.value;
    if (isChose === "Scegli mese") {
        buttonClear.style.display = "none";
    } else {
        buttonClear.style.display = "block";
    }
}

function clear() {
    buttonClear.style.display = "none";


    let thisDate = new Date(from.valueOf()).toISOString();


    $.ajax({
        type: 'GET',
        // dataType: 'text',
        data: {date: thisDate},
        // async: true,
        url: '/svuotaTabella',
        contentType: 'text'
    }).done(function (response) {
        buttonClear.style.display = "block";
        let risposte = response;
        caricamento();


    }).fail(function (err) {
        console.log(err);
        console.log('error');
    })
}