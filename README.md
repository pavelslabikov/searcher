# searcher
Консольное приложение, позволяющее производить поиск в `.csv` файле по индексированной колонке, используя сравнение по префиксам строк. 
# Запуск
```
mvn package
java -Xmx9m -jar target/searcher.jar
```
# Конфигурация
- Конфигурационный файл находится в `src/main/resources/application.yml`
    - `app.filename` - относительный путь до `.csv` файла, в котором будет выполняться поиск  
    - `app.column-number` - номер колонки для индексирования *(отсчёт от 1)*
```
app:
  column-number: 2
  filename: "./src/main/resources/airports.dat"
```  
- Перегрузка дефолтных значений возможна при помощи установки соответствующих
ключей при запуске приложения (`--app.filename=new_file` и `--app.column-number=1`)