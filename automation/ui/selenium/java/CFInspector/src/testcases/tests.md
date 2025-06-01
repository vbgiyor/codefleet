### Last Updated on June 01, 2025 
## ✅ **A/B Testing Page (`/abtest`)**

### **Test Case 1: Verify the A/B Testing page loads successfully from the CFInspector page**

* **Steps**:

    1. Navigate to the CFInspector Page.
    2. Click on the link/button to access the A/B Testing page.
* **Expected Result**:

    * The A/B Testing page loads successfully.

---

### **Test Case 2: Verify the headline text is "Good News" or "Surprise"**

* **Steps**:

    1. Navigate to the `/abtest` page.
* **Expected Result**:

    * The headline text is either **"Good News"** or **"Surprise"**.

---

### **Test Case 3: Verify the paragraph text contains "Also known as split testing..."**

* **Steps**:

    1. Navigate to the `/abtest` page.
* **Expected Result**:

    * The paragraph text contains the phrase **"Also known as split testing..."**.

---

### **Test Case 4: Verify navigating back to the CFInspector Page from the A/B Testing page**

* **Steps**:

    1. Navigate to the `/abtest` page.
    2. Click the back button or link to go back to the CFInspector Page.
* **Expected Result**:

    * The user is redirected back to the CFInspector page.

---

## ✅ **Add/Remove Elements Page (`/addremoveelements`)**

### **Test Case 1: Verify the Add/Remove Elements page loads successfully from the CFInspector Page**

* **Steps**:

    1. Navigate to the CFInspector Page.
    2. Click on the link/button to access the Add/Remove Elements page.
* **Expected Result**:

    * The Add/Remove Elements page loads successfully.

---

### **Test Case 2: Verify the "Add Element" button is present and clickable**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
* **Expected Result**:

    * The "Add Element" button is visible and clickable.

---

### **Test Case 3: Verify the "Remove Element" button is present and clickable**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
* **Expected Result**:

    * The "Remove Element" button is visible and clickable.

---

### **Test Case 4: Verify a maximum of 5 elements can be added**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Add elements until the limit is reached.
* **Expected Result**:

    * A maximum of 5 elements can be added to the list.

---

### **Test Case 5: Verify the 'Add Element' button gets disabled after adding the 5th element**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Add 5 elements.
* **Expected Result**:

    * The "Add Element" button is disabled after the 5th element is added.

---

### **Test Case 6: Verify the user is prompted with message "Maximum limit of 5 elements reached." after adding 5 elements**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Add 5 elements.
* **Expected Result**:

    * A message **"Maximum limit of 5 elements reached."** appears.

---

### **Test Case 7: Verify the element input text box and 'Add Element' button get disabled after adding 5 elements**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Add 5 elements.
* **Expected Result**:

    * The element input text box and "Add Element" button are disabled.

---

### **Test Case 8: Verify clicking the "Remove" button removes the last element in the displayed list**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Add elements to the list.
    3. Click the "Remove" button.
* **Expected Result**:

    * The last element in the list is removed.

---

### **Test Case 9: Verify the user is prompted with the message "No elements to remove." after removing all 5 elements**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Remove all 5 elements.
* **Expected Result**:

    * The message **"No elements to remove."** appears.

---

### **Test Case 10: Verify navigating back to the CFInspector page from the Add/Remove Elements page**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Click the back button or link to go back to the CFInspector Page.
* **Expected Result**:

    * The user is redirected back to the CFInspector page.

---

### **Test Case 11: Verify the element input text box is enabled after removing all 5 elements from the page**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Remove all 5 elements.
* **Expected Result**:

    * The element input text box is enabled.

---

### **Test Case 12: Hide "Maximum limit of 5 elements reached." message when the number of items in the provided list is >=4**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Add 4 or more elements.
* **Expected Result**:

    * The message **"Maximum limit of 5 elements reached."** is hidden when the number of elements is >=4.

---

### **Test Case 13: Hide "No elements to remove." message when there is at least one element in the list**

* **Steps**:

    1. Navigate to the `/addremoveelements` page.
    2. Ensure at least one element is in the list.
* **Expected Result**:

    * The message **"No elements to remove."** is hidden if at least one element is present.

---

## ✅ **Basic Auth Page (`/basicauth`)**

### **Test Case 1: Verify navigating to the Basic Auth page from the CFInspector page triggers the Basic Auth prompt**

* **Steps**:

    1. Navigate to the CFInspector Page.
    2. Click on the link/button to access the Basic Auth page.
* **Expected Result**:

    * The Basic Auth prompt is triggered.

---

### **Test Case 2: Verify entering correct credentials (admin\:admin) displays the success message**

* **Steps**:

    1. Navigate to the Basic Auth page.
    2. Enter **admin\:admin** as the credentials.
* **Expected Result**:

    * A success message, **"Congratulations! You have successfully authenticated..."** is displayed.

---

### **Test Case 3: Verify canceling the Basic Auth prompt redirects the user back to the CFInspector page**

* **Steps**:

    1. Navigate to the Basic Auth page.
    2. Cancel the Basic Auth prompt.
* **Expected Result**:

    * The user is redirected back to the CFInspector page.

---

### **Test Case 4: Verify entering incorrect credentials (e.g., user\:pass) redirects the user back to the CFInspector page**

* **Steps**:

    1. Navigate to the Basic Auth page.
    2. Enter incorrect credentials (e.g., **user\:pass**).
* **Expected Result**:

    * The user is redirected back to the CFInspector page.

---

### **Test Case 5: Verify navigating back to the CFInspector page after successful authentication**

* **Steps**:

    1. Navigate to the Basic Auth page.
    2. Enter valid credentials.
    3. Click the link or button to return to the CFInspector page.
* **Expected Result**:

    * The user is redirected back to the CFInspector page.

---
Sure! Here are the test cases based on the code you provided, following the format you requested:

---

## ✅ **Selenium Page (`/resources/selenium`)**

### **Test Case 1: Verify the Selenium page loads successfully**

* **Steps**:

    1. Navigate to the Selenium page (`/resources/selenium`).
* **Expected Result**:

    * The page loads successfully, and the current URL matches the expected URL.

---

### **Test Case 2: Verify the title of the Selenium page is correct**

* **Steps**:

    1. Navigate to the Selenium page (`/resources/selenium`).
* **Expected Result**:

    * The page title is **"Selenium Resource Library"**.

---

### **Test Case 3: Verify the Selenium page description contains the correct text**

* **Steps**:

    1. Navigate to the Selenium page (`/resources/selenium`).
* **Expected Result**:

    * The description text contains the phrase **"This in-depth tutorial series explores Selenium UI automation with Java"**.

---

### **Test Case 4: Verify the title of the Selenium Projects section is correct**

* **Steps**:

    1. Navigate to the Selenium page (`/resources/selenium`).
    2. Check the title of the **Selenium Projects** section.
* **Expected Result**:

    * The title of the Selenium Projects section is **"🔹 Selenium Projects"**.

---

### **Test Case 5: Verify the presence of project links in the Selenium Projects section**

* **Steps**:

    1. Navigate to the Selenium page (`/resources/selenium`).
    2. Check for the presence of links in the **Selenium Projects** section.
* **Expected Result**:

    * The links for individual projects are present (specific project links will be verified in subsequent tests).

---

### **Test Case 6: Verify clicking on the "CFInspector" project navigates correctly**

* **Steps**:

    1. Navigate to the Selenium page (`/resources/selenium`).
    2. Click on the **CFInspector** project link.
* **Expected Result**:

    * The page navigates to the CFInspector project, and the URL should contain **"/cfinspector"**.

---