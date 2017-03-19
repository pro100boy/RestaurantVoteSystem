## Restaurant Vote System (application deployed in application context `vote`). ##

### *Get results of vote for the specified date. If date isn't presented, then date = today*
`curl -s http://localhost:8080/vote/rest/profile/votes/result?date= --user user@ya.ru:password`

### *Get a list of restaurants with menus for date. If date isn't presented, then date = today*
`curl -s http://localhost:8080/vote/rest/profile/restaurants/polls?date=2017-02-20 --user user@ya.ru:password`

----------

#### Test AdminRestController

- get All Users:
    
> `curl -s http://localhost:8080/vote/rest/admin/users --user admin@gmail.com:admin`

- get User 100001:
    
> `curl -s http://localhost:8080/vote/rest/admin/users/100001 --user admin@gmail.com:admin`

- create User:
    
> `curl -s -X POST -d '{"name": "NewUser","email": "NewUser@ya.ru","password": "password","enabled": true,"roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8888/vote/rest/admin/users --user admin@gmail.com:admin`

#### Test ProfileRestController

- get profile of authorized user:
    
> `curl -s http://localhost:8080/vote/rest/profile --user user@ya.ru:password`

- get list of authorized user votes between dates:
    
> `curl -s http://localhost:8080/vote/rest/profile/between?startDate=&endDate= --user admin@gmail.com:admin`

----------

#### Test RestaurantAdminRestController

- create Restaurant:
    
> `curl -s -X POST -d '{"name": "New Restaurant","description": "Description of New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8888/vote/rest/admin/restaurants --user  admin@gmail.com:admin`

-  update Restaurant
> `curl -s -X PUT -d '{"name": "Updated Restaurant","description": "Description of Updated Restaurant"}' -H 'Content-Type: application/json' http://localhost:8888/vote/rest/admin/restaurants/100022 --user admin@gmail.com:admin`

- get the restaurant with menus and votes between dates:

> `curl -s http://localhost:8888/vote/rest/admin/restaurants/100004/between?startDate=&endDate= --user admin@gmail.com:admin`

- delete Restaurant:
`curl -s -X DELETE http://localhost:8888/vote/rest/admin/restaurants/100006 --user admin@gmail.com:admin`

#### Test RestaurantProfileRestController

- get All Restaurants:
    
> `curl -s http://localhost:8888/vote/rest/profile/restaurants --user user@ya.ru:password`

- find the list of restaurants which names begin with:
    
> `curl -s http://localhost:8888/vote/rest/profile/restaurants/by?name=restaur --user user@ya.ru:password`

----------

#### Test VoteAdminRestController

- get all votes from concrete user

> `curl -s http://localhost:8888/vote/rest/admin/votes/users/100000 --user admin@gmail.com:admin`

- get votes list for period for specific restaurant

> `curl -s http://localhost:8888/vote/rest/admin/votes/restaurants/100005/between?startDate=&endDate= --user admin@gmail.com:admin`

#### Test VoteProfileRestController

- get all user votes

> `curl -s http://localhost:8888/vote/rest/profile/votes --user user@ya.ru:password`

- create vote for restaurant 100004

> `curl -s -S -u user@ya.ru:password -X POST http://localhost:8888/vote/rest/profile/votes/restaurants/100004`
