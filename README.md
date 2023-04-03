# Prueba

Este es un proyecto realizado con Springboot, H2, Test y JWT

Prerequisitos; tener instaladas los siguientes elementos:

    -   jdk8
    -   Gradle: https://gradle.org/
    -   Postman

Inicie una terminal (cmd, PowerShell, prompt, etc) y sitúese en el  el directorio donde se encuentre el código
fuente descargado desde git ejecutar el comando de gradle para compilar y ejecutar la aplicacion.

    - ./gradlew bootRun en mac y linux
    - gradlew bootRun en windows
    - En caso de error por permiso denegado de permiso de super usuario con [chmod 755 gradlew]

Tambien es posible utilizar las herramientas que disponga el ide para ejecutar gradle.
![img.png](img.png)

    
Para probar el microservicio Rest se debe importar la coleccion de Postman: ApiUsuarioTest.postman_collection.json. 
Una vez importada la coleccion debe ejecutar el request "GenerarToken" , el cual entregara un token de acceso con el
que prorá ejecutar los demas endpoints. Se suguiere testearlos en el orden entregado y que usan variables de entorno.

Para Acceder a la Base de Datos H2 (Embebida en memoria), usar las credenciales:

    - http://localhost:8080/prueba/api/h2-console
    - JDBC URL: jdbc:h2:mem:testdb
    - user: sa
    - pass: password

