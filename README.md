## First start the opa server in the previous repo, then

## invoke request to the spring boot app

```shell
curl --location 'http://localhost:8080/salary' \
--header 'Content-Type: application/json' \
--data '{
    "username": "charlie",
    "employee": "alice"
}'
```