## Assignment - A6 At Your Service

## Team Contributions

### Ajay Inavolu

- Figured out the API to be used for this project (SERP Home Depot API).
- Implemented error handling and response parsing mechanisms.
- Modularized success, error, and exception handling into dedicated methods.
- Implemented SnackBar fix for empty fields returned from the API.
- Designed and added the Application Icon.
- Moved the API Key into a secure location.
- Handled empty or nonexistent 'products' even if the API response is 200.

### Jiaming Xu

- Built the UI interface for the search page and search result page (activity_at_your_service.xml, activity_search_result.xml, and most parts of item_card_collapse.xml).
- Developed the search function, including user input, API structure understanding, composing and sending HTTP requests, saved instance state for search activity, etc.
- Created a RecyclerView for search results, including getting HTTP responses, parsing product details, opening a new activity, and displaying results as a RecyclerView list.
- Assisted in designing and debugging.

### Kiran Shatiya Thirugnanasambantham Radhabai

- Figured out the API to be used for this project (SERP Home Depot API).
- Created an initial branch 'kiran-changes-2' with established communication to the SERP API.
- Worked on Instance Management for SearchResult Activity and formatted the title text in the card layout.
- Made the Item card class parcelable for saving instances in the search result activity.
- Tested the app for discrepancies and reported them to the group.

### Vishrutha Abbaiah Reddy

- Figured out the API to be used for this project (SERP Home Depot API).
- Worked on adapting the card view to accommodate different length strings.
- Created the long-click card collapse and expanded the feature to meet a challenge requirement.
- Updated and built the UI interface for item_card_collapse.xml to accommodate new changes.
- Assisted in debugging and testing the app, ensuring code cleanliness.


## Project Description

Our project addresses various challenges in creating a user-friendly and efficient product suggestion system. Below are some of the key challenges we have tackled:

1. **Displaying Icons for Categorical Variables:** We have successfully displayed icons for categorical variables in our user interface. For instance, each product suggestion is accompanied by a review section depicted using a 5-star rating scale. The JSON response provides float values for ratings, and we have integrated these seamlessly.

2. **Variable Length Lists with RecyclerView:** Our response is a variable-length list of product suggestions that dynamically adjusts based on search queries. These product suggestions are displayed as cards in a RecyclerView, ensuring a responsive and adaptable user interface.

3. **Handling Variable Length Strings:** Product names and brands can vary in length for each product. To address this, we have implemented a constraint layout inside each card. When collapsed, only two lines of the product name are displayed, and the full name is revealed when the card is expanded.

4. **Inputting Variable Numbers of Values:** We have provided a user-friendly interface with a variety of input controls, including radio buttons, dropdowns, toggle switches, and text fields. This allows users to input search queries with different requirements and preferences.

In addition to these challenges, we have met several other constraints:

- **Equal Team Contributions:** Each team member has contributed equally to the project's success.
- **Spinner Wheel for Loading Progress:** We have incorporated a spinner wheel to visually indicate loading progress, enhancing the user experience.
- **Non-blocking Main Thread:** Our application ensures that the main thread is not blocked, guaranteeing smooth and responsive interactions.
- **Error Handling:** We have implemented robust error handling to gracefully handle unexpected situations.
- **InstanceState Management:** To maintain a seamless user experience, we have properly saved and handled the instance state.

Feel free to explore our project and provide feedback. We appreciate your interest and support!
