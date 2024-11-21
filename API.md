# API

## User

### Sign Up User

> `(POST) /api/signup`

- Request (application/json)

    - Body (object)
        - email: `user@example.com` (string, required) - User's email address
        - password: `Password123` (string, required) - User's password

- Response 201 (application/json)

    - Attributes (object)

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Fields are empty` (string) - Error message
            - reason: `invalidFields` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Email is wrong` (string) - Error message
            - reason: `invalidEmail` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Password is wrong` (string) - Error message
            - reason: `invalidPassword` (string) - Unique error type

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `User already exists` (string) - Error message
            - reason: `invalidUniqueKey` (string) - Unique error type

### Sign In User

> `(POST) /api/signin`

- Request (application/json)

    - Body (object)
        - email: `user@example.com` (string, required) - User's email address
        - password: `Password123` (string, required) - User's password

- Response 201 (application/json)

    - Attributes (object)
        - token: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...` (string, required) - Authentication token

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Fields are empty` (string) - Error message
            - reason: `invalidFields` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Email is wrong` (string) - Error message
            - reason: `invalidEmail` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `User is not found` (string) - Error message
            - reason: `userNotFound` (string) - Unique error type

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Authorization error` (string) - Error message
            - reason: `alreadyLoggedIn` (string) - Unique error type

### Get current user information

> `(GET) /api/profile`

- Response 200 (application/json)

    - Attributes (object)
        - name: `John Doe` (string) - User name
        - email: `user@example.com` (string) - User's email address
        - role: `manager|user` (string) - Access level

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

### Update user information

> `(PUT) /api/profile`

- Request (application/json)

    - Body (object)
        - email: `user@example.com` (string) - User's email address
        - name: `Dohn Doe` (string) - User's name

- Response 200 (application/json)

    - Attributes (object)
        - name: `John Doe` (string) - User name
        - email: `user@example.com` (string) - User's email address
        - role: `manager|user` (string) - Access level

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Email already exists` (string) - Error message
            - reason: `invalidUniqueKey` (string) - Unique error type

### Update password

> `(PUT) /api/profile/password`

- Request (application/json)

    - Body (object)
        - password: `my-new-password` (string) - User's new password

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Password is wrong` (string) - Error message
            - reason: `invalidPassword` (string) - Unique error type

### Terminate active session

> `(DELETE) /api/logout`

- Response 200 (application/json)

    - Attributes (object)

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

## Service

### Search

> `(GET) /api/search`

- Request (application/json)

    - Attributes: (object)
        - fromLatitude: `48.8575` (number, required) - Latitude of the start city
        - fromLongitude: `2.3514` (number, required) - Longitude of the start city
        - toLatitude: `40.4167` (number, required) - Latitude of the end city
        - toLongitude: `3.7033` (number, required) - Longitude of the end city
        - time: `1723669200000` (number, optional) - unix timestamp of the trip

- Response 200 (application/json)

    - Attributes: (object)
        - from: (object) - set of data about start point
            - stationId: `5` (number) - Unique station identifier
            - city: `Paris` (string) - City name
            - geolocation: (object) Station coordinates
                - latitude: `48.8575` (number) - Latitude of the start station
                - longitude: `2.3514` (number) - Longitude of the start station
        - to: (object) - set of data about end point
            - stationId: `48` (number) - Unique station identifier
            - city: `Madrid` (string) - City name
            - geolocation: (object) Station coordinates
                - latitude: `40.4167` (number) - Latitude of the end station
                - longitude: `3.7033` (number) - Longitude of the end station
        - routes: (array[object]) - List of available routes
            - id: `64` (number) - Route identifier
            - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
            - carriages:
              `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
              array[string]) - List of carriage types for the train
            - schedule: (array[object]) - List of potential rides
                - rideId: `44` (number) - Identifier of certain schedule for the route
                - segments: (array[object]) - List of road section between each station. Always 1 less than the number
                  of stations on the route
                    - time: `['2024-08-08T22:19:57.708Z', '2024-08-12T03:29:57.708Z']` (array[string]) - dates of the
                      start of movement on the section and the end of movement on the section in string
                      form - [departure_from_prev_station, arrival_at_next_station]
                    - price: (object) - set of prices for all carriage types on this route. It varies in size depending
                      on the train configuration
                        - dynamic-carriage-type-1: `210` (number) - the price of 210 units for _dynamic-carriage-type-1_
                          carriage type on current section
                    - occupiedSeats: (array[number]) - list of occupied seat numbers

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Start station not found` (string) - Error message
            - reason: `stationNotFound` (string) - Error type

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `End station not found` (string) - Error message
            - reason: `stationNotFound` (string) - Error type

### To get ride information

> `(GET) /api/search/{rideId}`

- Request (application/json)

    - Parameters:
        - rideId: `745` (number, required) - Identifier of the ride, it is instance of the route

- Response 200 (application/json)

    - Attributes: (object)
        - rideId: `745` (number) - Identifier of certain schedule for the route
        - routeId: `18` (number) - Identifier of corresponding route
        - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
        - carriages:
          `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
          array[string]) - List of carriage types for the train
        - schedule: (object) - Information about the ride
            - segments: (array[object]) - List of road section between each station. Always 1 less than the number of
              stations on the route
                - time: `['2024-08-08T22:19:57.708Z', '2024-08-12T03:29:57.708Z']` (array[string]) - dates of the start
                  of movement on the section and the end of movement on the section in string
                  form - [departure_from_prev_station, arrival_at_next_station]
                - price: (object) - set of prices for all carriage types on this route. It varies in size depending on
                  the train configuration
                    - dynamic-carriage-type-1: `210` (number) - the price of 210 units for _dynamic-carriage-type-1_
                      carriage type on current section
                - occupiedSeats: `[4,28,42,61]` (array[number]) - list of occupied seat numbers

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Ride not found` (string) - Error message
            - reason: `rideNotFound` (string) - Error type

### To make an order

> `(POST) /api/order`

- Request (application/json)

    - Body (object)
        - rideId: `745` (number, required) - Identifier of certain schedule for the route
        - seat: `27` (number, required) - Selected seat number
        - stationStart: `7` (number, required) - Station id which user boards the train
        - stationEnd: `61` (number, required) - Station id which user leaves the train

- Response 201 (application/json)

    - Attributes: (object)
        - id: `209` (string) - identifier of new order

- Response 400 (application/json)

    - Attributes: (object)
        - error:
            - message: `Ride not found` (string) - Error message
            - reason: `rideNotFound` (string) - Error type

- Response 400 (application/json)

    - Attributes: (object)
        - error:
            - message: `Invalid stations` (string) - Error message
            - reason: `invalidStations` (string) - Error type

- Response 400 (application/json)

    - Attributes: (object)
        - error:
            - message: `Parameters are irrelevant` (string) - Error message
            - reason: `invalidData` (string) - Error type

- Response 400 (application/json)

    - Attributes: (object)
        - error:
            - message: `Ride is already booked` (string) - Error message
            - reason: `alreadyBooked` (string) - Error type

- Response 400 (application/json)

    - Attributes: (object)
        - error:
            - message: `Trip is expired` (string) - Error message
            - reason: `invalidRide` (string) - Error type

### To cancel active order

> `(DELETE) /api/order/{orderId}`

- Request (application/json)

    - Parameters:
    - orderId: `215` (number, required) - Identifier of the order

- Response 200 (application/json)

    - Attributes: (object)

- Response 400 (application/json)

    - Attributes: (object)
        - error:
            - message: `Order is not found` (string) - Error message
            - reason: `orderNotFound` (string) - Error type

- Response 400 (application/json)
    - Attributes: (object)
        - error:
            - message: `Order is not active` (string) - Error message
            - reason: `orderNotActive` (string) - Error type

### To retrieve orders

> `(GET) /api/order`

- Request (application/json)

    - Attributes: (object)
        - all: `true` (boolean, optional) - allows manager to retrieve all orders

- Response 200 (application/json)
    - Attributes: (array[object])
        - id: `64` (number) - Order identifier
        - rideId: `45` (number) - Ride identifier
        - routeId: `18` (number) - Route identifier
        - seatId: `33` (number) - Seat index in the whole train
        - userId: `3` (number) - User identifier
        - status: `active|completed|rejected|canceled` (string) - Order status indicates current order state
        - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
        - carriages:
          `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
          array[string]) - List of carriage types for the train
        - schedule: (object)
            - segments: (array[object]) - List of road section between each station. Always 1 less than the number of
              stations on the route
                - time: `['2024-08-08T22:19:57.708Z', '2024-08-12T03:29:57.708Z']` (array[string]) - dates of the start
                  of movement on the section and the end of movement on the section in string
                  form - [departure_from_prev_station, arrival_at_next_station]
                - price: (object) - set of prices for all carriage types on this route. It varies in size depending on
                  the train configuration
                    - dynamic-carriage-type-1: `210` (number) - the price of 210 units for _dynamic-carriage-type-1_
                      carriage type on current section

## Admin

### Retrieve users

> `(GET) /api/users`

- Request (application/json)

    - Attributes: (object)

- Response 200 (application/json)
    - Attributes: (array[object])
        - id: `3` (number) - User identifier
        - email: `mail@mail.com` (string) - User email
        - name: `Dohn` (string) - User name
        - role: `user|manager` (string) - User role

### Retrieve carriage type list

> `(GET) /api/carriage`

- Response 200 (application/json)
    - Attributes (array[object])
        - code: `type5` (string) - Carriage unique identifier
        - name: `Only-women` (string) - Carriage name
        - rows: `18` (number) - amount of rows in carriage
        - leftSeats: `2` (number) - amount of seats to the left of the aisle
        - rightSeats: `2` (number) - amount of seats to the right of the aisle

### Create new carriage type

> `(POST) /api/carriage`

- Request (application/json)

    - Body (object)
        - name: `Only-women` (string) - Carriage name
        - rows: `18` (number) - amount of rows in carriage
        - leftSeats: `2` (number) - amount of seats to the left of the aisle
        - rightSeats: `2` (number) - amount of seats to the right of the aisle

- Response 201 (application/json)

    - Attributes (object)
        - code: `type18` (string) - Carriage unique identifier

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)
    - Attributes: (object)
        - error:
            - message: `Carriage already exists` (string) - Error message
            - reason: `invalidUniqueKey` (string) - Unique error type

### Update carriage type

> `(PUT) /api/carriage/{code}`

- Request (application/json)

    - Parameters: (object)
        - code: `type18` (string) - Carriage unique identifier
    - Body: (object)
        - name: `Only-women` (string) - Carriage name
        - rows: `16` (number) - amount of rows in carriage
        - leftSeats: `2` (number) - amount of seats to the left of the aisle
        - rightSeats: `3` (number) - amount of seats to the right of the aisle

- Response 200 (application/json)

    - Attributes: (object)
        - code: `type18` (string) - Carriage unique identifier

- Response 401 (application/json)

    - Attributes: (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)
    - Attributes: (object)
        - error:
            - message: `Carriage not found` (string) - Error message
            - reason: `recordNotFound` (string) - Unique error type

### Delete carriage type

> `(DELETE) /api/carriage/{code}`

- Request (application/json)

    - Parameters: (object)
        - code: `type18` (string) - Carriage unique identifier

- Response 200 (application/json)

    - Attributes (object)

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier
- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Carriage not found` (string) - Error message if carriage code is wrong
            - reason: `recordNotFound` (string) - Unique error type
- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Carriage is already used` (string) - Error message if some order exist with certain carriage
              type
            - reason: `recordInUse` (string) - Unique error type

### Retrieve station list

> `(GET) /api/station`

- Response 200 (application/json)
    - Attributes (array[object])
        - id: `5` (number) - Station identifier
        - city: `London` (string) - City name where station is located
        - latitude: `51.5074` (number) - Latitude of the station
        - longitude: `-0.1278` - Longitude of the station
        - connectedTo: `[{id: 4, distance: 44},...]` (array[object]) - List of connected stations, where _id_ -
          identifier, _distance_ - distance between them in km

### Create new station

> `(POST) /api/station`

- Request (application/json)

    - Body (object)
        - city: `Birmingham` (string) - City name where station is located
        - latitude: `52.4862` (number) - Latitude of the station
        - longitude: `-1.8904` - Longitude of the station
        - relations: `[4,7,9,14,21]` (array[number]) - List of related station identifiers

- Response 201 (application/json)

    - Attributes (object)
        - id: `17` (number) - New station identifier

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Invalid station data` (string) - Error message
            - reason: `invalidStationData` (string) - Wrong token identifier

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Invalid station relations` (string) - Error message
            - reason: `invalidStationData` (string) - Wrong token identifier

### Delete station

> `(DELETE) /api/station/{id}`

- Request (application/json)

    - Parameters:
        - id: `215` (number, required) - Identifier of the station

- Response 200 (application/json)

    - Attributes: (object)

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier
- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Station is already used` (string) - Error message if some route exist with certain station
            - reason: `recordInUse` (string) - Unique error type

### To retrieve routes

> `(GET) /api/route`

- Request (application/json)

    - Attributes: (object)

- Response 200 (application/json)
    - Attributes: (array[object])
        - id: `64` (number) - Route identifier
        - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
        - carriages:
          `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
          array[string]) - List of carriage types for the train

### To create new route

> `(POST) /api/route`

- Request (application/json)

    - Body (object)
        - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
        - carriages:
          `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
          array[string]) - List of carriage identifiers for the train

- Response 201 (application/json)

    - Attributes (object)
        - id: `17` (number) - New route identifier

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

### To update route

> `(PUT) /api/route/{id}`

- Request (application/json)

    - Parameters:
        - id: `17` (number, required) - Identifier of the route
    - Body (object)
        - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
        - carriages:
          `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
          array[string]) - List of carriage identifiers for the train

- Response 200 (application/json)

    - Attributes (object)
        - id: `17` (number) - Route identifier

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

### Delete route

> `(DELETE) /api/route/{id}`

- Request (application/json)

    - Parameters:
        - id: `17` (number, required) - Identifier of the route

- Response 200 (application/json)

    - Attributes: (object)

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Route not found` (string) - Error message if routeId is wrong
            - reason: `recordNotFound` (string) - Unique error type

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Route is already used` (string) - Error message if some order exist with certain route
            - reason: `recordInUse` (string) - Unique error type

### Retrieve route information

> `(GET) /api/route/{id}`

- Request (application/json)
- Parameters:

    - id: `17` (number, required) - Identifier of the route

- Response 200 (application/json)
    - Attributes: (array[object])
        - id: `64` (number) - Route identifier
        - path: `[33, 5, 62, 11, 48, 34]` (array[number]) - List of station identifiers
        - carriages:
          `['carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_2', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7', 'carriage_type_7']` (
          array[string]) - List of carriage types for the train
        - schedule: (array[object]) - List of potential rides
            - rideId: `44` (number) - Identifier of certain schedule for the route
            - segments: (array[object]) - List of road section between each station. Always 1 less than the number of
              stations on the route
                - time: `['2024-08-08T22:19:57.708Z', '2024-08-12T03:29:57.708Z']` ([string, string]) - dates of the
                  start of movement on the section and the end of movement on the section in string
                  form - [departure_from_prev_station, arrival_at_next_station]
                - price: (object) - set of prices for all carriage types on this route. It varies in size depending on
                  the train configuration
                    - dynamic-carriage-type-1: `210` (number) - the price of 210 units for _dynamic-carriage-type-1_
                      carriage type on current section

### Create new ride

> `(POST) /api/route/{routeId}/ride`

- Request (application/json)

    - Parameters:
        - routeId: `17` (number, required) - Identifier of the route
    - Body (object)
        - segments: (array[object])
            - time: `['2024-08-08T22:19:57.708Z', '2024-08-12T03:29:57.708Z']` ([string, string]) - dates of the start
              of movement on the section and the end of movement on the section in string
              form - [departure_from_prev_station, arrival_at_next_station]
            - price: (object) - set of prices for all carriage types on this route. It varies in size depending on the
              train configuration
                - dynamic-carriage-type-1: `210` (number) - the price of 210 units for _dynamic-carriage-type-1_
                  carriage type on current section

- Response 201 (application/json)

    - Attributes (object)
        - id: `215` (number) - New ride identifier

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Route not found` (string) - Error message if routeId is wrong
            - reason: `recordNotFound` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Parameters are irrelevant` (string) - Error message if request parameters are wrong
            - reason: `invalidData` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Time is irrelevant` (string) - Error message if time sequence is wrong
            - reason: `invalidData` (string) - Unique error type

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Price is irrelevant` (string) - Error message if price sequence is wrong
            - reason: `invalidData` (string) - Unique error type

### Update ride

> `(PUT) /api/route/{routeId}/ride/{rideId}`

- Request (application/json)

    - Parameters:
        - routeId: `17` (number, required) - Identifier of the route
        - rideId: `215` (number, required) - Identifier of the ride
    - Body (object)
        - segments: (array[object])
            - time: `['2024-08-08T22:19:57.708Z', '2024-08-12T03:29:57.708Z']` ([string, string]) - dates of the start
              of movement on the section and the end of movement on the section in string
              form - [departure_from_prev_station, arrival_at_next_station]
            - price: (object) - set of prices for all carriage types on this route. It varies in size depending on the
              train configuration
                - dynamic-carriage-type-1: `210` (number) - the price of 210 units for _dynamic-carriage-type-1_
                  carriage type on current section

- Response 200 (application/json)

    - Attributes (object)

- Response 401 (application/json)

    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Ride not found` (string) - Error message if routeId or rideId is wrong
            - reason: `recordNotFound` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Parameters are irrelevant` (string) - Error message if request parameters are wrong
            - reason: `invalidData` (string) - Unique error type

- Response 400 (application/json)

    - Attributes (object)
        - error:
            - message: `Time is irrelevant` (string) - Error message if time sequence is wrong
            - reason: `invalidData` (string) - Unique error type

- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Price is irrelevant` (string) - Error message if price sequence is wrong
            - reason: `invalidData` (string) - Unique error type

### Delete ride

> `(DELETE) /api/route/{routeId}/ride/{rideId}`

- Request (application/json)

    - Parameters:
        - routeId: `17` (number, required) - Identifier of the route
        - rideId: `215` (number, required) - Identifier of the ride

- Response 200 (application/json)

    - Attributes (object)

- Response 401 (application/json)
    - Attributes (object)
        - error:
            - message: `Access is not granted` (string) - Error message
            - reason: `invalidAccessToken` (string) - Wrong token identifier
- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Ride not found` (string) - Error message if routeId or rideId is wrong
            - reason: `recordNotFound` (string) - Unique error type
- Response 400 (application/json)
    - Attributes (object)
        - error:
            - message: `Ride is already used` (string) - Error message if some order exist with certain ride
            - reason: `recordInUse` (string) - Unique error type
