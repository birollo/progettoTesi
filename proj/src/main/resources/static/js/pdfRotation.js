let buttonPDF= document.getElementById('buttonPDF');
buttonPDF.addEventListener("click", PDFRotation);
buttonPDF.style.display = "none";

$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', toggleCheck);



function toggleCheck() {
    let isChose = choser.value;
    if (isChose === "Scegli mese") {
        buttonPDF.style.display = "none";
    } else {
        buttonPDF.style.display = "block";
    }
}

function PDFRotation() {
    buttonPDF.style.display = "none";


    let thisDate = new Date(from.valueOf()).toISOString();


    $.ajax({
        type: 'GET',
        // dataType: 'text',
        data: {date: thisDate},
        // async: true,
        url: '/rotation/download',
        contentType: 'text'
    }).done(function (response) {
        buttonPDF.style.display = "block";
        let risposte = response;
        // caricamento();


    }).fail(function (err) {
        console.log(err);
        console.log('error');
    })
}