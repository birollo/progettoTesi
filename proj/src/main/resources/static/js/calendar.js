


var lista_persone = [];
// let config = {};
let GSTCState = {};

async function aggiornamento(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data), // body data type must match "Content-Type" header
    });
    console.log("ho fatto");
    return response.json(); // parses JSON response into native JavaScript objects
}

function caricamento() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            // async: true,
            url: '/listaPersone',
            contentType: 'text'
        }).done(function (response) {

            lista_persone = response;
            for (var x = 0; x < lista_persone.length; x++) {
                // console.log("x" + lista_persone.length);
                if (lista_persone[x].turni.length === 0){

                }else {
                    for (var y = 0; y < lista_persone[x].turni.length; y++){
                        let thisId =  lista_persone[x].turni[y].turno.name;
                        let thisName = lista_persone[x].name;
                        let thisSurname = lista_persone[x].surname;
                        let thisDate = new Date(lista_persone[x].turni[y].date).toString();
                        createItem(thisId, thisName + " " + thisSurname, thisDate);
                    }
                }
                createRow(lista_persone[x].name + " " + lista_persone[x].surname);
            }

            createColumns();
            let myConfig = createConfig();

            // let GSTCState = GSTC.api.stateFromConfig(myConfig);
            GSTCState = (window.state = GSTC.api.stateFromConfig(myConfig));
            // GSTCState.subscribe('config.plugin.ItemMovement', itemMovement => {
            //     if (!itemMovement || !itemMovement.item) return;
            //     state.update(`config.chart.items.${itemMovement.item.id}.isResizing`, itemMovement.item.resizing);
            // });


            const element = document.getElementById('app');

            const gstc = GSTC({
                element,
                state: GSTCState
            });
            window.gstc = gstc; // debug

            // const app = GSTC({
            //     element: document.getElementById("app"),
            //     state
            // });

            //todo: porcata
            var lista_righe = document.getElementsByClassName("gantt-schedule-timeline-calendar__list-column-row");
            // console.log("k" + lista_righe.length);
            for (var k = 0; k < lista_persone.length; k++) {
                var thisId = lista_persone[k].id;
                var aTag = document.createElement('a');
                aTag.setAttribute('href', "/user/" + thisId);
                aTag.innerText = "->";
                lista_righe[k].appendChild(aTag);

            }
            //todo: inserire tot mese scorso come i link (prendendo la seconda parte)

        }).fail(function (err) {
            console.log(err);
            console.log('error');
        })
    });
}

const rows = {};

function createRow(i) {
    const id = i.toString();
    rows[id] = {
        id,
        label: `${id}`,
    };
}

const startDate = GSTC.api
    .date()
    .subtract(1, 'month')
    .valueOf();


let columns = {};

function createColumns() {
    columns = {
        data: {
            'label-column-or-whatever': {
                id: 'label-column-or-whatever',
                data: 'label',
                width: 150,
                // expander: true,
                header: {
                    content: 'Cognome'
                }
            },
            'Mese precedente': {
                id: 'Mese precedente',
                isHTML: true,
                data: '<a>profilo</a>',
                width: 150,
                header: {
                    content: 'Mese precedente'
                }
            },
            'Mese corrente': {
                id: 'Mese corrente',
                isHTML: true,
                data: '<a>profilo</a>', //todo: controllare se si puo usare data al posto di innerHTML
                width: 150,
                header: {
                    content: 'Mese corrente'
                }
            }
        }
    }
}

const items = {};
let count = 0;
function createItem(id, name, date) {
    let endDay = new Date(date);
    let startDate = new Date(date);
    let thisId = id.toString();
    endDay.setDate(endDay.getDate() + 1);
    items[count] = {
        id: count,
        label: id,
        rowId: name,
        moveable: false,
        time: {
            start: startDate.valueOf(),
            end: endDay.valueOf()
        },
        style: {background: 'green'}
    };
    // items[items.length + 1] = {
    //     thisId,
    //     rowId: name,
    //     moveable: true,
    //     label: id,
    //     time: {
    //         start: date.valueOf(),
    //         end: endDay.valueOf(),
    //     },
    //     style: {background: 'green'}
    // };
    count++;
}


class ItemClickAction {
    constructor(element, data) {
        this.data = data;
        this.onClick = this.onClick.bind(this);
        element.addEventListener('click', this.onClick);
        // console.log("costruttore items");
    }

    update(element, data) {
        this.data = data;
    }

    destroy(element, data) {
        element.removeEventListener('click', this.onClick);
    }

    onClick(event) {
        console.log("this.data" + this.data);

        let message = prompt("Inserisci il numero del nuovo turno", "");
        if (message != null) {
            let itemId = message;
            let rowId = this.data.item.rowId;
            let date = new Date(this.data.item.time.start);
            //console.log("message" + message);
            createItem(itemId, rowId, date);
            //todo: aggiorna con parametro un assegnazioe e cambiare lista<Ass.> in controller
            let jsonAssegnazione = {
                "turno": itemId,
                "persona": rowId,
                "data": date
            };
            aggiornamento('http://localhost:8080/aggiornamento', jsonAssegnazione);

            GSTCState.update('config', config => {
                config.list.rows = rows;
                config.chart.items = items;
                config.chart.time.from = new Date('2020-01-01').getTime();
                config.chart.time.to = new Date('2020-01-14').getTime();
                return config;
            });

        }
    }
}


class RowClickAction {
    constructor(element, data) {
        this.data = data;
        this.onClick = this.onClick.bind(this);
        element.addEventListener('click', this.onClick);
    }

    update(element, data) {
        this.data = data;
    }

    destroy(element, data) {
        element.removeEventListener('click', this.onClick);
    }

    onClick(event) {
            let rowId = this.data.id.split(":")[0];
            let date = new Date(this.data.id.split(":")[1] + ":00");
            location.href = "assegnazione/" + rowId + "/" + date;

        // console.log("this.data" + this.data.id);
        // modal();
        // let message = prompt("Inserisci il turno", "1");
        // if (message != null) {
        //     let itemId = message;
        //     let rowId = this.data.id.split(":")[0];
        //     let date = new Date(this.data.id.split(":")[1] + ":00");
        //     // console.log("message" + message);
        //     createItem(itemId, rowId, date);
        //     let jsonAssegnazione = {
        //         "turno": itemId,
        //         "persona": rowId,
        //         "data": date
        //     };
        //     aggiornamento('http://localhost:8080/aggiornamento', jsonAssegnazione);
        //     GSTCState.update('config', config => {
        //         config.list.rows = rows;
        //         config.chart.items = items;
        //         config.chart.time.from = new Date('2020-01-01').getTime();
        //         config.chart.time.to = new Date('2020-01-14').getTime();
        //         return config;
        //     });
        //
        // }
    }
}

function createConfig() {

    const config = {
        plugins: [
            ItemMovement({
                // moveable: true,
                resizable: false,
                collisionDetection: true
            }),
            Selection({
                selected(data, type) {
                    console.log(data, type);
                }
            }),
            CalendarScroll(),
            // WeekendHighlight()
        ],
        list: {
            rowHeight: 35,
            rows,
            columns,
        },
        chart: {
            // items: {
            //     'R': {
            //         id: 'R',
            //         rowId: 'Paolo Aguiari',
            //         moveable: true,
            //         label: 'R',
            //         time: {
            //             start: new Date('2020-01-06').getTime(),
            //             end: new Date('2020-01-07').getTime()
            //         },
            //         style: {background: 'grey'}
            //     },
            //     '3711': {
            //         id: '3711',
            //         rowId: 'Rolando Aguiari',
            //         moveable: true,
            //         label: '3711',
            //         time: {
            //             start: new Date('2020-01-06').getTime(),
            //             end: new Date('2020-01-07').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     'C': {
            //         id: 'C',
            //         rowId: 'Luca Albini',
            //         moveable: true,
            //         // resizeable: false,
            //         label: 'C',
            //         time: {
            //             start: new Date('2020-01-06').getTime(),
            //             end: new Date('2020-01-07').getTime()
            //         },
            //         style: {background: 'grey'}
            //     },
            //     'R2': {
            //         id: 'R2',
            //         rowId: 'Aurelio Barelli',
            //         moveable: true,
            //         // resizeable: false,
            //         label: 'R',
            //         time: {
            //             start: new Date('2020-01-06').getTime(),
            //             end: new Date('2020-01-07').getTime()
            //         },
            //         style: {background: 'grey'}
            //     },
            //     '121': {
            //         id: '121',
            //         rowId: 'Paolo Aguiari',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '121',
            //         time: {
            //             start: new Date('2020-01-07').getTime(),
            //             end: new Date('2020-01-08').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '711': {
            //         id: '711',
            //         rowId: 'Rolando Aguiari',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '711',
            //         time: {
            //             start: new Date('2020-01-07').getTime(),
            //             end: new Date('2020-01-08').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '111': {
            //         id: '111',
            //         rowId: 'Luca Albini',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '111',
            //         time: {
            //             start: new Date('2020-01-07').getTime(),
            //             end: new Date('2020-01-08').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '31': {
            //         id: '31',
            //         rowId: 'Aurelio Barelli',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '31',
            //         time: {
            //             start: new Date('2020-01-07').getTime(),
            //             end: new Date('2020-01-08').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '10': {
            //         id: '10',
            //         rowId: 'Paolo Aguiari',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '10',
            //         time: {
            //             start: new Date('2020-01-08').getTime(),
            //             end: new Date('2020-01-09').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '711b': {
            //         id: '711b',
            //         rowId: 'Rolando Aguiari',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '711',
            //         time: {
            //             start: new Date('2020-01-08').getTime(),
            //             end: new Date('2020-01-09').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '121b': {
            //         id: '121b',
            //         rowId: 'Barelli',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '121',
            //         time: {
            //             start: new Date('2020-01-08').getTime(),
            //             end: new Date('2020-01-09').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '31b': {
            //         id: '31b',
            //         rowId: 'Barraco',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '31',
            //         time: {
            //             start: new Date('2020-01-08').getTime(),
            //             end: new Date('2020-01-09').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '20': {
            //         id: '20',
            //         rowId: 'Aguiari',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '20',
            //         time: {
            //             start: new Date('2020-01-09').getTime(),
            //             end: new Date('2020-01-10').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '711c': {
            //         id: '711c',
            //         rowId: 'Albini',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '711',
            //         time: {
            //             start: new Date('2020-01-09').getTime(),
            //             end: new Date('2020-01-10').getTime()
            //         },
            //         style: {background: 'green'}
            //     },
            //     '151': {
            //         id: '151',
            //         rowId: 'Barelli',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '151',
            //         time: {
            //             start: new Date('2020-01-09').getTime(),
            //             end: new Date('2020-01-10').getTime()
            //         },
            //         style: {background: 'yellow', color: 'black'}
            //     },
            //     '31c': {
            //         id: '31c',
            //         rowId: 'Barraco',
            //         moveable: true,
            //         // resizeable: false,
            //         label: '31',
            //         time: {
            //             start: new Date('2020-01-09').getTime(),
            //             end: new Date('2020-01-10').getTime()
            //         },
            //         style: {background: 'green'}
            //     }
            // },
            items,
            time: {
                //inizio calendario
                from: new Date('2020-01-01').getTime(),

                //fine calendario
                to: new Date('2020-01-14').getTime()
                // period: 'day'
            }
        },
        actions: {
            'chart-timeline-items-row-item': [ItemClickAction],
            'chart-timeline-grid-row-block': [RowClickAction],
        },
    };
    return config;
}

// //creazione calendario
// let GSTCState = (window.state = GSTC.api.stateFromConfig(config));
//
// const element = document.getElementById('app');

// const gstc = GSTC({
//     element,
//     state: GSTCState
// });


