# Building a room scheduler

This application schedules rooms for use. JDO parent-child relationship is used for this application.
The application does the following things:
1. The ability to add and delete rooms. The same room cannot be added twice nor a room can be deleted if it is currently booked.
2. The ability to add and delete bookings for a room. A booking can only be made if it doesn't clash or overlap with other bookings for that room.
3. Possible to view all the bookings for a given day on a room. 
4. Option to delete a booking when a room is viewed for its bookings.