let table = document.getElementById("report");
let from = new Date("2021/01/01").valueOf();
$('.from').datepicker({
    autoclose: true,
    minViewMode: 1,
    format: 'mm/yyyy'
}).on('changeDate', function (selected) {
    katon = new Date(selected.date.valueOf());
    katon.setDate(katon.getDate(new Date(selected.date.valueOf())));
    $('.to').datepicker('setStartDate', katon);
    from = new Date(selected.date.valueOf()).valueOf();
    caricamento();
});


function caricamento() {
    let url = window.location.href;
    let id = url.split("/")[4];
    let thisDate = new Date(from.valueOf()).toISOString();
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            // dataType: 'text',
            data: {
                id: id,
                date: thisDate
            },
            // async: true,
            url: '/reports',
            contentType: 'text'
        }).done(function (response) {
            table.innerHTML = "";

            let dates = response.dates;
            let names = response.shifftsName;
            let da = response.shifftsStart;
            let a = response.shifftsEnd;
            let servizio = response.durata;
            let valore = response.valore;
            let totMinuti = response.totaleMinuti;
            let vacanze = response.vacanze;
            let feriali = response.feriali;
            let sabati = response.sabati;
            let domenicheFestivi = response.domenicheFestivi;
            let malattia = response.malattia;
            let totMese = 0;
            let totVacanze = 0;
            let totFeriale = 0;
            let totSabati = 0;
            let totDomeniche =0;
            let totMalattia =0;
            for (let y = 0; y < dates.length; y++) {
                totMese += totMinuti[y];
                totVacanze += vacanze[y];
                totFeriale += feriali[y];
                totSabati += sabati[y];
                totDomeniche += domenicheFestivi[y];
                totMalattia += malattia[y];

                table.innerHTML +=  "<tr>" +
                                    "<td>" + dates[y] + "</td>" +
                                    "<td>" + names[y] + "</td>" +
                                    "<td>" + da[y].split(":")[0] + ":" + da[y].split(":")[1] + "</td>" +
                                    "<td>" + a[y].split(":")[0] + ":" + a[y].split(":")[1] + "</td>" +
                                    "<td>" + servizio[y] + "</td>" +
                                    "<td>" + valore[y] + "</td>" +
                                    "<td>" + totMinuti[y] + "</td>" +
                                    "<td>" + vacanze[y] + "</td>" +
                                    "<td>" + feriali[y] + "</td>" +
                                    "<td>" + sabati[y] + "</td>" +
                                    "<td>" + domenicheFestivi[y] + "</td>" +
                                    "<td>" + malattia[y] + "</td>" +
                                    "</tr>";
            }
            table.innerHTML += "<thead>" +
                                "<tr>" +
                                "<th scope=\"col\"></th>" +
                                "<th scope=\"col\"></th>" +
                                "<th scope=\"col\"></th>" +
                                "<th scope=\"col\"></th>" +
                                "<th scope=\"col\"></th>" +
                                "<th scope=\"col\"></th>" +
                                "<th scope=\"col\">Tot. minuti</th>" +
                                "<th scope=\"col\">Tot. vacanza</th>" +
                                "<th scope=\"col\">Tot. feriale</th>" +
                                "<th scope=\"col\">Tot sabati</th>" +
                                "<th scope=\"col\">Tot. Dom. e festivi</th>" +
                                "<th scope=\"col\">Tot. malattia</th>" +
                                "</tr>" +
                                "</thead>";

            table.innerHTML += "<th>" +
                                "<td>" +  "</td>" +
                                "<td>" +  "</td>" +
                                "<td>" +  "</td>" +
                                "<td>" +  "</td>" +
                                "<td>" + "</td>" +
                                "<td>" + totMese + "</td>" +
                                "<td>" + totVacanze + "</td>" +
                                "<td>" + totFeriale + "</td>" +
                                "<td>" + totSabati + "</td>" +
                                "<td>" + totDomeniche + "</td>" +
                                "<td>" + totMalattia +  "</td>" +
                                "</th>";


        }).fail(function (err) {
            console.log(err);
            console.log('error');
        })
    });
}

//parte tablescrolling
$(document).ready(function () {
    $('#dtVerticalScrollExample').DataTable({
        "scrollY": "200px",
        "scrollCollapse": true,
    });
    $('.dataTables_length').addClass('bs-select');
});
