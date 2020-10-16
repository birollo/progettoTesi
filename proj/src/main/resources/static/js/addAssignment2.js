document.getElementById('thisTurno').addEventListener('change', computeDifference)

function computeDifference() {
    let value = document.getElementById('thisTurno').value;
    $.ajax({
        type: 'GET',
        // async: true,
        url: '/assegnazione/{id}/{date}/getStartTime',
        data: {
            turno: value,
        },
        contentType: 'text'
    }).done(function (response) {
        let turno = response;
        let startTime = turno.startTime.split(":");
        let endTime = document.getElementById("thisEnd").innerText.toString();
        startTime = startTime[0] +":"+ startTime[1];
        document.getElementById("thisStart").innerHTML = startTime;

        let dt1 =  new Date("October 14, 2014 " + startTime + ":00");
        let dt2 = new Date("October 13 2014 " + endTime + ":00");
        var diff =(dt2.getTime() - dt1.getTime()) / 1000;
        diff /= (60 * 60);
        var ore = Math.abs(Math.trunc(diff));
        var minuti = Math.round(Math.abs((diff % 1) *60));

        if (ore < 12){
            document.getElementById('difference').style.backgroundColor = "red";
        }else {
            document.getElementById('difference').style.backgroundColor = "lightgreen";
        }

        if (minuti.toString().length === 1){
            minuti = "0"+minuti;
        }
        document.getElementById('difference').innerHTML = ore + ":" + minuti;

    }).fail(function (err) {
        console.log(err);
        console.log('error');
    })
}


