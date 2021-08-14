# Servlets

___

## Login Servlet
### URL: "/LoginServlet"

POST: ეგზავნება მომხმარებლის აუთენტიკაციისთვის საჭირო მონაცემები.

Sent Attributes:
* user_name - მომხარებლის სახელი.
* password - პაროლი.

Return: 
* Status 401 - წარუმატებელი აუთენტიკაცია.
* Status 303 - წარმატებული აუთენტიკაცია.

___

## Register Servlet
### URL: "/RegisterServlet"

POST: ეგზავნება მომხმარებლის რეგისტრაციისთვის საჭირო მონაცემები.

Sent Attributes:
* user_name - მომხარებლის სახელი.
* password - პაროლი.
* email - ელ-ფოსტა.

Return: 
* Status 409 - წარუმატებელი რეგისტრაცია.
* Status 201 - წარმატებული რეგისტრაცია.

___

## User Servlet
### URL: "/UserServlet" (Session Specific)

GET: აბრუნებს მომხმარებლის ძირითად მონაცემებს, მოგება/წაგების სტატისტიკას.

Return:
* user_name - მომხარებლის სახელი.
* email - ელ-ფოსტა.
* first_place_count - მოგებული პირველი ადგილების რ-ბა.
* second_place_count - მოგებული მეორე ადგილების რ-ბა.
* third_place_count - მოგებული მესამე ადგილების რ-ბა.
* loss_count - წაგებების რ-ბა.

___

## Game Servlet
### URL: "/GameServlet" (Session Specific)

GET: აბრუნებს არსებული ნივთებისა და თამაშში მონაწილე მომხმარებლების სიას.

Return:
* users: List of Usernames [{user_name - მომხარებლის სახელი}, ...] - მონაწილეების სია.
* items: List of Items [] - არსებული ნივთების სია.

___

## Garage Servlet
### URL: "/GarageServlet" (Session Specific)

GET: აბრუნებს მომხმარებლის გარაჯში არსებულ მანქანებისა და ნივთების სიას.

Return:
* cars: List of Cars [] - მანქანების სია.
* spare_items: List of Items[] - ნივთების სია.

POST: ამატებს მომხმარებლის გარაჟში მითითებული სახელის მანქანას.

Sent Attributes:
* car_name - დამატებული მანქანის სახელი.

Return:
* Status 201 - წარმატებული რეგისტრაცია.

___

## Car Servlet
### URL: "/CarServlet" (Session Specific)

POST: უმატებს/აკლებს მითითებულ მანქანას კომპონენტებს.

Sent Attributes:
* List[{
    type - ნივთის ტიპი,
    method - ნივთის დამატება/ამოღების აღმნიშნველი: "add"/"remove",
    item - არჩეული ნივთი,
}]

___

## Order Servlet
### URL: "/OrderServlet" (Session Specific)

GET: აბრუნებს აქტიური შეკვეთების სიას, ასევე მომხმარებლის შეკვეთებს.

Return:
* user_orders: List of Orders [] - მომხმარებლის შეკვეთების სია.
* orders: List of Orders[] - აქტიური შეკვეთების სია.

POST: ამატებს მომხმარებლისთვის შეკვეთას მითითებული პარამეტრებით.

Sent Attributes:
* source_items: List of Items [] - გასაცემი ნივთების სია.
* destination_items: List of Items [] - სასურველი ნივთების სია.

Return:
* Status 201 - წარმატებული დამატება.

___

# ზოგადი ობიექტების ატრიბუტები

Item: { - ნივთი

    id - ნივთის ID,
    name - ნივთის სახელი,
    amount - ნივთის რაოდენობა.
}

Car: { - მანქანა

    id - მანქანის ID,
    name - მანქანის სახელი,
    items: List of Items[] - მანქანის შემადგენელი კომპონენტები.
}
