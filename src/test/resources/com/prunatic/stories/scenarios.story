
Background:
Given it exists the following registered users:
    | username | roles |
    | user1    | PAG_1 |
    | user2    | PAG_2 |
    | user3    | PAG_3 |
Given it exists the following pages:
    | name  | requiredRole |
    | page1 | PAG_1        |
    | page2 | PAG_2        |
    | page3 | PAG_3        |

Scenario: Logged in user with role X browses page with required role X

Scenario: Logged in user with role Y browses page with required role X

Scenario: Not logged in user browses page with required role X

Scenario: Session did expire

Scenario: Session ttl renewal

