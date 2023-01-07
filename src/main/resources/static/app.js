function inflateTyres() {
    // Instantiating new EasyHTTP class

    const http = new EasyHTTP;


// Update Post
    http.put(
        'http://localhost:8080/carstatus/rest/tyre/inflate')

        // Resolving promise for response data
        .then(data => console.log(data))

        // Resolving promise for error
        .catch(err => console.log(err));
}

function rechargeBattery() {
    // Instantiating new EasyHTTP class

    const http = new EasyHTTP;


// Update Post
    http.put(
        'http://localhost:8080/carstatus/rest/battery/charge')

        // Resolving promise for response data
        .then(data => console.log(data))

        // Resolving promise for error
        .catch(err => console.log(err));
}

function refillCoolingLiquid() {
    // Instantiating new EasyHTTP class

    const http = new EasyHTTP;


// Update Post
    http.put(
        'http://localhost:8080/carstatus/rest/liquid/cooling/refill')

        // Resolving promise for response data
        .then(data => console.log(data))

        // Resolving promise for error
        .catch(err => console.log(err));
}

function refillBrakingLiquid() {
    // Instantiating new EasyHTTP class

    const http = new EasyHTTP;


// Update Post
    http.put(
        'http://localhost:8080/carstatus/rest/liquid/braking/refill')

        // Resolving promise for response data
        .then(data => console.log(data))

        // Resolving promise for error
        .catch(err => console.log(err));
}

function changePosition(direction) {
    // Instantiating new EasyHTTP class

    console.log(direction)
    const http = new EasyHTTP

    switch (direction) {
        case 'up':
            http.put(
                'http://localhost:8080/carstatus/rest/seat/up')
                .then(data => {if (data.err !== undefined) { alert(data.err) }})
                .catch(err => alert(err.data));
            break;
        case 'left':
            http.put(
                'http://localhost:8080/carstatus/rest/seat/left')
                .then(data => {if (data.err !== undefined) { alert(data.err) }})
                .catch(err => alert(err.data));
            break;
        case 'right':
            http.put(
                'http://localhost:8080/carstatus/rest/seat/right')
                .then(data => {if (data.err !== undefined) { alert(data.err) }})
                .catch(err => alert(err.data));
            break
        case 'down':
            http.put(
                'http://localhost:8080/carstatus/rest/seat/down')
                .then(data => {if (data.err !== undefined) { alert(data.err) }})
                .catch(err => alert(err.data));
            break
    }
}


function genRoadTrip() {
    const http = new EasyHTTP;
    http.put(
        'http://localhost:8080/carstatus/rest/simulation')
        .then(data => console.log(data))
        .catch(err => console.log(err));
}