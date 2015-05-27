
Lifecycle:
Before:
Given it exists the following registered users:
    | username | roles |
    | user1 | PAG_1 |
    | user2 | PAG_2 |
    | user3 | PAG_3 |
Given it exists the following pages:
    | name  | requiredRole |
    | page1 | PAG_1 |
    | page2 | PAG_2 |
    | page3 | PAG_3 |

Scenario: Logged in user with role X browses page with required role X
Given user 'user1' has authenticated his credentials
When the user browses the page 'page1'
Then he is granted for seeing that page

Scenario: Logged in user with role Y browses page with required role X
Given user 'user3' has authenticated his credentials
When the user browses the page 'page1'
Then he is not granted for seeing that page

Scenario: Not logged in user browses page with required role X
Given an unauthenticated user browses the page 'page1'
Then he will receive a message like 'Login is required'

Scenario: User session did expire
Given user 'user1' has authenticated his credentials
Given his session has expired
When the user browses the page 'page1'
Then he will receive a message like 'Session has expired'

Scenario: Renew user session ttl
Given user 'user1' has authenticated his credentials
When his session is validated
Then its expiring time will be increased by 5 more minutes


