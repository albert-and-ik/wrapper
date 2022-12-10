## Wrapper 
Предназначен для обертки ответов перед отправкой клиенту.
По умолчанию используется [SimpleWrapperModel](/src/main/java/com/example/wrapper/model/SimpleWrapperModel.java):
```json
{
    "data" : {"you": "response"}
}
```
Для того чтобы использовать свою обертку нужно создать Bean унаследованный от [AbstractWrapperModel< T >](/src/main/java/com/example/wrapper/model/AbstractWrapperModel.java) 
и реализовать метод `void setResponse(T data)`

Унаследованный класс должен содержать в себе конструктор без параметров, getters/setters для всех полей или проще говоря быть [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object). 
*Можно использовать [`@Data`](https://projectlombok.org/features/Data)*


Чтобы использовать обертку нужно указать аннотацию:
1. `@Wrap` - можно указать над методом, который возвращает ответ для пользователя или над классом контроллером. 
*Если указать над классом, то работать будет и у методов*
2. `@WrapRestController` - содержит в себе `@Wrap` и  `@RestController`
3. `@AlwaysWrap` - всегда нужно оборачивать объект
4. `@NoWrap` - указывает, что оборачивать не надо
5. `@NeverWrap` - никогда не оборачивать

Приоритеты аннотаций:
1. `@AlwaysWrap`
2. `@NoWrap` и `@NeverWrap`
3. `@Wrap` и `@WrapRestController`

Примеры ответов:
```json
{
    "data": {
        "user": "username",
        "token": "some token for user"
    }
}
```
```json
{
    "data": "Wrap",
    "isPresent": true,
    "anyData": "any data",
    "someValue": {
        "name": "name",
        "desc": "desc",
        "code": 101
    }
}
```
***Примечание:*** исключения **не** обертываются и **не** обрабатываются.