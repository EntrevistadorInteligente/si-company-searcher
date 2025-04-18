# WhatsApp Webhook Service

Este microservicio es parte de una arquitectura de procesamiento de mensajes de WhatsApp. Su función principal es recibir los webhooks de WhatsApp, guardarlos en una base de datos MongoDB para resiliencia y publicarlos en Kafka para su procesamiento posterior.

## Arquitectura

La aplicación sigue una arquitectura hexagonal (ports & adapters) con tres capas principales:

### 1. Capa de Dominio

* **Modelos**: Entidades puras de negocio sin dependencias externas (WhatsappMessage)
* **Puertos**: Interfaces que definen cómo la capa de dominio interactúa con servicios externos
  * `WhatsappMessageRepository`: Puerto para persistencia de mensajes
  * `WhatsappPublisher`: Puerto para publicación de mensajes a Kafka
* **Servicios de Dominio**: Implementan la lógica de negocio principal
  * `CrearProcesarMensajeWhatsappService`: Gestiona la creación y actualización de mensajes

### 2. Capa de Aplicación

* **Casos de Uso**: Interfaces que definen las operaciones que puede realizar la aplicación
  * `ProcesarMensajeWhatsapp`: Caso de uso para procesar mensajes entrantes
* **Servicios de Aplicación**: Implementan los casos de uso, coordinando la interacción entre el dominio y los adaptadores
  * `ProcesarMensajeWhatsappService`: Implementa el procesamiento de mensajes, coordinando persistencia, publicación y procesamiento

### 3. Capa de Infraestructura

* **DAOs**: Interfaces de acceso a datos que extienden de los repositorios de Spring
  * `WhatsappMessageDao`: Acceso a datos para mensajes de WhatsApp en MongoDB
* **Adaptadores de Repositorio**: Implementan los puertos de repositorio del dominio
  * `WhatsappMessageRepositoryAdapter`: Implementa el puerto del dominio utilizando el DAO
* **Adaptadores JMS**: Implementan los puertos de mensajería
  * `WhatsappPublisherAdapter`: Implementa el puerto de publicación con Kafka
* **Controladores**: Punto de entrada a la aplicación
  * `EntrevistaController`: Recibe los webhooks de WhatsApp y los procesa
* **DTOs y Mappers**: Conversión entre formatos de datos
  * DTOs para la estructura del webhook de WhatsApp
  * `WhatsappMessageMapper`: Convierte entre objetos de dominio, entidades y DTOs

## Flujo de Procesamiento

1. El webhook de WhatsApp es recibido por `EntrevistaController`.
2. El controlador convierte el DTO a un objeto de dominio `WhatsappMessage`.
3. El controlador invoca el caso de uso `ProcesarMensajeWhatsapp`.
4. El servicio de aplicación coordina el proceso:
   - Guarda el mensaje en MongoDB mediante el puerto `WhatsappMessageRepository`.
   - Publica el mensaje en Kafka mediante el puerto `WhatsappPublisher`.
   - Marca el mensaje como procesado en MongoDB.
5. Si algo falla, el mensaje queda en estado pendiente y puede ser reprocesado.

## Configuración

La aplicación se configura mediante variables de entorno:

* `MONGODB_URI`: URI de conexión a MongoDB (default: `mongodb://localhost:27017/analizador_empresa`)
* `KAFKA_SERVERS`: Servidores Kafka (default: `localhost:9092`)
* `PORT`: Puerto HTTP (default: `8080`)

## Endpoints

* **GET /v1/whatsapp_webhook?hub.mode=...**: Endpoint de verificación para el webhook de WhatsApp
* **POST /v1/whatsapp_webhook/webhook**: Endpoint para recibir mensajes de WhatsApp
* **GET /v1/whatsapp_webhook/reprocess**: Endpoint para reprocesar mensajes pendientes