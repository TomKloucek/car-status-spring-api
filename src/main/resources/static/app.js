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

