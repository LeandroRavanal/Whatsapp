## Whastapp Group

La idea de este ejercicio es que cada integrante se asocie a un grupo predefinido y pueda enviar notificaciones vía web a los demás integrantes.

Como el fin del ejercicio es el envío de notificaciones, el grupo sea crea por única vez al iniciar la aplicación y todos los usuarios van a pertenecer a este grupo.

Para las notificaciones usé Firebase que asocia un token a cada navegador (usuario). El usuario debe habilitar las notificaciones.

Los mensajes quedan guardados en la cache Redis como clave-valor dentro de la entidad grupo.

A nivel técnico es importante comentar que la aplicación está dividida en dos partes el frontend con los controladores y las validaciones de negocio y el backend con las notificaciones y que por medio de Spring-Boot se pueden levantar ambas, o una u otra. La comunicación entre ambas partes es por una queue de ActiveMQ que al realizar un desacople permite levantar instancias a conveniencia y en definitiva un escalamiento horizontal.

Generación:

```
 mvn clean package
```

Ejecución:

```
frontend y backend: java -jar whatsapp-0.0.1.jar
frontend: java -jar whatsapp-0.0.1.jar --app.notification-sender=false
backend: java -jar whatsapp-0.0.1.jar --spring.main.web-application-type=none --app.front-controller=false
```

Importante: Firebase necesita varias claves, por tanto, antes de probarlo, deben actualizar los siguientes archivos:

> 1. index.html
> 2. firebase-service-account.json
> 3. firebase-messaging-sw.js

También van a necesitar instalar ActiveMQ y Redis y usar Java8 y Maven3.
