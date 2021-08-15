# Servlets

___

## Login Servlet
### URL: "/LoginServlet"

### Status: Done

POST: ეგზავნება მომხმარებლის აუთენტიკაციისთვის საჭირო მონაცემები.

Sent Attributes:
* user_name - მომხარებლის სახელი.
* password - პაროლი.

Return:
* **Status 400 - ცუდი მოთხოვნა.**
* **Status 401 - წარუმატებელი აუთენტიკაცია.**
* **Status 303 - წარმატებული აუთენტიკაცია.**

___

## Register Servlet
### URL: "/RegisterServlet"

### Status: Done

POST: ეგზავნება მომხმარებლის რეგისტრაციისთვის საჭირო მონაცემები.

Sent Attributes:
* user_name - მომხარებლის სახელი.
* password - პაროლი.
* email - ელ-ფოსტა.

Return: 
* **Status 400 - ცუდი მოთხოვნა.**
* **Status 409 - წარუმატებელი რეგისტრაცია.**
* **Status 201 - წარმატებული რეგისტრაცია.**

___

## User Servlet
### URL: "/UserServlet" (Session Specific)

### Status: Needs Testing


GET: აბრუნებს მომხმარებლის ძირითად მონაცემებს, მოგება/წაგების სტატისტიკას.

Return:
* **Status 200 - წარმატებული მოთხოვნა.**
* user_name - მომხარებლის სახელი.
* email - ელ-ფოსტა.
* first_place_count - მოგებული პირველი ადგილების რ-ბა.
* second_place_count - მოგებული მეორე ადგილების რ-ბა.
* third_place_count - მოგებული მესამე ადგილების რ-ბა.
* loss_count - წაგებების რ-ბა.

___

## Game Servlet
### URL: "/GameServlet" (Session Specific)

### Status: Blocked


GET: აბრუნებს არსებული ნივთებისა და თამაშში მონაწილე მომხმარებლების სიას.

Return:
* **Status 200 - წარმატებული მოთხოვნა.**
* users: List of Usernames [{user_name - მომხარებლის სახელი}, ...] - მონაწილეების სია.
* items: List of Items [] - არსებული ნივთების სია.

POST: 

Return:
* **Status 200 - წარმატებული მოთხოვნა.**

___


## Lobby Servlet

### URL: "/LobbyServlet" (Session Specific)

### Status: In Progress

GET: აბრუნებს ლობიში მყოფი მონაწილეების სიას, ასევე სესიის მომხმარებლის როლს (წამყვანია თუ არა).

Return:
* **Status 200 - წარმატებული მოთხოვნა.**
* status: - ლობის მდგომარეობა ("in_lobby", "active", "finished").
* users: List of Usernames [{
    user_name - მომხმარებლის სახელი
    role - მომხმარებლის როლი
}, ...] - მონაწილეების სია.
* role: - სესიის მომხმარებლის როლი ("host", "guest")

POST: გადაყავს სესიის მომხმარებელი თამაშის ლობიში. ამ შემთხვევაში მომხმარებელი ემატება არსებულ ლობის, ან იმნება
ახალი თუ ასეთი არ არსებობს.

Return:
* **Status 200 - წარმატებული მოთხოვნა.**

___


## Lobby Servlet

### URL: "/LobbyServlet" (Session Specific)

### Status: In Progress

GET: აბრუნებს ლობიში მყოფი მონაწილეების სიას, ასევე სესიის მომხმარებლის როლს (წამყვანია თუ არა).

Return:
* **Status 200 - წარმატებული მოთხოვნა.**
* status: - ლობის მდგომარეობა ("no_lobby", "in_lobby", "production", "drag_race").
* users: List of Usernames [{user_name - მომხმარებლის სახელი}, ...] - მონაწილეების სია.
* role: - სესიის მომხმარებლის როლი ("host", "guest")
___

## Garage Servlet
### URL: "/GarageServlet" (Session Specific)

### Status: Needs Testing

GET: აბრუნებს მომხმარებლის გარაჯში არსებულ მანქანებისა და ნივთების სიას.

Return:
* **Status 200 - წარმატებული მოთხოვნა.**
* cars: List of Cars [] - მანქანების სია.
* items: List of Items[] - ნივთების სია.

POST: ამატებს მომხმარებლის გარაჟში მითითებული სახელის მანქანას.

Sent Attributes:
* car_name - დამატებული მანქანის სახელი.

Return:
* **Status 201 - წარმატებული მოთხოვნა.**
* **Status 409 - წარუმატებელი მოთხოვნა (მანქანა ვერ დაემატა გარაჟს).**

___

## Car Servlet
### URL: "/CarServlet" (Session Specific)

### Status: Blocked

GET: აბრუნებს მითითებული მანქანის შემადგენელი კომპონენტების მონაცემებს.

Sent Attributes:
* car_name - დამატებული მანქანის სახელი.

Return:
* **Status 201 - წარმატებული მოთხოვნა.**
* items: List of Items[] - ნივთების სია.

POST: უმატებს/აკლებს მითითებულ მანქანას კომპონენტებს.

Sent Attributes:
* List[{
    type - ნივთის ტიპი,
    method - ნივთის დამატება/ამოღების აღმნიშნველი: "add"/"remove",
    item - არჩეული ნივთი,
}]

Return:
* **Status 201 - წარმატებული მოთხოვნა.**
* **Status 409 - წარუმატებელი მოთხოვნა (ნივთი ვერ დაემატა/მოაკლდა მანქანას).**

___

## Order Servlet
### URL: "/OrderServlet" (Session Specific)

### Status: Needs Testing

GET: აბრუნებს აქტიური შეკვეთების სიას, ასევე მომხმარებლის შეკვეთებს.

Return:
* **Status 201 - წარმატებული დამატება.**
* user_orders: List of Orders [] - მომხმარებლის შეკვეთების სია.
* orders: List of Orders[] - აქტიური შეკვეთების სია.

POST: ამატებს მომხმარებლისთვის შეკვეთას მითითებული პარამეტრებით.

Sent Attributes:
* source_items: List of Items [] - გასაცემი ნივთების სია.
* destination_items: List of Items [] - სასურველი ნივთების სია.

Return:
* **Status 201 - წარმატებული დამატება.**

___

# ზოგადი ობიექტების ატრიბუტები

    Item: { - ნივთი

        id - ნივთის ID,
        name - ნივთის სახელი,
        type - ნივთის ტიპი,
        [amount - ნივთის რაოდენობა]
    }

    Car: { - მანქანა

        id - მანქანის ID,
        name - მანქანის სახელი
    }

    Order: { - შეკვეთა

        user - შეკვეთის მფლობელის სახელი,
        source_items - List of Items [],
        destination_items - List of Items []
    }
