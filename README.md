# taskable object oriented assessment

## Notes

1. I've created unit tests only for Order Class
2. I've assumed that the data is coming into the applications via xml files

## Instructions to run

1. Clone the repository and run the main class to print output(preferably in Intellij Idea)
2. Run the test suite by right clicking on OrderTest inside the package tests.orders and clicking on "Run OrderTest"

## Sample Output from the main class

* If everything runs as expected the output would look like this:

    
    # This state is the initial state of the customers directly loaded from the xml
    Customer's state before orders are processed
    Id: 312431       Name: Matthew Johnson       Memberships: []       ItemsBought: {}       
    Id: 312432       Name: Tomasz Krakowiak       Memberships: [Book Club Membership]       ItemsBought: {}       
    Id: 312433       Name: Kashyap Vadrevu       Memberships: [Video Club Membership, Book Club Membership]       ItemsBought: {Video=[21343, 21344], Book=[21348]}       
    ==================================================
    # These are the orders loaded from the xml that needs to be processed.
    Orders to be processed
    Id: 185601       Total Price: 80.0       Customer ID: 312431       Item Line: {21342=Video Club Membership}       
    Id: 185602       Total Price: 77.0       Customer ID: 312431       Item Line: {21341=Book Club Membership, 21346=Book, 21347=Book, 21345=Video}       
    Id: 185603       Total Price: 38.0       Customer ID: 312432       Item Line: {21348=Book, 21349=Book}       
    Id: 185604       Total Price: 38.0       Customer ID: 312433       Item Line: {21351=Video, 21352=Video}       
    ==================================================
    Processing orders
    ==================================================
    Processing done
    ==================================================
    # These are the shippoing slips generated after the above orders have been processed.
    Shipping slips generated
    Id: 2       Customer Id: 312431       orderID: 185602       Items to be shipped: [21346, 21347, 21345]       Address: 3318 N Summit Pointe Dr, Topanga, CA 90290, USA       
    Id: 3       Customer Id: 312432       orderID: 185603       Items to be shipped: [21348, 21349]       Address: 240 Wawrzyszew, Renesansowa, Warsaw 46580, Poland       
    Id: 4       Customer Id: 312433       orderID: 185604       Items to be shipped: [21351, 21352]       Address: 910 King St, San Francisco, CA 91458, USA
    # This is the state of customers after all the prders have been processed and shipping slips generated.
    Customer's state after orders are processed
    Id: 312431       Name: Matthew Johnson       Memberships: [Video Club Membership, Book Club Membership]       ItemsBought: {Book=[21346, 21347], Video=[21345]}       
    Id: 312432       Name: Tomasz Krakowiak       Memberships: [Book Club Membership]       ItemsBought: {Book=[21348, 21349]}       
    Id: 312433       Name: Kashyap Vadrevu       Memberships: [Video Club Membership, Book Club Membership]       ItemsBought: {Video=[21343, 21344, 21351, 21352], Book=[21348]}
    ==================================================