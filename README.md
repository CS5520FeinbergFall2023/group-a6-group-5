## Assignment - A8 Stick it to Em'

## Project Description

Our project establishes connection with firebase to send stickers with users in real-time. 

## Challenges faced

1. Storing stickers in Firebase: Instead of the traditional approach, we went ahead with storing the stickers to Firebase and retrieving it for sharing with users using identifiers.
2. Restructuring the database: Restructuring the database to store messages and users in separate tables rather than linking them to the same table entries.
3. Handling multiple classes and interfaces: Since we are building this on top of the existing app, we have to add more classes and interfaces to support full functionality.

In addition to these challenges, we have met several other constraints:

- **Equal Team Contributions:** Each team member has contributed equally to the project's success.
- **Smooth Interactions:** Our application ensures that the main thread is not blocked, guaranteeing smooth and responsive interactions.
- **Real-time updates:** We have implemented code to show updates in real-time.
- **InstanceState Management:** To maintain a seamless user experience, we have properly saved and handled the instance states for user and messages sent.

-------------------------------------------------------------------------------------------------------------------------------------------------------------

## Author Contributions 

### Ajay Inavolu

1. Chat Display & History Fixes: Rectified several chat-related issues including:
  - Display anomalies during screen rotation.
  - Display of chat history on the chat page.
  - Stickers display problem.
  - Enhanced chat display to show only engaged users.
  - Improved user representation in chat history by displaying names instead of userIDs.
2. User and Chat Activity Enhancements:
  - Introduced chat history to the list page, showing only prior interactions with specific users.
  - Modified UserListActivity to fetch the list of chat users.
  - Added a new chat activity dedicated to sending and receiving stickers.
3. Database & Model Adjustments:
  - Tweaked database attributes to circumvent chat history display errors.
  - Introduced model classes for stickers and message files, and another set for stickers and messages.
4. User Interface Enhancements:
  - Integrated a Floating Action Button to showcase a list of all potential chat users.
5. Codebase Refinements:
  - Adapted MainActivity and UserListActivity for better compatibility with the new database structure.
  - Transitioned from using "userName" to "userID" in specific activity data exchanges

### Jiaming Xu

1. Personal profile page
  - Design and implement the personal profile page
  - Show user sticker use in recycler view
  - Fetch and calculate sticker usage with firebase query
  - Adapt view for portrait view and landscape view with dynamic column count
2. Navigation bar & intent
  - Design & implement the bottom navigation bar across pages
  - Pass down user ID and username through intent to allow personal information display like welcome messages and other database query.
3. Database
  - Design the basic structure of the database
  - Write function for showing image store on firebase storage
  - Populate the database with initial data
4. User login
  - User login and register functionality on the first page
  - Introduce User model class
5. UI improvement and bug fix
  Aside from pages mentioned above, also improved the UI in user chat as well as contacts list by bringing scroller view, guide text, etc. Help with debugging and problem fixing in other pages

### Kiran Shatiya Thirugnanasambantham Radhabai

1. StickerAdapter class:
  - Implemented the highlight and storing sticker objects when selected functionality
  - Passed on the selected stickers to UserChatActivity Functionality
  - Ensured real-time updates are happening in UI and database when stickers are passed onto the ChatActivity class
2. UserChatActivity class:
  - Implemented the sendMessage() functionality that sends the sticker
  - Modified the initial functionality of sending stickers directly to store and send when the button is clicked
  - Stored the objects and passed it onto the UserChat history
  - Established the connection with Firebase and update the data when message is sent
3. About Us page:
  - Created the about us page with group name and details
4. Codebase refinements for UI:
  - Fixed the bottom nav and colours for all pages
  - Modified layouts for chat page
  Apart from the above changes, I sat for initial discussions and supported in testing / debugging the notification issues, message sending and database issues, and also wireframing the layouts for each page.

### Vishrutha Abbaiah Reddy

1. Notifications: Added most of the notification functionality
  - Display anomalies during screen rotation.
  - Made changes to the user chat to keep track of number of messages in a chat.
  - Sticker displayed in the notification.
  - Enhanced chat display to show notifications only when a new message is received by the users.
  - Split the fetching chat history and activity listener functionality for smoother functioning of sending and receiving messages.
2. User Interface Enhancements:
  - Made the UI changes to make it look a little better
  - Codebase Refinements:
  - Made the changes to check for loggedIn user to send the notification.
  - Added local images for the notification sticker.


-------------------------------------------------------------------------------------------------------------------------------------------------------------

Feel free to explore our project and provide feedback. We appreciate your interest and support!
