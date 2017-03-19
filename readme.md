## Restaurant Vote System (application deployed in application context `vote`). ##

### *Get results of vote for the specified date. If date isn't presented, then date = today*
`curl -s http://localhost:8080/vote/rest/profile/votes/result?date= --user admin@gmail.com:admin`

### *Get a list of restaurants with menus for date. If date isn't presented, then date = today*
`curl -s http://localhost:8080/vote/rest/profile/restaurants/polls?date=2017-02-20 --user user@ya.ru:password`

### Test AdminRestController

- get All Users:
    
> `curl -s http://localhost:8080/vote/rest/admin/users --user admin@gmail.com:admin`

- get User 100001:
    
> `curl -s http://localhost:8080/vote/rest/admin/users/100001 --user admin@gmail.com:admin`